package com.kiwiko.jdashboard.webapp.persistence.identification.unique.web;

import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/uuids/api")
public class UniversalUniqueIdentifierAPIController {

    @Inject private UniqueIdentifierService uniqueIdentifierService;

    @GetMapping("")
    public String generateUuid() {
        return uniqueIdentifierService.generateUuid();
    }
}
