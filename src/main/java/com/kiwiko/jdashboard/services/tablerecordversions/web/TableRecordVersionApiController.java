package com.kiwiko.jdashboard.services.tablerecordversions.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.services.tablerecordversions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.TableRecordVersionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@JdashboardConfigured
@RequestMapping("/table-record-versions/public-api")
@UserPermissionCheck(PermissionNames.ADMIN)
public class TableRecordVersionApiController {

    @Inject private TableRecordVersionService tableRecordVersionService;

    @GetMapping("/{id}")
    public TableRecordVersion getById(@PathVariable("id") Long id) {
        return tableRecordVersionService.get(id).orElse(null);
    }

    @GetMapping("/tables/{tableName}/{id}")
    public List<TableRecordVersion> getUpdatesForRecord(
            @PathVariable("tableName") String tableName,
            @PathVariable("id") Long id) {
        GetTableRecordVersions getTableRecordVersions = new GetTableRecordVersions(tableName, id);
        return tableRecordVersionService.getVersions(getTableRecordVersions);
    }
}
