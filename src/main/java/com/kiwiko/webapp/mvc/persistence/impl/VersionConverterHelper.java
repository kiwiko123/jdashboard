package com.kiwiko.webapp.mvc.persistence.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.kiwiko.library.persistence.dataAccess.api.versions.Version;
import com.kiwiko.library.persistence.dataAccess.data.VersionDTO;
import com.kiwiko.webapp.mvc.json.api.errors.JsonException;

import javax.inject.Inject;
import java.util.List;

public class VersionConverterHelper {

    @Inject
    private ObjectMapper objectMapper;

    public List<Version> deserializeVersionsJson(String versionsJson) {
        CollectionType collectionType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, VersionDTO.class);
        try {
            return objectMapper.readValue(versionsJson, collectionType);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
}
