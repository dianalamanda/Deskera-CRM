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
package com.krawler.workflow.module.bizservice;

import java.io.Serializable;

import com.krawler.workflow.module.dao.ModuleBuilderDao;

/**
 * @author Ashutosh
 *
 */
public class ModuleBuilderServiceImpl implements ModuleBuilderService
{

    private ModuleBuilderDao moduleBuilderDao;
    
    /* (non-Javadoc)
     * @see com.krawler.workflow.module.bizservice.ModuleBuilderService#undeployModule(java.lang.String)
     */
    public void undeployModule(String moduleId)
    {
        getModuleBuilderDao().undeployModule(moduleId);
    }

    /**
     * @return the moduleBuilderDao
     */
    public ModuleBuilderDao getModuleBuilderDao()
    {
        return moduleBuilderDao;
    }

    /**
     * @param moduleBuilderDao the moduleBuilderDao to set
     */
    public void setModuleBuilderDao(ModuleBuilderDao moduleBuilderDao)
    {
        this.moduleBuilderDao = moduleBuilderDao;
    }
    
    public Object getMBDocById(Class entityClass, Serializable id){
        return moduleBuilderDao.get(entityClass, id);
    }

}
