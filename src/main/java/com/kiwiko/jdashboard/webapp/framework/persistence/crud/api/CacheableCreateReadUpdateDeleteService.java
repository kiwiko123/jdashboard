package com.kiwiko.jdashboard.webapp.framework.persistence.crud.api;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.persistence.dataAccess.api.DataEntity;
import com.kiwiko.jdashboard.library.persistence.identification.Identifiable;
import com.kiwiko.jdashboard.library.persistence.properties.api.EntityMapper;
import com.kiwiko.jdashboard.webapp.framework.persistence.dataaccess.api.EntityManagerDAO;
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

    protected abstract ObjectCache getCache();
    protected abstract TemporalAmount getCacheDuration();

    @Override
    public Optional<DTO> read(long id) {
        String key = makeCacheKey(id);
        return obtain(key, () -> super.read(id).orElse(null));
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
        getCache().invalidate(key);
    }

    protected <T> Optional<T> obtain(String key, Supplier<T> fetch) {
        ObjectCache cache = getCache();
        Optional<T> cachedResult = cache.get(key);
        return cachedResult.or(() -> Optional.of(cache.cache(key, fetch.get(), getCacheDuration())));
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
        getCache().cache(key, obj, getCacheDuration());
    }
}
