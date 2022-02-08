package com.kiwiko.jdashboard.webapp.persistence.data.versions.web;

import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks.UserPermissionCheck;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.DataEntityUpdateFetcher;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.jdashboard.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@JdashboardConfigured
@RequestMapping("/table-record-versions/api")
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
@UserPermissionCheck(PermissionNames.ADMIN)
public class TableRecordVersionAPIController {

    @Inject private TableRecordVersionService tableRecordVersionService;
    @Inject private DataEntityUpdateFetcher dataEntityUpdateFetcher;

    @GetMapping("/{id}")
    public TableRecordVersion getById(@PathVariable("id") Long id) {
        return tableRecordVersionService.get(id).orElse(null);
    }

    @GetMapping("/tables/{tableName}/{id}")
    public List<TableRecordVersion> getUpdatesForRecord(
            @PathVariable("tableName") String tableName,
            @PathVariable("id") Long id) {
        return dataEntityUpdateFetcher.getUpdates(tableName, id);
    }
}
