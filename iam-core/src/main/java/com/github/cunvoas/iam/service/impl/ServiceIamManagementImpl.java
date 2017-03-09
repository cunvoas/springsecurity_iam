package com.github.cunvoas.iam.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.github.cunvoas.iam.service.ServiceIamManagement;

@Component
@ManagedResource(
        objectName="bean:name=iam-service", 
        description="IAM : Search Service "
        )
public class ServiceIamManagementImpl implements ServiceIamManagement {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIamManagementImpl.class);
    
    protected int minimumStringLengthForSearch=3;
    
    /**
     * Setter for minimumStringLenghtForSearch.
     * @param minimumStringLenghtForSearch the minimumStringLenghtForSearch to set
     */
    @ManagedAttribute(description="Set the minimum length for search criteria")
    public void setMinimumStringLengthForSearch( String minimumLenghtForSearch ) {
        try {
            int mini = Integer.parseInt(minimumLenghtForSearch);
            
            // 2 minimum
            if (mini>2) {
                this.minimumStringLengthForSearch = mini;
            }
        } catch (NumberFormatException e) {
            LOGGER.error("JMX : bad numeric format", e);
        }
    }

    /**
     * @see com.github.cunvoas.iam.service.impl.ServiceIamManagement#getMinimumStringLengthForSearch()
     */
    @Override
    public int getMinimumStringLengthForSearch() {
        return minimumStringLengthForSearch;
    }

    
    
}
