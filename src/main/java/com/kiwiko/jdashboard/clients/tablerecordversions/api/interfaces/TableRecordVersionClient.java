package com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

public interface TableRecordVersionClient {

    ClientResponse<CreateTableRecordVersionOutput> create(CreateTableRecordVersionInput input);
}
