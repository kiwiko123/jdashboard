package com.kiwiko.jdashboard.framework.data;

import lombok.Data;

@Data
public abstract class DataEntityDTO<T> {
    private T id;
}
