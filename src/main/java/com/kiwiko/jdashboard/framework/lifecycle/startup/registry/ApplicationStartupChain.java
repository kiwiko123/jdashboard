package com.kiwiko.jdashboard.framework.lifecycle.startup.registry;

import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;

import java.util.Collections;
import java.util.List;

public class ApplicationStartupChain {

    public final List<ApplicationStartupHook> getStartupHooks() {
        return Collections.emptyList();
    }
}
