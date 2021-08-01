package com.kiwiko.webapp.persistence.data.versions.web;

import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.persistence.data.cdc.api.interfaces.DataEntityUpdateFetcher;
import com.kiwiko.webapp.persistence.data.versions.api.dto.TableRecordVersion;
import com.kiwiko.webapp.persistence.data.versions.api.interfaces.TableRecordVersionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@CrossOriginConfigured
@RequestMapping("/table-record-versions/api")
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
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
