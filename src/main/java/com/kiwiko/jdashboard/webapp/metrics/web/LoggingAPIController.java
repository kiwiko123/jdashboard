package com.kiwiko.jdashboard.webapp.metrics.web;

import com.kiwiko.jdashboard.library.metrics.data.LogLevel;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.metrics.web.data.LogData;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.internal.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.requests.api.annotations.RequestBodyCollectionParameter;
import com.kiwiko.jdashboard.webapp.framework.requests.api.RequestError;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@CrossOriginConfigured
public class LoggingAPIController {

    @Inject private Logger logger;

    @PutMapping("/logging/api/log")
    public ResponseEntity<ResponsePayload> logByLevel(@RequestBody LogData logData) {
        log(logData);
        return ResponseBuilder.ok();
    }

    @PutMapping("/logging/api/log/batched")
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
                logger.debug(logMessage);
                break;
            case INFO:
                logger.info(logMessage);
                break;
            case WARN:
                logger.warn(logMessage);
                break;
            case ERROR:
                logger.error(logMessage);
                break;
            default:
                throw new RequestError(String.format("Unsupported level %s", level.toString()));
        }
    }
}
