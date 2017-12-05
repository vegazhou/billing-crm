package com.kt.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Ticket implements Serializable {
	  private Map data = new HashMap();


	  public HashMap getData() {
	    return (HashMap) data;
	  }

	  public void setData(Map map) {
	    data = map;
	  }
}