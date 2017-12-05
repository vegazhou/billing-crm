package com.kt.biz.customer;

import com.kt.biz.types.AgentType;
import com.kt.entity.mysql.crm.Customer;
import com.kt.exception.CustomerPrimaryFieldMissing;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class CustomerPrimaryFields {

    private String displayName;
    
    private String contactName;
    
    private String contactEmail;
    
    private String contactPhone;

    private CustomerLevel level;

    private boolean isVat;

    private AgentType agentType;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public CustomerLevel getLevel() {
        return level;
    }

    public void setLevel(CustomerLevel level) {
        this.level = level;
    }

    public boolean isVat() {
        return isVat;
    }

    public void setIsVat(boolean isVat) {
        this.isVat = isVat;
    }
    
    

    public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public void fillCustomerEntity(Customer customer) {
        if (StringUtils.isNotBlank(displayName)) {
            customer.setDisplayName(displayName.trim());
        }
        customer.setContactName(contactName);
        customer.setContactEmail(contactEmail);
        customer.setContactPhone(contactPhone);
        customer.setLevel(level.getLevel());
        customer.setIsVat(isVat ? 1 : 0);
        if (agentType == null) {
            customer.setAgentType(AgentType.DIRECT.toString());
        } else {
            customer.setAgentType(agentType.toString());
        }
    }

    public void validate() throws CustomerPrimaryFieldMissing {
        if (StringUtils.isBlank(displayName)) {
            throw new CustomerPrimaryFieldMissing("displayName missing");
        }
        if (StringUtils.isBlank(contactName)) {
            throw new CustomerPrimaryFieldMissing("contactName missing");
        }
        if (StringUtils.isBlank(contactEmail)) {
            throw new CustomerPrimaryFieldMissing("contactEmail missing");
        }
        if (StringUtils.isBlank(contactPhone)) {
            throw new CustomerPrimaryFieldMissing("contactPhone missing");
        }
        
        if (level == null) {
            throw new CustomerPrimaryFieldMissing("level missing");
        }
    }
}
