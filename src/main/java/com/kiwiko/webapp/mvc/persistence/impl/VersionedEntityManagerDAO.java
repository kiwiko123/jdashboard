package com.kiwiko.webapp.mvc.persistence.impl;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.persistence.dataAccess.api.EntityManagerDAO;
import com.kiwiko.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.library.persistence.dataAccess.api.versions.Version;
import com.kiwiko.library.persistence.dataAccess.api.versions.VersionedEntity;
import com.kiwiko.library.persistence.dataAccess.data.VersionChanges;
import com.kiwiko.library.persistence.dataAccess.data.VersionDTO;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.mvc.requests.api.CurrentRequestService;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.users.data.User;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class VersionedEntityManagerDAO<T extends VersionedEntity> extends EntityManagerDAO<T> {

    private static final Set<String> IGNORED_VERSION_FIELD_NAMES = Set.of("versions");

    @Inject private JsonMapper jsonMapper;
    @Inject private VersionConverterHelper versionConverterHelper;
    @Inject private CurrentRequestService currentRequestService;
    @Inject private LogService logService;
    private final ReflectionHelper reflectionHelper;

    protected VersionedEntityManagerDAO() {
        super();
        reflectionHelper = new ReflectionHelper();
    }

    @Override
    public T save(T entity) {
        try {
            recordVersion(entity);
        } catch (Exception e) {
            logService.error(String.format("Error saving new version for entity %s", entity.toString()), e);
            throw e; // TODO remove after this works
        }

        return super.save(entity);
    }

    @Override
    public void delete(T entity) {
        entity.setIsRemoved(true);
        save(entity);
    }

    private void recordVersion(T entity) {
        List<Version> versions = new ArrayList<>();
        VersionDTO version = new VersionDTO();

        if (entity.getId() != null) {
            T currentRecord = getById(entity.getId())
                    .orElseThrow(() -> new PersistenceException(String.format("Failed to fetch current record of type %s", entity.getClass().getSimpleName())));
            versions = versionConverterHelper.deserializeVersionsJson(currentRecord.getVersions());
            VersionChanges diff = makeDiff(currentRecord, entity);

            version.setChanges(diff);
            version.setVersion(versions.size());

            currentRequestService.getCurrentRequestContext()
                    .flatMap(RequestContext::getUser)
                    .map(User::getId)
                    .ifPresent(version::setUserId);
        }

        versions.add(version);
        String dumpedVersions = jsonMapper.writeValueAsString(versions);
        entity.setVersions(dumpedVersions);
    }

    private VersionChanges makeDiff(T currentRecord, T updatedRecord) {
        Map<String, Object> changes = new HashMap<>();
        for (Field field : reflectionHelper.getFields(entityType)) {
            if (IGNORED_VERSION_FIELD_NAMES.contains(field.getName())) {
                continue;
            }

            Object currentValue;
            Object updatedValue;

            try {
                field.trySetAccessible();
                currentValue = field.get(currentRecord);
                updatedValue = field.get(updatedRecord);
            } catch (IllegalAccessException e) {
                throw new PersistenceException(String.format("Failed to access field %s", field.getName()), e);
            }

            if (!Objects.equals(currentValue, updatedValue)) {
                changes.put(field.getName(), updatedValue);
            }
        }

        return new VersionChanges(changes);
    }
}
