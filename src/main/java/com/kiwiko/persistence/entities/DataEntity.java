package com.kiwiko.persistence.entities;

import com.kiwiko.persistence.identification.Identifiable;

public interface DataEntity extends Identifiable<Long> {

    void save();

    void delete();
}
