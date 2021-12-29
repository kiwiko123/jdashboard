package com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters;

public class CreateUuidParameters {

    private String referenceKey;
    private UniqueIdentifierCreationStrategy uniqueIdentifierCreationStrategy;

    public CreateUuidParameters(String referenceKey) {
        this.referenceKey = referenceKey;
        uniqueIdentifierCreationStrategy = UniqueIdentifierCreationStrategy.DEFAULT;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public UniqueIdentifierCreationStrategy getUniqueIdentifierCreationStrategy() {
        return uniqueIdentifierCreationStrategy;
    }

    public void setUniqueIdentifierCreationStrategy(UniqueIdentifierCreationStrategy uniqueIdentifierCreationStrategy) {
        this.uniqueIdentifierCreationStrategy = uniqueIdentifierCreationStrategy;
    }
}
