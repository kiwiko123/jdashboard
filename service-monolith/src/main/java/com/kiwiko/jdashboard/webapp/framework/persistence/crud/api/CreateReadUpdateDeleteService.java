package com.kiwiko.jdashboard.webapp.framework.persistence.crud.api;

import com.kiwiko.jdashboard.webapp.framework.persistence.dataaccess.api.EntityManagerDAO;
import com.kiwiko.jdashboard.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.jdashboard.library.persistence.identification.Identifiable;
import com.kiwiko.jdashboard.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.jdashboard.library.persistence.properties.api.EntityMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Deprecated
public abstract class CreateReadUpdateDeleteService<
        Entity extends Identifiable<Long>,
        DTO extends Identifiable<Long>,
        DataFetcher extends EntityManagerDAO<Entity>,
        Mapper extends EntityMapper<Entity, DTO>>
        implements CreateReadUpdateDeleteAPI<DTO> {
    protected abstract DataFetcher getDataFetcher();
    protected abstract Mapper getMapper();

    @Override
    public Optional<DTO> get(long id) {
        return read(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> read(long id) {
        DataFetcher dataFetcher = getDataFetcher();
        Mapper mapper = getMapper();

        return dataFetcher.getById(id)
                .map(mapper::toDTO);
    }

    @Transactional
    @Override
    public <R extends DTO> DTO create(R obj) {
        DataFetcher dataFetcher = getDataFetcher();
        Mapper mapper = getMapper();

        Entity entity = mapper.toEntity(obj);
        entity = dataFetcher.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    @Override
    public <R extends DTO> DTO update(R obj) {
        DataFetcher dataFetcher = getDataFetcher();
        if (dataFetcher.getProxyById(obj.getId()).isEmpty()) {
            String message = String.format("%s with ID %d doesn't exist", obj.getClass().getName(), obj.getId());
            throw new PersistenceException(message);
        }

        Mapper mapper = getMapper();
        Entity updatedEntity = mapper.toEntity(obj);
        updatedEntity = dataFetcher.save(updatedEntity);
        return mapper.toDTO(updatedEntity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        DataFetcher dataFetcher = getDataFetcher();
        Entity entity = dataFetcher.getById(id)
                .orElseThrow(() -> new PersistenceException(String.format("Entity with ID %d doesn't exist", id)));
        dataFetcher.delete(entity);
    }
}
