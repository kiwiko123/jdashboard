package com.kiwiko.jdashboard.servicerequestkeys.service.interfaces;

public final class ServiceRequestKeyScopes {
    /**
     * Internal requests are service-to-service requests strictly within Jdashboard.
     * The request must originate from Jdashboard and be made to an API in Jdashboard.
     */
    public static final String INTERNAL = "internal";

    /**
     * External requests originate from outside of Jdashboard and are made to an API in Jdashboard.
     */
    public static final String EXTERNAL = "external";

    private ServiceRequestKeyScopes() {}
}
