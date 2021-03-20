package com.kiwiko.webapp.mvc.persistence.crud.api;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.persistence.dataAccess.api.DataEntity;
import com.kiwiko.library.persistence.identification.Identifiable;
import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.mvc.persistence.dataaccess.api.EntityManagerDAO;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class CacheableCreateReadUpdateDeleteService<
        Entity extends DataEntity,
        DTO extends Identifiable<Long>,
        DAO extends EntityManagerDAO<Entity>,
        Mapper extends EntityMapper<Entity, DTO>>
        extends CreateReadUpdateDeleteService<Entity, DTO, DAO, Mapper> {

    protected abstract ObjectCache cache();
    protected abstract TemporalAmount cacheDuration();

    @Transactional(readOnly = true)
    @Override
    public Optional<DTO> read(long id) {
        String key = makeCacheKey(id);
        return obtain(key, () -> super.read(id));
    }

    @Transactional
    @Override
    public <R extends DTO> DTO create(R obj) {
        DTO result = super.create(obj);
        attemptToCacheObject(result);
        return result;
    }

    @Transactional
    @Override
    public <R extends DTO> DTO update(R obj) {
        DTO result = super.update(obj);
        attemptToCacheObject(result);
        return result;
    }

    @Transactional
    @Override
    public void delete(long id) {
        super.delete(id);
        String key = makeCacheKey(id);
        cache().invalidate(key);
    }

    protected <T> T obtain(String key, Supplier<T> fetch) {
        Optional<T> cachedResult = cache().get(key);
        return cachedResult.orElseGet(() -> cache().cache(key, fetch.get(), cacheDuration()));

    }

    private String makeCacheKey(Long id) {
        return String.format("%s.%d", getClass().getName(), id);
    }

    private void attemptToCacheObject(DTO obj) {
        Long id = obj.getId();
        if (id == null) {
            return;
        }
        String key = makeCacheKey(id);
        cache().cache(key, obj, cacheDuration());
    }
}
