package com.kiwiko.webapp.mvc.persistence.crud.api;

import com.kiwiko.library.persistence.dataAccess.api.DataEntity;
import com.kiwiko.webapp.mvc.persistence.dataaccess.api.EntityManagerDAO;
import com.kiwiko.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.library.persistence.identification.Identifiable;
import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class CreateReadUpdateDeleteService<
        Entity extends DataEntity,
        DTO extends Identifiable<Long>,
        DataFetcher extends EntityManagerDAO<Entity>,
        Mapper extends EntityMapper<Entity, DTO>>
        implements CreateReadUpdateDeleteAPI<DTO> {
    protected final DataFetcher dataFetcher;
    protected final Mapper mapper;

    protected CreateReadUpdateDeleteService() {
        dataFetcher = getDataFetcher();
        mapper = getMapper();
    }

    protected abstract DataFetcher getDataFetcher();
    protected abstract Mapper getMapper();

    @Override
    public Optional<DTO> get(long id) {
        return read(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> read(long id) {
        return dataFetcher.getById(id)
                .map(mapper::toDTO);
    }

    @Transactional
    @Override
    public <R extends DTO> DTO create(R obj) {
        Entity entity = mapper.toEntity(obj);
        entity = dataFetcher.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    @Override
    public <R extends DTO> DTO update(R obj) {
        if (dataFetcher.getProxyById(obj.getId()).isEmpty()) {
            String message = String.format("%s with ID %d doesn't exist", obj.getClass().getName(), obj.getId());
            throw new PersistenceException(message);
        }

        Entity updatedEntity = mapper.toEntity(obj);
        updatedEntity = dataFetcher.save(updatedEntity);
        return mapper.toDTO(updatedEntity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Entity entity = dataFetcher.getById(id)
                .orElseThrow(() -> new PersistenceException(String.format("Entity with ID %d doesn't exist", id)));
        dataFetcher.delete(entity);
    }
}
