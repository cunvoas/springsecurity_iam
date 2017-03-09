package com.github.cunvoas.iam.metics;

import org.springframework.stereotype.Component;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.github.cunvoas.iam.service.impl.ServiceIamRessourceImpl;

@Component
public class MeticsMonitor {
    
    private final MetricRegistry registry = new MetricRegistry();
    
    private final JmxReporter reporter = JmxReporter.forRegistry(registry).build();
    
    private final Histogram resultCounts = registry.histogram(name(ServiceIamRessourceImpl.class, "result-counts"));
    //resultCounts.update(results.size());
    
    
    public MeticsMonitor () {
        reporter.start();
        
    }
    
    
    private static final String name(Class<?> klass, String measure) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(klass.getSimpleName());
        sBuilder.append(":");
        sBuilder.append(measure);
        return sBuilder.toString();
    }
    
    
}
