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
package com.krawler.crm.database.tables;
// Generated Jul 9, 2009 7:57:07 PM by Hibernate Tools 3.2.1.GA

/**
 * CrmCombodata generated by hbm2java
 */
public class CrmCombodata  implements java.io.Serializable {


     private String valueid;
     private CrmCombomaster crmCombomaster;
     private String rawvalue;
     private String parentid;
//     private String aliasname;
     private int isEdit;
     private int itemsequence;
     private String percentStage;
     
    public CrmCombodata() {
    }


    public CrmCombodata(String valueid) {
        this.valueid = valueid;
    }
    public CrmCombodata(String valueid, CrmCombomaster crmCombomaster, String rawvalue, String parentid, int isEdit ,String percentStage) {
       this.valueid = valueid;
       this.crmCombomaster = crmCombomaster;
       this.rawvalue = rawvalue;
       this.parentid = parentid;
//       this.aliasname = aliasname;
       this.isEdit =isEdit;
       this.percentStage = percentStage;
       
    }

    public int isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }

    public String getValueid() {
        return this.valueid;
    }

    public void setValueid(String valueid) {
        this.valueid = valueid;
    }
    public CrmCombomaster getCrmCombomaster() {
        return this.crmCombomaster;
    }

    public void setCrmCombomaster(CrmCombomaster crmCombomaster) {
        this.crmCombomaster = crmCombomaster;
    }
    public String getRawvalue() {
        return this.rawvalue;
    }

    public void setRawvalue(String rawvalue) {
        this.rawvalue = rawvalue;
    }
    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
//    public String getAliasname() {
//        return this.aliasname;
//    }
//
//    public void setAliasname(String aliasname) {
//        this.aliasname = aliasname;
//    }
    public String getPercentStage() {
        return percentStage;
    }

    public void setPercentStage(String percentStage) {
        this.percentStage = percentStage;
    }
    public int getItemsequence() {
		return itemsequence;
	}
	public void setItemsequence(int itemsequence) {
		this.itemsequence = itemsequence;
	}

}
