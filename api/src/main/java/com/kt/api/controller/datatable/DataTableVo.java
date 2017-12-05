package com.kt.api.controller.datatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2015/11/2.
 */
public class DataTableVo {

    private String sEcho;
    private int iDisplayStart;
    private int iDisplayLength;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private List<Object> aaData;
    private String sortField;
    private String sortType;

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    // return zero-based page number
    public int getiCurrentPage() {
        return iDisplayStart / iDisplayLength;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
        setiTotalDisplayRecords(iTotalRecords);
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    @SuppressWarnings("unchecked")
    public void setData(Object aaData) {
        if (aaData instanceof List) {
            this.aaData = (List<Object>) aaData;
        } else {
            this.aaData = new ArrayList<Object>();
            this.aaData.add(aaData);
        }
    }

    public List<Object> getAaData() {
        return aaData;
    }

    public int getCurrentPageNumber() {
        if (getiDisplayStart() > 0) {
            return (getiDisplayStart() / getiDisplayLength()) + 1;
        } else {
            return 1;
        }
    }
}
