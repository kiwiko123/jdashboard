package com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

public interface TableRecordVersionService {

    LinkedList<TableRecordVersion> getVersions(GetTableRecordVersions query);

    Set<TableRecordVersion> getLastUpdated(GetLastUpdatedInput input);

    Optional<TableRecordVersion> get(long id);

    TableRecordVersion create(TableRecordVersion version);
}
