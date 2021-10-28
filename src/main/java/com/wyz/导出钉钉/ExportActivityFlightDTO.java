package com.wyz.导出钉钉;

import java.io.Serializable;

/**
 * @program: 运单
 * @description: 航班计划导出
 * @author: wyz
 * @create: 2021-05-18 14:50
 **/

public class ExportActivityFlightDTO implements Serializable {

    private static final long serialVersionUID = 505077348678270794L;


    private String exportFileName;


    private String[] headers;


    int[] widths;


    String[] columns;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
}
