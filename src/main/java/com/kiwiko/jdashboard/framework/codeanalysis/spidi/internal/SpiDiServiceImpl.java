package com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ResolveDependenciesInput;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanConfigurationRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.BeanDependencyRegistry;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.dto.ConfigurationRegistry;

import javax.inject.Inject;

public class SpiDiServiceImpl implements SpiDiService {

    @Inject private DependencyConfigurationAnalyzer dependencyConfigurationAnalyzer;
    @Inject private DependencyResolver dependencyResolver;

    @Override
    public void resolveConfigurationDependencies(ResolveDependenciesInput input) throws SpiDiException {
        ConfigurationRegistry configurationRegistry = dependencyConfigurationAnalyzer.buildConfigurationRegistry();
        BeanConfigurationRegistry beanConfigurationRegistry = dependencyConfigurationAnalyzer.buildBeanConfigurationRegistry(configurationRegistry);
        BeanDependencyRegistry beanDependencyRegistry = dependencyConfigurationAnalyzer.buildBeanDependencyRegistry(beanConfigurationRegistry);

        dependencyResolver.resolveDependencies(
                input,
                configurationRegistry,
                beanConfigurationRegistry,
                beanDependencyRegistry);
    }
}
