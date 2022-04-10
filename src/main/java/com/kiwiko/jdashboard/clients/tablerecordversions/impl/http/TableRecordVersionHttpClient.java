package com.kiwiko.jdashboard.clients.tablerecordversions.impl.http;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests.CreateTableRecordVersionRequest;
import com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests.GetTableRecordVersionsRequest;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class TableRecordVersionHttpClient implements TableRecordVersionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public ClientResponse<CreateTableRecordVersionOutput> create(CreateTableRecordVersionInput input) {
        CreateTableRecordVersionRequest request = new CreateTableRecordVersionRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public ClientResponse<GetTableRecordVersionOutput> getVersions(GetTableRecordVersions input) {
        GetTableRecordVersionsRequest request = new GetTableRecordVersionsRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }
}
