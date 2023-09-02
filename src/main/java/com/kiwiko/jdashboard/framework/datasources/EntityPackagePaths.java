package com.kiwiko.jdashboard.framework.datasources;

import java.util.Set;

/**
 * TODO infer this at application startup through reflection.
 */
public final class EntityPackagePaths {
    public static final Set<String> DEFAULT_DATASOURCE_PACKAGE_PATHS = Set.of(
            "com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal.data",
            "com.kiwiko.jdashboard.webapp.framework.requests.internal.dataAccess",
            "com.kiwiko.jdashboard.webapp.persistence.identification.unique.internal.data",
            "com.kiwiko.jdashboard.webapp.apps.grocerylist.internal.data",
            "com.kiwiko.jdashboard.webapp.apps.chatroom.internal.core.data.entities",
            "com.kiwiko.jdashboard.webapp.apps.games.state.internal.data",
            "com.kiwiko.jdashboard.webapp.apps.games.scrabble.words.internal.dataAccess",
            "com.kiwiko.jdashboard.webapp.notifications.internal.dataaccess",
            "com.kiwiko.jdashboard.services.usercredentials.internal.data",
            "com.kiwiko.jdashboard.permissions.service.internal.data",
            "com.kiwiko.jdashboard.sessions.service.internal.data",
            "com.kiwiko.jdashboard.services.users.internal.data",
            "com.kiwiko.jdashboard.tablerecordversions.service.internal.data",
            "com.kiwiko.jdashboard.featureflags.service.internal.data",
            "com.kiwiko.jdashboard.servicerequestkeys.service.internal.data",
            "com.kiwiko.jdashboard.timeline.events.service.internal.data");

    public static final Set<String> FRAMEWORK_INTERNAL_DATASOURCE_PACKAGE_PATHS = Set.of(
            "com.kiwiko.jdashboard.webapp.application.events.internal.data",
            "com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data");
}
