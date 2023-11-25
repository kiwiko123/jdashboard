package com.kiwiko.jdashboard.framework.data;

public interface DataEntityMapper<E extends LongDataEntity, D extends LongDataEntityDTO> {

    void copyToEntity(D sourceDto, E targetEntity);

    E toEntity(D sourceDto);

    void copyToDto(E sourceEntity, D targetDto);

    D toDto(E sourceEntity);
}
