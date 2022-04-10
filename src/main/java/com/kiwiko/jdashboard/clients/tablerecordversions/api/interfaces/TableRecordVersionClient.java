package com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

public interface TableRecordVersionClient {

    ClientResponse<CreateTableRecordVersionOutput> create(CreateTableRecordVersionInput input);

    ClientResponse<GetTableRecordVersionOutput> getVersions(GetTableRecordVersions input);

    ClientResponse<GetLastUpdatedOutput> getLastUpdated(GetLastUpdatedInput input);
}
