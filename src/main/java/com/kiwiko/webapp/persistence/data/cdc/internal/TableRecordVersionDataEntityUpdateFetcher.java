package com.kiwiko.webapp.persistence.data.cdc.internal;

import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.DataEntityUpdateFetcher;
import com.kiwiko.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import com.kiwiko.webapp.persistence.data.versions.api.interfaces.parameters.GetTableRecordVersions;

import javax.inject.Inject;
import java.util.LinkedList;

public class TableRecordVersionDataEntityUpdateFetcher implements DataEntityUpdateFetcher {

    @Inject private TableRecordVersionService tableRecordVersionService;

    @Override
    public LinkedList<TableRecordVersion> getUpdates(String tableName, long id) {
        GetTableRecordVersions query = new GetTableRecordVersions(tableName, id);
        return tableRecordVersionService.getVersions(query);
    }
}
