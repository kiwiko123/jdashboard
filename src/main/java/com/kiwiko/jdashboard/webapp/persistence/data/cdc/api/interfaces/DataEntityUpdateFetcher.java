package com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces;

import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.dto.TableRecordVersion;

import java.util.LinkedList;

public interface DataEntityUpdateFetcher {

    LinkedList<TableRecordVersion> getUpdates(String tableName, long id);
}
