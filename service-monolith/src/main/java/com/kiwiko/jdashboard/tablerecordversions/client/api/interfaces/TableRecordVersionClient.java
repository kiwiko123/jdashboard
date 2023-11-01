package com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.tablerecordversions.service.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;

public interface TableRecordVersionClient {

    ClientResponse<CreateTableRecordVersionOutput> create(CreateTableRecordVersionInput input);

    ClientResponse<GetTableRecordVersionOutput> getVersions(GetTableRecordVersions input);

    ClientResponse<GetLastUpdatedOutput> getLastUpdated(GetLastUpdatedInput input);
}
