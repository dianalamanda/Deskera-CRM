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
package com.krawler.crm.savedsearch.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.krawler.common.admin.SavedSearchQuery;
import com.krawler.common.service.ServiceException;
import com.krawler.utils.json.base.JSONException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.krawler.common.session.SessionExpiredException;
import com.krawler.common.util.Constants;
import com.krawler.common.util.StringUtil;
import com.krawler.crm.savedsearch.bizservice.SavedSearchService;

import com.krawler.spring.sessionHandler.sessionHandlerImpl;
import com.krawler.utils.json.base.JSONObject;
import static com.krawler.crm.savedsearch.web.SavedSearchConstants.*;

public class SavedSearchController extends MultiActionController {

    private String successView;
    private sessionHandlerImpl sessionHandlerImplObj;
    private SavedSearchService SaveSearchServiceObj;
    private SavedSearchJson SaveSearchJsonObj;
    
    private static Log LOG = LogFactory.getLog(SavedSearchController.class);

    public void setsessionHandlerImpl(sessionHandlerImpl sessionHandlerImpl1) {
        this.sessionHandlerImplObj = sessionHandlerImpl1;
    }

    public void setSaveSearchService(SavedSearchService SaveSearchServiceObj) {
        this.SaveSearchServiceObj = SaveSearchServiceObj;
    }

    public void setSaveSearchJson(SavedSearchJson SaveSearchJsonObj) {
        this.SaveSearchJsonObj = SaveSearchJsonObj;
    }

    public String getSuccessView() {
        return successView;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public ModelAndView getSavedSearchQueries(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jobj = null;
        try {
            String userId = sessionHandlerImpl.getUserid(request);
            int firstResult = Integer.parseInt(request.getParameter(Constants.start));
            int maxResults = Integer.parseInt(request.getParameter(Constants.limit));
            List<SavedSearchQuery> ll = SaveSearchServiceObj.getSavedSearchQueries(userId, firstResult, maxResults);
            jobj = SaveSearchJsonObj.getSavedSearchQueries(ll, SaveSearchServiceObj.getSavedSearchQueries(userId));
        } catch (ServiceException ex) {
            LOG.info(ex.getMessage(), ex);
        } catch (JSONException ex) {
            LOG.info(ex.getMessage(), ex);
        } catch (SessionExpiredException e) {
            LOG.info(e.getMessage(), e);
        }
        return new ModelAndView("jsonView", Constants.model, jobj.toString());
    }

    public ModelAndView getSavedSearchQuery(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jobj = null;
        try {
            String searchid = request.getParameter(JSON_searchid);
            SavedSearchQuery SavedSearchQueryObj = SaveSearchServiceObj.getSavedSearchQuery(searchid);
            jobj = SaveSearchJsonObj.getSavedSearchQuery(SavedSearchQueryObj);
        } catch (ServiceException ex) {
            LOG.info(ex.getMessage(), ex);
        } catch (JSONException ex) {
            LOG.info(ex.getMessage(), ex);
        }
        return new ModelAndView("jsonView", Constants.model, jobj.toString());
    }
    public ModelAndView deleteSavedSearchQuery(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jobj = null;
        boolean success = false;
        try {
            String searchid = request.getParameter(JSON_searchid);
            success = SaveSearchServiceObj.deleteSavedSearchQuery(searchid);
        } catch (ServiceException ex) {
            LOG.info(ex.getMessage(), ex);
        } finally {
            try {
                jobj = SaveSearchJsonObj.deleteSavedSearchQuery(success);
            } catch (JSONException ex) {
                Logger.getLogger(SavedSearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ModelAndView("jsonView", Constants.model, jobj.toString());
    }
    public ModelAndView saveSearchQuery(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jobj1 = new JSONObject();
        try {
            int module = Integer.parseInt(request.getParameter(JSON_module));
            String userId = sessionHandlerImpl.getUserid(request);
            String searchname = request.getParameter(JSON_searchname);
            String searchquery = request.getParameter(JSON_searchstate);
            String confirmationFlag = request.getParameter("confirmationFlag");
            List<SavedSearchQuery> ll=SaveSearchServiceObj.getSavedSearchQueries(userId, searchname);
            if(ll.size()>0 && StringUtil.isNullOrEmpty(confirmationFlag)){
                jobj1.put("msg", "Saved search with same name already exists. Do you want to overwrite it?");
               
            } else {

                SavedSearchQuery SavedSearchQueryObj =  SaveSearchServiceObj.saveSearchQuery(module, userId, searchquery, searchname);
                jobj1 = SaveSearchJsonObj.saveSearchQuery(SavedSearchQueryObj);

            }

        } catch (ServiceException ex) {
            LOG.info(ex.getMessage(), ex);
        } catch (JSONException ex) {
            LOG.info(ex.getMessage(), ex);
        } catch (SessionExpiredException e) {
            LOG.info(e.getMessage(), e);
        }
        return new ModelAndView("jsonView", Constants.model, jobj1.toString());
    }
}
