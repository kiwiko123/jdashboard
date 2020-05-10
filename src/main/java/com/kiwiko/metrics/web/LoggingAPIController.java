package com.kiwiko.metrics.web;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.data.LogLevel;
import com.kiwiko.metrics.web.data.LogData;
import com.kiwiko.mvc.json.api.ResponseBuilder;
import com.kiwiko.mvc.json.data.ResponsePayload;
import com.kiwiko.mvc.requests.api.annotations.RequestBodyCollectionParameter;
import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@CrossOriginConfigured
public class LoggingAPIController {

    @Inject
    private LogService logService;

    @PostMapping("/logging/api/log/by-level")
    public ResponseEntity<ResponsePayload> logByLevel(@RequestBody LogData log) {
        log(log);
        return ResponseBuilder.ok();
    }

    @PostMapping("/logging/api/log/batched/by-level")
    public ResponseEntity<ResponsePayload> logBatchedByLevel(
            @RequestBodyCollectionParameter(name = "logs", valueType = LogData.class) List<LogData> logs) {
        logs.forEach(this::log);
        return ResponseBuilder.ok();
    }

    private void log(LogData log) {
        String message = String.format("(%s) %s", log.getTimestamp().toString(), log.getMessage());
        String logMessage = String.format("[CLIENT] %s", message);
        if (log.getError().isPresent()) {
            logMessage = String.format("%s\n\t%s", logMessage, log.getError().get());
        }
        LogLevel level = log.getLevel();

        switch (level) {
            case DEBUG:
                logService.debug(logMessage);
                break;
            case INFO:
                logService.info(logMessage);
                break;
            case WARN:
                logService.warn(logMessage);
                break;
            case ERROR:
                logService.error(logMessage);
                break;
            default:
                throw new RequestError(String.format("Unsupported level %s", level.toString()));
        }
    }
}
