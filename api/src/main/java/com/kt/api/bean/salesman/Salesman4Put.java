package com.kt.api.bean.salesman;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/6/2.
 */
public class Salesman4Put {
    @Size(min = 0, max = 20, message = "salesman.name.Size")
    @NotBlank(message = "salesman.name.NotBlank")
    private String name;

    @Size(min = 0, max = 50, message = "salesman.email.Size")
    @NotBlank(message = "salesman.email.NotBlank")
    private String email;
    
    private int enabled=1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
    
    
}
