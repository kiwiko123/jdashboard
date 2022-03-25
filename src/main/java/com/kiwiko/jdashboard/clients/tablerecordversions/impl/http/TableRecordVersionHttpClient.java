package com.kiwiko.jdashboard.clients.tablerecordversions.impl.http;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.TableRecordVersionClient;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests.CreateTableRecordVersionRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class TableRecordVersionHttpClient implements TableRecordVersionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public ClientResponse<CreateTableRecordVersionOutput> create(CreateTableRecordVersionInput input) {
        CreateTableRecordVersionRequest request = new CreateTableRecordVersionRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }

    @Override
    public CompletableFuture<ClientResponse<CreateTableRecordVersionOutput>> createAsync(CreateTableRecordVersionInput input) {
        CreateTableRecordVersionRequest request = new CreateTableRecordVersionRequest(input);
        return jdashboardApiClient.silentAsynchronousCall(request);
    }
}
