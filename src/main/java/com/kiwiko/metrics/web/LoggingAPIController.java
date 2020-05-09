package com.kiwiko.metrics.web;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.data.LogLevel;
import com.kiwiko.mvc.json.data.ResponseBuilder;
import com.kiwiko.mvc.json.data.ResponsePayload;
import com.kiwiko.mvc.requests.api.annotations.RequestBodyParameter;
import com.kiwiko.mvc.requests.api.RequestError;
import com.kiwiko.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@CrossOriginConfigured
public class LoggingAPIController {

    @Inject
    private LogService logService;

    @PostMapping("/logging/api/log/by-level")
    public ResponseEntity<ResponsePayload> log(
            @RequestBodyParameter(name = "level") String levelName,
            @RequestBodyParameter(name = "message") String message,
            @RequestBodyParameter(name = "error", required = false) String error) {
        LogLevel level = LogLevel.getByName(levelName)
                .orElseThrow(() -> new RequestError(String.format("Unknown log level \"%s\"", levelName)));
        String logMessage = String.format("[CLIENT] %s", message);
        if (error != null) {
            logMessage = String.format("%s\n\t%s", logMessage, error);
        }

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

        return ResponseBuilder.ok();
    }
}
