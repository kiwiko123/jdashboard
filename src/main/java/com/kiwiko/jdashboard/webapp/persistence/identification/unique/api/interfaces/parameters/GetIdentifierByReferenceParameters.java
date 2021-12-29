package com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters;

public class GetIdentifierByReferenceParameters {

    private String referencedTableName;
    private Long referencedId;

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public GetIdentifierByReferenceParameters setReferencedTableName(String referencedTableName) {
        this.referencedTableName = referencedTableName;
        return this;
    }

    public Long getReferencedId() {
        return referencedId;
    }

    public GetIdentifierByReferenceParameters setReferencedId(Long referencedId) {
        this.referencedId = referencedId;
        return this;
    }
}
