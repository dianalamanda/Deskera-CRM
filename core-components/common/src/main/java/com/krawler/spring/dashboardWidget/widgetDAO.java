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
package com.krawler.spring.dashboardWidget;

import com.krawler.common.service.ServiceException;
import com.krawler.spring.common.KwlReturnObject;
import java.util.HashMap;

public interface widgetDAO {
    
    /**
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    public KwlReturnObject removeWidgetFromState(HashMap<String, Object> requestParams) throws ServiceException;
    
    /**
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    public KwlReturnObject insertWidgetIntoState(HashMap<String, Object> requestParams) throws ServiceException;
    
    /**
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    public KwlReturnObject changeWidgetState(HashMap<String, Object> requestParams) throws ServiceException;
}
