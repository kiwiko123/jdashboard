package com.kiwiko.jdashboard.services.tablerecordversions.web;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.TableRecordVersionService;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/table-record-versions/service-api")
@JdashboardConfigured
@InternalServiceCheck
public class TableRecordVersionServiceApiController {

    @Inject private TableRecordVersionService tableRecordVersionService;

    @GetMapping("/{table}/{id}")
    public GetTableRecordVersionOutput query(
            @PathVariable("table") String tableName,
            @PathVariable("id") long recordId) {
        GetTableRecordVersions input = new GetTableRecordVersions(tableName, recordId);
        List<TableRecordVersion> tableRecordVersions = tableRecordVersionService.getVersions(input);

        GetTableRecordVersionOutput response = new GetTableRecordVersionOutput();
        response.setTableRecordVersions(tableRecordVersions);
        return response;
    }

    @PostMapping("")
    public CreateTableRecordVersionOutput create(@RequestBody CreateTableRecordVersionInput input) {
        TableRecordVersion tableRecordVersion = input.getTableRecordVersion();
        Objects.requireNonNull(tableRecordVersion, "Table record version is required");

        TableRecordVersion createdVersion = tableRecordVersionService.create(tableRecordVersion);
        CreateTableRecordVersionOutput output = new CreateTableRecordVersionOutput();
        output.setTableRecordVersion(createdVersion);

        return output;
    }
}
