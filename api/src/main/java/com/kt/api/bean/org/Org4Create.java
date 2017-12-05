package com.kt.api.bean.org;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Org4Create
 */
public class Org4Create {
    @JsonIgnore
    private String pid;

    @Size(min = 4, max = 100, message = "apis.org.validation.code.Size")
    //@NotBlank(message = "apis.org.validation.code.NotBlank")
    private String code;

    @Size(min = 0, max = 100, message = "apis.org.validation.orgName.Size")
    @NotBlank(message = "apis.org.validation.orgName.NotBlank")
    private String orgName;

    @Size(min = 0, max = 100, message = "apis.org.validation.siteName.Size")
    //@NotBlank(message = "apis.org.validation.siteName.NotBlank")
    private String siteName;

    @Size(min = 0, max = 200, message = "apis.org.validation.domain.Size")
    private String domain;

    @Size(min = 0, max = 500, message = "apis.org.validation.logoUrl.Size")
    private String logoUrl;

    @Size(min = 0, max = 36, message = "apis.org.validation.provinceId.Size")
    //@NotBlank(message = "apis.org.validation.provinceId.NotBlank")
    private String provinceId;

    @Size(min = 0, max = 36, message = "apis.org.validation.cityId.Size")
    //@NotBlank(message = "apis.org.validation.cityId.NotBlank")
    private String cityId;

    @Size(min = 0, max = 500, message = "apis.org.validation.address.Size")
    private String address;

    @Size(min = 0, max = 50, message = "apis.org.validation.postCode.Size")
    private String postCode;

    @Size(min = 0, max = 200, message = "apis.org.validation.homePage.Size")
    private String homePage;

    @Size(min = 0, max = 36, message = "apis.org.validation.industryId.Size")
    //@NotBlank(message = "apis.org.validation.industryId.NotBlank")
    private String industryId;

    @Size(min = 0, max = 36, message = "apis.org.validation.employeeScale.Size")
    //@NotBlank(message = "apis.org.validation.employeeScale.NotBlank")
    private String employeeScale;

    @Size(min = 0, max = 200, message = "apis.org.validation.contactName.Size")
    private String contactName;

    @Size(min = 0, max = 100, message = "apis.org.validation.contactPhone.Size")
    //@NotBlank(message = "apis.org.validation.contactPhone.NotBlank")
    private String contactPhone;

    @Size(min = 0, max = 100, message = "apis.org.validation.contactEmail.Size")
    private String contactEmail;
    
    @Size(min = 0, max = 100, message = "apis.org.validation.finAdminEmail.Size")
    private String finAdminEmail;
    
    @Size(min = 0, max = 100, message = "apis.org.validation.finAdminName.Size")
    private String finAdminName;
    
    @Size(min = 0, max = 100, message = "apis.org.validation.crmCustomerId.Size")
    private String crmCustomerId;

    @JsonIgnore
    private int status = 1;

    private int userService = 0;

    @JsonIgnore
    private int type;

    @Size(min = 0, max = 36, message = "apis.org.validation.sourceId.Size")
    private String sourceId;

    @JsonIgnore
    private String creator;

    @JsonIgnore
    private String createTime;

    @JsonIgnore
    private String updater;

    @JsonIgnore
    private String updateTime;

    @Size(min = 6, max = 6, message = "apis.org.validation.captcha.Size")
    //@NotBlank(message = "apis.org.validation.captcha.NotBlank")
    private String captcha;
    
    
    @Valid
	private List<String> service4Create;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getEmployeeScale() {
        return employeeScale;
    }

    public void setEmployeeScale(String employeeScale) {
        this.employeeScale = employeeScale;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

	public List<String> getService4Create() {
		return service4Create;
	}

	public void setService4Create(List<String> service4Create) {
		this.service4Create = service4Create;
	}

	public int getUserService() {
		return userService;
	}

	public void setUserService(int userService) {
		this.userService = userService;
	}

	public String getFinAdminEmail() {
		return finAdminEmail;
	}

	public void setFinAdminEmail(String finAdminEmail) {
		this.finAdminEmail = finAdminEmail;
	}

	public String getFinAdminName() {
		return finAdminName;
	}

	public void setFinAdminName(String finAdminName) {
		this.finAdminName = finAdminName;
	}

	public String getCrmCustomerId() {
		return crmCustomerId;
	}

	public void setCrmCustomerId(String crmCustomerId) {
		this.crmCustomerId = crmCustomerId;
	}
    
    
}
