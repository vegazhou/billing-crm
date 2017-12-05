package com.kt.auth;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;


public class SessionTicket {
	private static Logger logger = Logger.getLogger(SessionTicket.class.getName());
	
    protected long createTime = 0;
    protected long lifeBeginTime = 0;
    protected long timeToLive = 5400;
    protected String ticket = null;
    
    /** The ticket id. */
    protected String id;    

    /* rawTicket */
    protected byte[] rawTicket = null;

    public byte[] getRawTicket() {
		return rawTicket;
	}

	public void setRawTicket(byte[] rawTicket) {
		this.rawTicket = rawTicket;
	}


	/* verify info */
    protected String verifyInfo = "";

    
    /**
     * Constructs a new Ticket
     */
    public SessionTicket() {
        this.createTime = System.currentTimeMillis();
        this.lifeBeginTime = createTime;
    }

    /**
     * Constructs a new Ticket with basic ticket attributes
     *
     * @param version the version for the ticket
     * @param type the ticketType
     * @param createTime the creation time
     * @param numberofuses
     * @param timetolive
     */
    public SessionTicket(long createTime, long timeToLive) {
        this.createTime = createTime;
        this.lifeBeginTime = createTime;
        this.timeToLive = timeToLive;
        
    }

	public final long getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public final long getTimeToLive() {
		return timeToLive;
	}

	public final void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}


	public final boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public final String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public synchronized String getTicket() {
    	if (ticket == null) {
    		try {
    			return TicketUtil.generateTicket(this);
    		} catch (Exception e) {
    			return "";
    		} 
    	} else {
    		return ticket;
    	}  	
    }


	public final long getStartTime() {
		return lifeBeginTime;
	}

	public final void resetStartTime(long lifeBeginTime) {
		this.lifeBeginTime = lifeBeginTime;
		this.rawTicket = null;
	}

    
    public boolean isExpired() {
    	// check expired or not
    	if (timeToLive > 0) {
			long now = System.currentTimeMillis();
			if (lifeBeginTime + (timeToLive * 1000L) < now) {
                logger.error("ticket is expired: now is:" + now+",life begin time is "+lifeBeginTime+" and ttl is "+timeToLive);
				return true;
			}
    	}
    	return false;
    }  	
}
