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
package com.krawler.crm.quotation;

import com.krawler.common.admin.Company;
import com.krawler.common.admin.Projreport_Template;
import com.krawler.common.service.ServiceException;
import com.krawler.common.util.KWLErrorMsgs;
import com.krawler.common.util.StringUtil;
import com.krawler.crm.database.tables.CrmProduct;
import com.krawler.crm.database.tables.Quotation;
import com.krawler.crm.database.tables.QuotationDetail;
import com.krawler.crm.database.tables.Tax;
import com.krawler.crm.utils.Constants;
import com.krawler.dao.BaseDAO;
import com.krawler.spring.common.KwlReturnObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.DataAccessException;

public class QuotationDAOImpl  extends BaseDAO implements QuotationDAO {
    
    public KwlReturnObject saveQuotation(HashMap<String, Object> dataMap) throws ServiceException {
        List list = new ArrayList();
        try {
            String soid = (String) dataMap.get("id");

            Quotation quotation = new Quotation();
            if(StringUtil.isNullOrEmpty(soid)) {
            	quotation.setDeleted(false);
                quotation.setID(StringUtil.generateUUID());
            } else {
            	quotation = (Quotation) get(Quotation.class, soid);
            }

            if (dataMap.containsKey("entrynumber")) {
            	quotation.setquotationNumber((String) dataMap.get("entrynumber"));
            }
            if (dataMap.containsKey("autogenerated")) {
            	quotation.setAutoGenerated((Boolean) dataMap.get("autogenerated"));
            }
            if (dataMap.containsKey("memo")) {
            	quotation.setMemo((String) dataMap.get("memo"));
            }
            if (dataMap.containsKey("customerid")) {
                quotation.setCustomer(dataMap.get("customerid").toString());
            }
            if (dataMap.containsKey("moduleid")) {
                quotation.setModuleid(dataMap.get("moduleid").toString());
            }
            if (dataMap.containsKey("orderdate")) {
            	quotation.setQuotationDate(Long.valueOf(dataMap.get("orderdate").toString()));
            }
            if (dataMap.containsKey("duedate")) {
            	quotation.setDueDate(Long.valueOf(dataMap.get("duedate").toString()));
            }
            if (dataMap.containsKey("total")) {
            	quotation.setTotalamount(Double.valueOf(dataMap.get("total").toString()));
            }
            if (dataMap.containsKey("status")) {
            	quotation.setStatus(Integer.valueOf(dataMap.get("status").toString()));
            }
            if (dataMap.containsKey("discount") && !StringUtil.isNullOrEmpty(dataMap.get("discount").toString())) {
            	quotation.setDiscount(Float.valueOf(dataMap.get("discount").toString()));
            }
            if (dataMap.containsKey("isTaxable") && !StringUtil.isNullOrEmpty(dataMap.get("isTaxable").toString())) {
            	quotation.setTaxable(Boolean.TRUE.equals(Boolean.parseBoolean(dataMap.get("isTaxable").toString())));
            }
            if (dataMap.containsKey("perdiscount") && !StringUtil.isNullOrEmpty(dataMap.get("perdiscount").toString())) {
            	quotation.setPerDiscount(Boolean.TRUE.equals(Boolean.parseBoolean(dataMap.get("perdiscount").toString())));
            }
            if (dataMap.containsKey("discountAmount") && !StringUtil.isNullOrEmpty(dataMap.get("discountAmount").toString())) {
            	quotation.setDiscountAmount(Double.valueOf(dataMap.get("discountAmount").toString()));
            }
            if (dataMap.containsKey("taxAmount") && !StringUtil.isNullOrEmpty(dataMap.get("taxAmount").toString()) ) {
            	quotation.setTaxAmount(Double.valueOf(dataMap.get("taxAmount").toString()));
            }
            if (dataMap.containsKey("isProductTax")  && !StringUtil.isNullOrEmpty(dataMap.get("isProductTax").toString())) {
                quotation.setProductTax(Boolean.TRUE.equals(Boolean.parseBoolean(dataMap.get("isProductTax").toString())));
            }
            if (dataMap.containsKey("templateid")) {
                Projreport_Template template = dataMap.get("templateid")==null?null:(Projreport_Template) get(Projreport_Template.class, (String) dataMap.get("templateid"));
                quotation.setTemplateid(template);
            }
            if (dataMap.containsKey("taxid") && !StringUtil.isNullOrEmpty(dataMap.get("taxid").toString())) {
                Tax tax = (dataMap.get("taxid")==null?null:(Tax)get(Tax.class, (String) dataMap.get("taxid")));
                quotation.setTax(tax);
            }
            if (dataMap.containsKey("companyid")) {
                Company company = dataMap.get("companyid")==null?null:(Company) get(Company.class, (String) dataMap.get("companyid"));
                quotation.setCompany(company);
            }
            if (dataMap.containsKey("sodetails")) {
                if(dataMap.get("sodetails") != null) {
                	quotation.setRows((Set<QuotationDetail>) dataMap.get("sodetails"));
                }
            }
            save(quotation);
            list.add(quotation);
        } catch(Exception ex){
            ex.printStackTrace();
            throw ServiceException.FAILURE("saveQuotation : "+ex.getMessage(), ex);
        }
        return new KwlReturnObject(true, null, null, list, list.size());
    }

    public KwlReturnObject editQuotation(HashMap<String, Object> dataMap) throws ServiceException {
        List list = new ArrayList();
        try {
            String soid = (String) dataMap.get("id");

            Quotation quotation = new Quotation();
            quotation = (Quotation) get(Quotation.class, soid);

            if (dataMap.containsKey("entrynumber")) {
            	quotation.setquotationNumber((String) dataMap.get("entrynumber"));
            }
            if (dataMap.containsKey("memo")) {
            	quotation.setMemo((String) dataMap.get("memo"));
            }
            if (dataMap.containsKey("customerid")) {
                quotation.setCustomer(dataMap.get("customerid").toString());
            }
            if (dataMap.containsKey("moduleid")) {
                quotation.setModuleid(dataMap.get("moduleid").toString());
            }
            if (dataMap.containsKey("orderdate")) {
            	quotation.setQuotationDate(Long.valueOf(dataMap.get("orderdate").toString()));
            }
            if (dataMap.containsKey("duedate")) {
            	quotation.setDueDate(Long.valueOf(dataMap.get("duedate").toString()));
            }
            if (dataMap.containsKey("total")) {
            	quotation.setTotalamount(Double.valueOf(dataMap.get("total").toString()));
            }
            if (dataMap.containsKey("status")) {
            	quotation.setStatus(Integer.valueOf(dataMap.get("status").toString()));
            }
            if (dataMap.containsKey("discount") && !StringUtil.isNullOrEmpty(dataMap.get("discount").toString())) {
            	quotation.setDiscount(Float.valueOf(dataMap.get("discount").toString()));
            }
            if (dataMap.containsKey("isTaxable") && !StringUtil.isNullOrEmpty(dataMap.get("isTaxable").toString())) {
                quotation.setTaxable(Boolean.TRUE.equals(Boolean.parseBoolean(dataMap.get("isTaxable").toString())));
            }
            if (dataMap.containsKey("perdiscount") && !StringUtil.isNullOrEmpty(dataMap.get("perdiscount").toString())) {
            	quotation.setPerDiscount(Boolean.TRUE.equals(Boolean.parseBoolean(dataMap.get("perdiscount").toString())));
            }
            if (dataMap.containsKey("discountAmount") && !StringUtil.isNullOrEmpty(dataMap.get("discountAmount").toString())) {
            	quotation.setDiscountAmount(Double.valueOf(dataMap.get("discountAmount").toString()));
            }
            if (dataMap.containsKey("taxAmount") && !StringUtil.isNullOrEmpty(dataMap.get("taxAmount").toString()) ) {
            	quotation.setTaxAmount(Double.valueOf(dataMap.get("taxAmount").toString()));
            }
            if (dataMap.containsKey("isProductTax")  && !StringUtil.isNullOrEmpty(dataMap.get("isProductTax").toString())) {
            	quotation.setProductTax(Boolean.TRUE.equals(Boolean.parseBoolean(dataMap.get("isProductTax").toString())));
            }
            if (dataMap.containsKey("templateid")) {
                Projreport_Template template = dataMap.get("templateid")==null?null:(Projreport_Template) get(Projreport_Template.class, (String) dataMap.get("templateid"));
                quotation.setTemplateid(template);
            }
            if (dataMap.containsKey("taxid") && !StringUtil.isNullOrEmpty(dataMap.get("taxid").toString())) {
                Tax tax = (dataMap.get("taxid")==null?null:(Tax)get(Tax.class, (String) dataMap.get("taxid")));
                quotation.setTax(tax);
            }
            if (dataMap.containsKey("companyid")) {
                Company company = dataMap.get("companyid")==null?null:(Company) get(Company.class, (String) dataMap.get("companyid"));
                quotation.setCompany(company);
            }
            if (dataMap.containsKey("sodetails")) {
                if(dataMap.get("sodetails") != null) {
                	quotation.setRows((Set<QuotationDetail>) dataMap.get("sodetails"));
                }
            }
            saveOrUpdate(quotation);
            list.add(quotation);
        } catch(Exception ex){
            ex.printStackTrace();
            throw ServiceException.FAILURE("saveQuotation : "+ex.getMessage(), ex);
        }
        return new KwlReturnObject(true, null, null, list, list.size());
    }
    
    
    @Override
    public KwlReturnObject getQuotationItemsId(String quotation,String productId) throws ServiceException {
        List list = new ArrayList();
        int dl = 0;
        try{
        	String hql = "select ID from QuotationDetail   where quotation.ID=? and product.productid=?";

            list = executeQuery(hql,new Object[]{quotation,productId});
            dl = list.size();
        } catch(Exception ex) {
            logger.warn(ex.getMessage(),ex);
        }
        return new KwlReturnObject(true, null, null, list, dl);
    }
    public KwlReturnObject saveQuotationDetails(HashMap<String, Object> dataMap) throws ServiceException {
        List list = new ArrayList();
        try {
            String sodid = (String) dataMap.get("id");

            QuotationDetail quotationDetail = new QuotationDetail();
            if(!StringUtil.isNullOrEmpty(sodid)) {
            	quotationDetail = (QuotationDetail) get(QuotationDetail.class, sodid);
            } else {
                quotationDetail.setID(StringUtil.generateUUID());
            }

            if (dataMap.containsKey("soid")) {
            	Quotation quotation = dataMap.get("soid")==null?null:(Quotation) get(Quotation.class, (String) dataMap.get("soid"));
            	quotationDetail.setQuotation(quotation);
            }
            if (dataMap.containsKey("srno")) {
            	quotationDetail.setSrno((Integer) dataMap.get("srno"));
            }
            if (dataMap.containsKey("rate")) {
            	quotationDetail.setRate((Double) dataMap.get("rate"));
            }
            if (dataMap.containsKey("quantity")) {
            	quotationDetail.setQuantity((Integer) dataMap.get("quantity"));
            }
            if (dataMap.containsKey("remark")) {
            	quotationDetail.setRemark((String) dataMap.get("remark"));
            }
            if (dataMap.containsKey("discount")) {
            	quotationDetail.setDiscount(Float.valueOf(dataMap.get("discount").toString()));
            }
            if (dataMap.containsKey("taxamount")) {
            	quotationDetail.setTaxAmount(Double.valueOf(dataMap.get("taxamount").toString()));
            }
            if (dataMap.containsKey("productid")) {
                CrmProduct product = dataMap.get("productid")==null?null:(CrmProduct) get(CrmProduct.class, (String) dataMap.get("productid"));
                quotationDetail.setProduct(product);
            }
            if(dataMap.containsKey("tax")){
                Tax rowtax = (dataMap.get("tax")==null?null:(Tax)get(Tax.class, (String) dataMap.get("tax")));
                quotationDetail.setTax(rowtax);
            }
            saveOrUpdate(quotationDetail);
            list.add(quotationDetail);
        } catch(Exception ex){
            throw ServiceException.FAILURE("saveQuotationDetail : "+ex.getMessage(), ex);
        }
        return new KwlReturnObject(true, null, null, list, list.size());
    }
    

    @Override
    public KwlReturnObject getQuotationList(HashMap<String, Object> dataMap) throws ServiceException {
        List list = new ArrayList();
        int dl = 0;
        try{
             ArrayList filter_names = new ArrayList();
             ArrayList filter_values = new ArrayList();

//            String companyid = (String) dataMap.get("companyid");
            if(dataMap.containsKey("filter_names")){
                filter_names = new ArrayList((List<String>) dataMap.get("filter_names"));
            }
            if(dataMap.containsKey("filter_values")){
                filter_values = new ArrayList((List<Object>) dataMap.get("filter_values"));
            }

            String Hql = "select c from Quotation c ";

            String filterQuery = StringUtil.filterQuery(filter_names, "where");
            int ind = filterQuery.indexOf("(");
            if(ind>-1){
                int index = Integer.valueOf(filterQuery.substring(ind+1,ind+2));
                filterQuery = filterQuery.replaceAll("("+index+")", filter_values.get(index).toString());
                filter_values.remove(index);
            }

            if(dataMap.containsKey("ss") && dataMap.get("ss") != null) {
                String ss=dataMap.get("ss").toString();
                if(!StringUtil.isNullOrEmpty(ss)){
                    String[] searchcol = new String[]{"c.quotationNumber"};
                    if(dataMap.containsKey("email") && dataMap.get("email") != null) {
                        searchcol = new String[]{"c.lastname","c.firstname"};
                    }

                    StringUtil.insertParamSearchString(filter_values, ss, 1);
                    String searchQuery = StringUtil.getSearchString(ss, "and", searchcol);
                    filterQuery +=searchQuery;
                }
            }

            Hql += filterQuery;

            list = executeQuery(Hql,filter_values.toArray());
            dl = list.size();
            int start=Integer.parseInt(dataMap.get("start").toString());
            int limit=Integer.parseInt(dataMap.get("limit").toString());
            list = executeQueryPaging(Hql, filter_values.toArray(), new Integer[]{start, limit});
        } catch(Exception ex) {
            logger.warn(ex.getMessage(),ex);
        }
        return new KwlReturnObject(true, null, null, list, dl);
    }

    @Override
    public KwlReturnObject getQuotationItems(HashMap<String, Object> dataMap) throws ServiceException {
        List list = new ArrayList();
        int dl = 0;
        try{
             ArrayList filter_names = new ArrayList();
             ArrayList filter_values = new ArrayList();

//            String companyid = (String) dataMap.get("companyid");
            if(dataMap.containsKey("filter_names")){
                filter_names = new ArrayList((List<String>) dataMap.get("filter_names"));
            }
            if(dataMap.containsKey("filter_values")){
                filter_values = new ArrayList((List<Object>) dataMap.get("filter_values"));
            }

            String Hql = "select c from QuotationDetail c ";

            String filterQuery = StringUtil.filterQuery(filter_names, "where");
            int ind = filterQuery.indexOf("(");
            if(ind>-1){
                int index = Integer.valueOf(filterQuery.substring(ind+1,ind+2));
                filterQuery = filterQuery.replaceAll("("+index+")", filter_values.get(index).toString());
                filter_values.remove(index);
            }
            Hql += filterQuery;

            list = executeQuery(Hql,filter_values.toArray());
            dl = list.size();
        } catch(Exception ex) {
            logger.warn(ex.getMessage(),ex);
        }
        return new KwlReturnObject(true, null, null, list, dl);
    }

    @Override
    public KwlReturnObject deleteQuotations(String[] arrayid) throws ServiceException {
        List ll = new ArrayList();
        List<Object> params = new ArrayList<Object>();
        int dl = 0;
        try {
            String hql = "update Quotation set deleted = ? where ID in (:quotationids)";
            params.add(true);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("quotationids", arrayid);
            executeUpdate(hql, params.toArray(), map);
        }catch(DataAccessException e) {
            logger.warn(e.getMessage(), e);
            throw ServiceException.FAILURE("QuotationDAOImpl.deleteQuotations : "+e.getMessage(), e);
        }catch(Exception e) {
            logger.warn(e.getMessage(), e);
            throw ServiceException.FAILURE("QuotationDAOImpl.deleteQuotations : "+e.getMessage(), e);
        }
        return new KwlReturnObject(true, KWLErrorMsgs.S01, "", ll, dl);
    }

    @Override
    public List<Quotation> getQuotations(List<String> recordIds) throws ServiceException {
        if (recordIds == null || recordIds.isEmpty())
        {
            return null;
        }
        StringBuilder hql = new StringBuilder("from Quotation where id in (");

        for (String record: recordIds)
        {
            hql.append("'" + record + "',");
        }

        hql.deleteCharAt(hql.length() - 1);
        hql.append(")");

        return executeQuery(hql.toString());
    }

    public static String generateNextAutoNumber(String pattern, String strCurrent){
        StringBuffer strNext=new StringBuffer(pattern);
        int x=0;
        if(strCurrent!=null&&pattern.length()==strCurrent.length()){
            for(x=0;x<pattern.length();x++){
                if(pattern.charAt(x)=='0'&&(strCurrent.charAt(x)<'0'||strCurrent.charAt(x)>'9'))
                    break;
            }
        }
        if(x==pattern.length())
            strNext=new StringBuffer(strCurrent);
        int carry=1;
        for(int i=strNext.length()-1;i>=0;i--){
            if(pattern.charAt(i)!='0') continue;
            int sum=(strNext.charAt(i)-'0')+carry;
            strNext.setCharAt(i, (char)(sum%10+'0'));
            carry=sum/10;
        }
        return strNext.toString();
    }

    @Override
    public String getNextAutoNumber(String companyid, int from) throws ServiceException {
        String autoNumber="";
        String table="", field="", pattern="";
        try {
            switch(from){
                   case Constants.AUTONUM_QUOTATION:
                   table=Constants.QUOTATION_TABLE;
                   field=Constants.QUOTATION_FIELD;
                   pattern=Constants.PATTERN_QUOTATION;
                   break;
            }
            if(pattern==null) return autoNumber;
            String query="select max("+field+") from "+table+" where autoGenerated=true and "+field+" like ? and company.companyID=?";
            List list=executeQuery(query, new Object[]{pattern.replace('0', '_'), companyid});
            if(list.isEmpty()==false)autoNumber=(String)list.get(0);

            while(!pattern.equals(autoNumber)){
                autoNumber = generateNextAutoNumber(pattern, autoNumber);
                query="select "+field+" from "+table+" where "+field+" = ? and company.companyID=?";
                list=executeQuery(query, new Object[]{autoNumber, companyid});
                if(list.isEmpty()) return autoNumber;
            }
        }catch(Exception e) {
            logger.warn(e.getMessage(), e);
            throw ServiceException.FAILURE("QuotationDAOImpl.getNextAutoNumber : "+e.getMessage(), e);
        }
        return "Auto number for the pattern '" + pattern + "' doesn't exist.<br>Please change the pattern or disable Auto generation.";
    }

    @Override
    public KwlReturnObject getQuotationCount(String entrynumber, String companyid) throws ServiceException {
        List list = new ArrayList();
        int dl = 0;
        try{
             ArrayList filter_names = new ArrayList();
             ArrayList filter_values = new ArrayList();

            String Hql = "select c from Quotation c where c.quotationNumber = ? and c.company.companyID = ?";
            filter_values.add(entrynumber);
            filter_values.add(companyid);
            list = executeQuery(Hql,filter_values.toArray());
            dl = list.size();
        } catch(Exception ex) {
            logger.warn(ex.getMessage(),ex);
        }
        return new KwlReturnObject(true, null, null, list, dl);
    }
    public KwlReturnObject deleteQuotationItems(String quotation,String productId) throws ServiceException {
        List ll = new ArrayList();
        int dl = 0;
        try {
            String hql = "delete  QuotationDetail  where quotation.ID=? and product.productid=? ";
            executeUpdate(hql, new Object[]{quotation,productId});
        }catch(DataAccessException e) {
            logger.warn(e.getMessage(), e);
            throw ServiceException.FAILURE("QuotationDAOImpl.deleteQuotationItems : "+e.getMessage(), e);
        }catch(Exception e) {
            logger.warn(e.getMessage(), e);
            throw ServiceException.FAILURE("QuotationDAOImpl.deleteQuotationItems : "+e.getMessage(), e);
        }
        return new KwlReturnObject(true, KWLErrorMsgs.S01, "", ll, dl);
    }
    
    public KwlReturnObject getQuotation(HashMap<String, Object> requestParams) throws ServiceException {
        List ll = null;
        int dl = 0;
        try {
             ArrayList filter_names = new ArrayList();
             ArrayList filter_params = new ArrayList();

            if(requestParams.containsKey("filter_names")){
                filter_names = (ArrayList) requestParams.get("filter_names");
            }
            if(requestParams.containsKey("filter_params")){
                filter_params = (ArrayList) requestParams.get("filter_params");
            }

            String Hql = "select distinct c from QuotationDetail c ";
            if(filter_names.contains("INp.productid")) {
                Hql += " inner join c.product as p ";
            }

            String filterQuery = StringUtil.filterQuery(filter_names, "where");
            int ind = filterQuery.indexOf("(");
            if(ind>-1){
                int index = Integer.valueOf(filterQuery.substring(ind+1,ind+2));
                filterQuery = filterQuery.replaceAll("("+index+")", filter_params.get(index).toString());
                filter_params.remove(index);
            }
            Hql += filterQuery;

            ll = executeQuery(Hql,filter_params.toArray());
            dl = ll.size();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            throw ServiceException.FAILURE("QuotationDAOImpl.getQuotation : " + e.getMessage(), e);
        }
        return new KwlReturnObject(true, "002", "", ll, dl);
    }

}
