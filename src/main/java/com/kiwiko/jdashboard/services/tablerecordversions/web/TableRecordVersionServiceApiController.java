package com.kiwiko.jdashboard.services.tablerecordversions.web;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.TableRecordVersionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

@RestController
@RequestMapping("/table-record-versions/service-api")
@JdashboardConfigured
@InternalServiceCheck
public class TableRecordVersionServiceApiController {

    @Inject private TableRecordVersionService tableRecordVersionService;

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
