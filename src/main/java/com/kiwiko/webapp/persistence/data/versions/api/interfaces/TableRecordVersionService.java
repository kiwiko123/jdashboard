package com.kiwiko.webapp.persistence.data.versions.api.interfaces;

import com.kiwiko.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.webapp.persistence.data.versions.api.interfaces.parameters.GetTableRecordVersions;

import java.util.LinkedList;

public interface TableRecordVersionService {

    LinkedList<TableRecordVersion> getVersions(GetTableRecordVersions query);

    TableRecordVersion create(TableRecordVersion version);
}
