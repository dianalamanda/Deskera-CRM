/*
 * Copyright (C) 2012  Krawler Information Systems Pvt Ltd
 * All rights reserved.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package com.krawler.common.admin; 

import java.util.Date;

public class AuditTrail {
    private String ID;
    private String details;
    private String IPAddress;
    private User user;
    private AuditAction action;
//    private Date auditTime;
    private Long audittime;
    private String recid;
    //@@@ - extraid is used in crm only.
    private String extraid;
    
    private Long userId;
    
    private Long companyId;
    
    private Long auditGroupId;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public Long getAudittime() {
        return audittime;
    }

    public void setAudittime(Long audittime) {
        this.audittime = audittime;
    }


    public Date getAuditTime() {
        if(audittime!=null)
            return new Date(audittime);
        return null;
    }

    public void setAuditTime(Date auditTime) {
        this.audittime = auditTime.getTime();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getExtraid() {
        return extraid;
    }

    public void setExtraid(String extraid) {
        this.extraid = extraid;
    }

    /**
     * @return the userId
     */
    public Long getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    /**
     * @return the companyId
     */
    public Long getCompanyId()
    {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(Long companyId)
    {
        this.companyId = companyId;
    }

    /**
     * @return the auditGroupId
     */
    public Long getAuditGroupId()
    {
        return auditGroupId;
    }

    /**
     * @param auditGroupId the auditGroupId to set
     */
    public void setAuditGroupId(Long auditGroupId)
    {
        this.auditGroupId = auditGroupId;
    }
}
