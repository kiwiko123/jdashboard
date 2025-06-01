package com.kiwiko.jdashboard.tablerecordversions.service.api.interfaces;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.tablerecordversions.service.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.tablerecordversions.service.api.interfaces.parameters.GetTableRecordVersions;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

public interface TableRecordVersionService {

    LinkedList<TableRecordVersion> getVersions(GetTableRecordVersions query);

    Set<TableRecordVersion> getLastUpdated(GetLastUpdatedInput input);

    Optional<TableRecordVersion> get(long id);

    TableRecordVersion create(TableRecordVersion version);
}
