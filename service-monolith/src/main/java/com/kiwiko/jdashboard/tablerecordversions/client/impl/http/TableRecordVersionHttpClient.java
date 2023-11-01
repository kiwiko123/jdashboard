package com.kiwiko.jdashboard.tablerecordversions.client.impl.http;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.tablerecordversions.client.impl.http.requests.CreateTableRecordVersionRequest;
import com.kiwiko.jdashboard.tablerecordversions.client.impl.http.requests.GetLastUpdatedVersionsRequest;
import com.kiwiko.jdashboard.tablerecordversions.client.impl.http.requests.GetTableRecordVersionsRequest;
import com.kiwiko.jdashboard.tablerecordversions.service.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class TableRecordVersionHttpClient implements TableRecordVersionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private GsonProvider gsonProvider;

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

    @Override
    public ClientResponse<GetLastUpdatedOutput> getLastUpdated(GetLastUpdatedInput input) {
        List<String> serializedVersionRecords = input.getVersionRecords().stream()
                .map(gsonProvider.getDefault()::toJson)
                .collect(Collectors.toList());
        GetLastUpdatedVersionsRequest request = new GetLastUpdatedVersionsRequest(serializedVersionRecords);
        return jdashboardApiClient.silentSynchronousCall(request);
    }
}
