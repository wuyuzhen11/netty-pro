package com.wyz.导出钉钉;/**
 * @Author: 陈忠琳
 * @Description: TODO
 * @DateTime: 2021/3/29 16:57
 **/

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
    private static final String EXCEL_XLS = ".xls";
    private static final String EXCEL_XLSX = ".xlsx";
    private static final int SHEET_SIZE_XLS = 65535; //65536;
    private static final int SHEET_SIZE_XLSX = 100001;

    /**
     * Excel导出数量超过最大值错误代码
     */
    public static final String DOWNLOAD_ERROR_CODE = "1";


    /**
     * Excel导出操作
     *
     * @param is03Excel      是否导出03版本的Excel，否则导出07版本
     * @param response
     * @param exportFileName 导出文件名
     * @param VOList         需导出的数据
     * @param headers        表头字段数据
     * @param widths         宽度
     * @param columns        VO属性字段
     * @author zuosl  2015-9-15
     */
    @SuppressWarnings("unchecked")
    public static void exportExcel(boolean is03Excel,
                                   String exportFileName, List<?> volist,
                                   String[] headers, int[] widths, String[] columns) {
        if (null == volist || 0 >= volist.size()) {
            return;
        }

        if (is03Excel) {
            exportFileName = exportFileName + EXCEL_XLS;
//			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment; filename=" + exportFileName);
            //response.setContentType("application/vnd.ms-excel;charset=utf-8");
           /* try {
                //response.setHeader("Content-disposition", "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }*/

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFPalette palette = workbook.getCustomPalette();
//	        palette.setColorAtIndex((short) 50, (byte)(0xff), (byte)(0xc0), (byte)(0x00));
            palette.setColorAtIndex((short) 50, (byte) (0xc0), (byte) (0xc0), (byte) (0xc0));

            //表头样式
            HSSFCellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor((short) 50);
            headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //表头字体
            HSSFFont headFont = workbook.createFont();
            headFont.setFontHeightInPoints((short) 11);
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headStyle.setFont(headFont);

            //内容样式
            HSSFCellStyle columnStyle = workbook.createCellStyle();
//	        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//	        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
            columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            //内容字体
            HSSFFont columnFont = workbook.createFont();
            columnFont.setFontHeightInPoints((short) 11);
            columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            columnStyle.setFont(columnFont);

            //导出修改，数据量超过SHEET_SIZE_XLS行的建立新的sheet
            int dataSize = volist.size();
            int sheetCount = dataSize % (SHEET_SIZE_XLS - 1) == 0 ? dataSize / (SHEET_SIZE_XLS - 1) : dataSize / (SHEET_SIZE_XLS - 1) + 1;
            for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {
                HSSFSheet sheet = workbook.createSheet();

                //表头
                HSSFRow row = sheet.createRow(0);
                row.setHeight((short) 360);
                for (int i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(headStyle);
                    HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
                    sheet.setColumnWidth(i, widths[i] * 45);
                }

                //内容
                int rowNum = 0;
                for (int i = sheetNum * (SHEET_SIZE_XLS - 1); i < (sheetNum + 1) * (SHEET_SIZE_XLS - 1) && i < dataSize; i++) {
                    Object vo = volist.get(i);
                    row = sheet.createRow(++rowNum);
                    row.setHeight((short) 360);

                    for (int k = 0; k < columns.length; k++) {
                        HSSFCell cell = row.createCell(k);
                        cell.setCellStyle(columnStyle);

                        Class voClass = vo.getClass();


//	        			String textValue = null;
                        try {
                            Method getMethod = voClass.getMethod("get" + columns[k].substring(0, 1).toUpperCase() + columns[k].substring(1));
//
                            //可以根据需要增加导出的数据格式，只是要确保VO的类型与导出的类型一致
                            Object cellValue = getMethod.invoke(vo);
                            if (cellValue instanceof Integer) {
                                cell.setCellValue(((Integer) cellValue).intValue());
                            } else if (cellValue instanceof Long) {
                                cell.setCellValue(((Long) cellValue).longValue());
                            } else if (cellValue instanceof Date) {
                                CellStyle cellStyle = workbook.createCellStyle();
                                CreationHelper createHelper = workbook.getCreationHelper();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime((Date) cellValue);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);              //时（24小时制）
                                int minute = calendar.get(Calendar.MINUTE);                    //分
                                int second = calendar.get(Calendar.SECOND);                  //秒
                                if (hour == 0 && minute == 0 && second == 0) {
                                    short dateFormat = createHelper.createDataFormat().getFormat("yyyy/MM/dd");
                                    cellStyle.setDataFormat(dateFormat);
                                } else {
                                    short dateFormat = createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss");
                                    cellStyle.setDataFormat(dateFormat);
                                }
                                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                                columnFont.setFontHeightInPoints((short) 11);
                                columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
                                cellStyle.setFont(columnFont);
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(calendar.getTime());

                            } else if (cellValue == null) {
                                cell.setCellValue("");
                            } else if (cellValue instanceof String) {
                                cell.setCellValue(String.valueOf(cellValue));
                            } else if (cellValue instanceof Double) {
                                cell.setCellValue(((Double) cellValue).doubleValue());
                            }
//							textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//	        			HSSFRichTextString text = new HSSFRichTextString(null == textValue ? "" : textValue);
//	        			cell.setCellValue(text);
                    }
                }
            }

            try {
                OutputStream out = new FileOutputStream("c://text.xlsx");
                workbook.write(out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //导出07 后版本
            exportFileName = exportFileName + EXCEL_XLSX;

            //response.setContentType("application/vnd.ms-excel;charset=utf-8");
            /*try {
                //response.setHeader("Content-disposition", "attachment; filename=" + new String(exportFileName.getBytes("GBK"), "ISO_8859_1"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
*/
            XSSFWorkbook workbook = new XSSFWorkbook();

            //表头样式
            XSSFCellStyle headStyle = workbook.createCellStyle();
            XSSFColor color = new XSSFColor(new byte[]{(byte) (0xc0), (byte) (0xc0), (byte) (0xc0)});
            headStyle.setFillForegroundColor(color);
            headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            headStyle.setBorderBottom(CellStyle.BORDER_THIN);
            headStyle.setBorderLeft(CellStyle.BORDER_THIN);
            headStyle.setBorderRight(CellStyle.BORDER_THIN);
            headStyle.setBorderTop(CellStyle.BORDER_THIN);
            headStyle.setAlignment(CellStyle.ALIGN_CENTER);

            //表头字体
            XSSFFont headFont = workbook.createFont();
            headFont.setFontHeightInPoints((short) 11);
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headStyle.setFont(headFont);

            //内容样式
            XSSFCellStyle columnStyle = workbook.createCellStyle();
//	        columnStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//	        columnStyle.setFillPattern(CellStyle.NO_FILL);//(HSSFCellStyle.SOLID_FOREGROUND);
            columnStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            columnStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            columnStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            columnStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            columnStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            //内容字体
            XSSFFont columnFont = workbook.createFont();
            columnFont.setFontHeightInPoints((short) 11);
            columnFont.setFontName("Arial");
            columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            columnStyle.setFont(columnFont);

            //导出修改，数据量超过SHEET_SIZE_XLSX行的建立新的sheet
            int dataSize = volist.size();
            int sheetCount = dataSize % (SHEET_SIZE_XLSX - 1) == 0 ? dataSize / (SHEET_SIZE_XLSX - 1) : dataSize / (SHEET_SIZE_XLSX - 1) + 1;
            for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {
                XSSFSheet sheet = workbook.createSheet();

                //表头
                XSSFRow row = sheet.createRow(0);
                row.setHeight((short) 360);
                for (int i = 0; i < headers.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellStyle(headStyle);
                    XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
                    sheet.setColumnWidth(i, widths[i] * 45);
                }

                //内容
                int rowNum = 0;
                for (int i = sheetNum * (SHEET_SIZE_XLSX - 1); i < (sheetNum + 1) * (SHEET_SIZE_XLSX - 1) && i < dataSize; i++) {
                    Object vo = volist.get(i);
                    row = sheet.createRow(++rowNum);
                    row.setHeight((short) 360);

                    for (int k = 0; k < columns.length; k++) {
                        XSSFCell cell = row.createCell(k);
                        cell.setCellStyle(columnStyle);

                        Class voClass = vo.getClass();
                        String textValue = null;
                        try {
                            Method getMethod = voClass.getMethod("get" + columns[k].substring(0, 1).toUpperCase() + columns[k].substring(1));
                            //可以根据需要增加导出的数据格式，只是要确保VO的类型与导出的类型一致
                            Object cellValue = getMethod.invoke(vo);
                            if (cellValue instanceof Integer) {
                                cell.setCellValue(((Integer) cellValue).intValue());
                            } else if (cellValue instanceof Long) {
                                cell.setCellValue(((Long) cellValue).longValue());
                            } else if (cellValue instanceof Date) {
                                CellStyle cellStyle = workbook.createCellStyle();
                                CreationHelper createHelper = workbook.getCreationHelper();

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime((Date) cellValue);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);              //时（24小时制）
                                int minute = calendar.get(Calendar.MINUTE);                    //分
                                int second = calendar.get(Calendar.SECOND);                  //秒
                                if (hour == 0 && minute == 0 && second == 0) {
                                    short dateFormat = createHelper.createDataFormat().getFormat("yyyy/MM/dd");
                                    cellStyle.setDataFormat(dateFormat);
                                } else {
                                    short dateFormat = createHelper.createDataFormat().getFormat("yyyy/MM/dd HH:mm:ss");
                                    cellStyle.setDataFormat(dateFormat);
                                }
                                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                                columnFont.setFontHeightInPoints((short) 11);
                                columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
                                cellStyle.setFont(columnFont);
                                cell.setCellValue((Date) cellValue);
                                cell.setCellStyle(cellStyle);
                            } else if (cellValue == null) {
                                cell.setCellValue("");
                            } else if (cellValue instanceof String) {
                                cell.setCellValue(String.valueOf(cellValue));
                            } else if (cellValue instanceof Double) {
                                cell.setCellValue(((Double) cellValue).doubleValue());
                            }
                            //textValue = null == getMethod.invoke(vo) ? "" : String.valueOf(getMethod.invoke(vo));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //XSSFRichTextString text = new XSSFRichTextString(null == textValue ? "" : textValue);
                        //cell.setCellValue(text);
                    }
                }

            }

            try {
                OutputStream out = new FileOutputStream("c://text.xlsx");
                workbook.write(out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * Excel导入数据
     * 支持的VO属性全为String
     *
     * @param is          文件输入流
     * @param columns
     * @param startRow
     * @param startColumn
     * @return
     * @throws IOException
     * @author zuosl  2015-9-16
     */
    @SuppressWarnings("unchecked")
    public static List<?> importExcel(Object vo, InputStream is, String[] columns,
                                      int startRow, int startColumn) throws Exception {

        List<Object> voList = new ArrayList<Object>();

        // 循环工作表Sheet
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        if (hssfSheet == null) {
            return voList;
        }

        // 循环行Row
        for (int rowNum = startRow; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null) {
                continue;
            }

            // 循环列Cell
            Class voClass = vo.getClass();
            Object tempVO = voClass.newInstance();
            for (int cellNum = startColumn; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
                //超过字段数
                if (cellNum >= (columns.length + startColumn)) {
                    break;
                }

                HSSFCell hssfCell = hssfRow.getCell(cellNum);
                if (hssfCell == null) {
                    continue;
                }

                int k = cellNum - startColumn;
                Method setMethod = voClass.getMethod("set" + columns[k].substring(0, 1).toUpperCase() + columns[k].substring(1), String.class);
                setMethod.invoke(tempVO, getValue(hssfCell));
            }
            voList.add(tempVO);

        }
        return voList;

    }

    /**
     * 获取单元格值
     *
     * @param hssfCell
     * @return
     * @author zuosl  2015-9-16
     */
    public static String getValue(HSSFCell hssfCell) {
        switch (hssfCell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: {
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();
                }
//        	return String.valueOf(hssfCell.getNumericCellValue());
                return new DecimalFormat("#.######").format(hssfCell.getNumericCellValue());
            }

            case HSSFCell.CELL_TYPE_STRING:
                return String.valueOf(hssfCell.getStringCellValue());

            case HSSFCell.CELL_TYPE_FORMULA:
                return String.valueOf(hssfCell.getNumericCellValue());

            case HSSFCell.CELL_TYPE_BOOLEAN:
                return String.valueOf(hssfCell.getBooleanCellValue());

            default:
                return "";
        }
    }

    /**
     * 获取单元格值
     *
     * @param hssfCell
     * @return
     * @author zuosl  2015-9-16
     */
    public static String getValue(HSSFCell hssfCell, String dateFormat) {
        switch (hssfCell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: {
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                    SimpleDateFormat sdf = null;
                    if (null != dateFormat && !"".equals(dateFormat)) {
                        sdf = new SimpleDateFormat(dateFormat);
                    } else {
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();
                }
//        	return String.valueOf(hssfCell.getNumericCellValue());
                return new DecimalFormat("#.######").format(hssfCell.getNumericCellValue());
            }

            case HSSFCell.CELL_TYPE_STRING:
                return String.valueOf(hssfCell.getStringCellValue());

            case HSSFCell.CELL_TYPE_FORMULA:
                return String.valueOf(hssfCell.getNumericCellValue());

            case HSSFCell.CELL_TYPE_BOOLEAN:
                return String.valueOf(hssfCell.getBooleanCellValue());

            default:
                return "";
        }
    }


    /**
     * 读取Excel数据  07后版本
     * 支持的VO属性全为String
     *
     * @param vo
     * @param is
     * @param columns
     * @param startRow
     * @param startColumn
     * @return
     * @throws IOException
     * @author zuosl  2015-11-13
     */
    @SuppressWarnings("unchecked")
    public static List<?> importExcel2(Object vo, InputStream is, String[] columns,
                                       int startRow, int startColumn) throws IOException {
        List<Object> voList = new ArrayList<Object>();

        // 获取工作表Sheet
        Workbook workbook = new XSSFWorkbook(is);

        //获取sheet
        Sheet sheet = workbook.getSheetAt(0);
        if (null == sheet) {
            return voList;
        }

        // 循环行Row
        for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            //循环列Cell
            Class voClass = vo.getClass();
            try {
                Object tempVO = voClass.newInstance();
                for (int cellNum = startColumn; cellNum <= row.getLastCellNum(); cellNum++) {
                    //超过字段数
                    if (cellNum >= (columns.length + startColumn)) {
                        break;
                    }

                    Cell cell = row.getCell(cellNum);
                    if (null == cell) {
                        continue;
                    }

                    int k = cellNum - startColumn;
                    Method setMethod = voClass.getMethod("set" + columns[k].substring(0, 1).toUpperCase() + columns[k].substring(1), String.class);
                    setMethod.invoke(tempVO, getValue(cell, null));
                }
                voList.add(tempVO);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return voList;
    }


    /**
     * 读取Excel单元格值
     *
     * @param cell
     * @return
     * @author zuosl  2015-11-13
     */
    public static String getValue(Cell cell, String dateFormat) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (null != dateFormat && !"".equals(dateFormat)) {
                        sdf = new SimpleDateFormat(dateFormat);
                    } else {
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
//        	return String.valueOf(hssfCell.getNumericCellValue());
                return new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }

            case HSSFCell.CELL_TYPE_STRING:
                return String.valueOf(cell.getStringCellValue());

            case HSSFCell.CELL_TYPE_FORMULA:
                return String.valueOf(cell.getNumericCellValue());

            case HSSFCell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            default:
                return "";
        }
    }

    // 小标题的样式
    public static CellStyle title(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        // 横向居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 纵向居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 上细线
        style.setBorderTop(CellStyle.BORDER_THIN);
        // 下细线
        style.setBorderBottom(CellStyle.BORDER_THIN);
        // 左细线
        style.setBorderLeft(CellStyle.BORDER_THIN);
        // 右细线
        style.setBorderRight(CellStyle.BORDER_THIN);
        return style;
    }

    /**
     * 用来得到真实行数
     *
     * @param sheet
     * @return
     */
    public static int getExcelRealRow(Sheet sheet) {
        // 获取Excel表的真实行数
        boolean flag = false;
        for (int i = 1; i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {
                // 如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum())// 如果到了最后一行，直接将那一行remove掉
                {
                    sheet.removeRow(r);
                } else//如果还没到最后一行，则数据往上移一行
                {
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
        }
        return sheet.getLastRowNum();
    }

}
