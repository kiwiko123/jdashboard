package com.kiwiko.jdashboard.services.tablerecordversions.web;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedInput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.VersionRecord;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.TableRecordVersionService;
import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardServiceClientIdentifiers;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/table-record-versions/service-api")
@JdashboardConfigured
@LockedApi(JdashboardServiceClientIdentifiers.DEFAULT)
public class TableRecordVersionServiceApiController {

    @Inject private TableRecordVersionService tableRecordVersionService;
    @Inject private GsonProvider gsonProvider;

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

    @GetMapping("/last-updated")
    public GetLastUpdatedOutput getLastUpdated(
            @RequestParam("vr") List<String> versionRecords) {
        Set<VersionRecord> decodedVersionRecords = versionRecords.stream()
                .map(value -> URLDecoder.decode(value, StandardCharsets.UTF_8))
                .map(value -> gsonProvider.getDefault().fromJson(value, VersionRecord.class))
                .collect(Collectors.toSet());
        GetLastUpdatedInput input = new GetLastUpdatedInput();
        input.setVersionRecords(decodedVersionRecords);

        Set<TableRecordVersion> lastUpdatedVersions = tableRecordVersionService.getLastUpdated(input);
        GetLastUpdatedOutput output = new GetLastUpdatedOutput();
        output.setLastUpdatedVersions(lastUpdatedVersions);
        return output;
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
