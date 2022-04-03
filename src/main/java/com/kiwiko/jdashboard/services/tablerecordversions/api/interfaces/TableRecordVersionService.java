package com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces;

import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;

import java.util.LinkedList;
import java.util.Optional;

public interface TableRecordVersionService {

    LinkedList<TableRecordVersion> getVersions(GetTableRecordVersions query);

    Optional<TableRecordVersion> get(long id);

    TableRecordVersion create(TableRecordVersion version);
}