package com.kiwiko.webapp.mvc.persistence.crud.api;

import com.kiwiko.library.persistence.dataAccess.api.DataEntity;
import com.kiwiko.library.persistence.dataAccess.api.EntityManagerDAO;
import com.kiwiko.library.persistence.dataAccess.api.PersistenceException;
import com.kiwiko.library.persistence.identification.Identifiable;
import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class CreateReadUpdateDeleteService<
        Entity extends DataEntity,
        DTO extends Identifiable<Long>,
        DAO extends EntityManagerDAO<Entity>,
        Mapper extends EntityMapper<Entity, DTO>>
        implements CreateReadUpdateDeleteAPI<DTO> {

    protected abstract DAO dataAccessObject();

    protected abstract Mapper mapper();

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> get(long id) {
        return read(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> read(long id) {
        DAO dao = dataAccessObject();
        Mapper mapper = mapper();

        return dao.getById(id)
                .map(mapper::toDTO);
    }

    @Transactional
    @Override
    public <R extends DTO> DTO create(R obj) {
        DAO dao = dataAccessObject();
        Mapper mapper = mapper();

        Entity entity = mapper.toEntity(obj);
        entity = dao.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    @Override
    public <R extends DTO> DTO update(R obj) {
        DAO dao = dataAccessObject();
        Mapper mapper = mapper();

        if (!dao.getProxyById(obj.getId()).isPresent()) {
            String message = String.format("%s with ID %d doesn't exist", obj.getClass().getName(), obj.getId());
            throw new PersistenceException(message);
        }

        Entity updatedEntity = mapper.toEntity(obj);
        updatedEntity = dao.save(updatedEntity);
        return mapper.toDTO(updatedEntity);
    }

    @Transactional
    @Override
    public void delete(long id) {
        DAO dao = dataAccessObject();
        Entity entity = dao.getById(id)
                .orElseThrow(() -> new PersistenceException(String.format("Entity with ID %d doesn't exist", id)));
        dao.delete(entity);
    }
}
