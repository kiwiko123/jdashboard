package com.kiwiko.jdashboard.framework.data;

import com.kiwiko.jdashboard.langutils.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.langutils.reflection.properties.api.BidirectionalFieldMapper;
import com.kiwiko.jdashboard.langutils.reflection.properties.api.errors.PropertyMappingException;

public abstract class DataEntityFieldMapper<E extends LongDataEntity, D extends LongDataEntityDTO>
    extends BidirectionalFieldMapper<E, D>
    implements DataEntityMapper<E, D> {

    private final ReflectionHelper reflectionHelper;

    protected DataEntityFieldMapper() {
        this.reflectionHelper = new ReflectionHelper();
    }

    @Override
    public void copyToEntity(D sourceDto, E targetEntity) {
        copyToSource(sourceDto, targetEntity);
    }

    @Override
    public E toEntity(D sourceDto) {
        return toSourceType(sourceDto);
    }

    @Override
    public void copyToDto(E sourceEntity, D targetDto) {
        copyToTarget(sourceEntity, targetDto);
    }

    @Override
    public D toDto(E sourceEntity) {
        return toTargetType(sourceEntity);
    }

    /**
     * @deprecated prefer {@link #copyToEntity(LongDataEntityDTO, LongDataEntity)}
     */
    @Deprecated
    public void copyToSource(D source, E destination) {
        super.copyToSource(source, destination);
    }

    /**
     * @deprecated prefer {@link #toEntity(LongDataEntityDTO)}
     */
    @Override
    @Deprecated
    public E toSourceType(D source) {
        return super.toSourceType(source);
    }

    /**
     * @deprecated prefer {@link #copyToDto(LongDataEntity, LongDataEntityDTO)}
     */
    @Override
    @Deprecated
    public void copyToTarget(E source, D destination) throws PropertyMappingException {
        super.copyToTarget(source, destination);
    }

    /**
     * @deprecated prefer {@link #toDto(LongDataEntity)}
     */
    @Override
    @Deprecated
    public D toTargetType(E source) throws PropertyMappingException {
        return super.toTargetType(source);
    }
}
