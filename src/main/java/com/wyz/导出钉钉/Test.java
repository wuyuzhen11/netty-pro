package com.wyz.导出钉钉;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: nettyPro
 * @description: 钉钉数据处理导出excel
 * @author: wyz
 * @create: 2021-09-12 16:28
 **/
public class Test {
    public static void main(String[] args) {
        String txt = "2020.11.05\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-11-03 19:00\n" +
                "结束时间:2020-11-03 22:00\n" +
                "2020.11.03\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-29 19:00\n" +
                "结束时间:2020-10-29 21:00\n" +
                "2020.10.29\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-28 19:00\n" +
                "结束时间:2020-10-28 22:00\n" +
                "2020.10.28\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-27 19:00\n" +
                "结束时间:2020-10-27 21:00\n" +
                "2020.10.27\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-26 19:00\n" +
                "结束时间:2020-10-26 22:00\n" +
                "2020.10.27\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:休息日加班\n" +
                "开始时间:2020-10-25 14:00\n" +
                "结束时间:2020-10-25 17:00\n" +
                "2020.10.26\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-22 19:00\n" +
                "结束时间:2020-10-22 21:00\n" +
                "2020.10.22\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-21 19:00\n" +
                "结束时间:2020-10-21 21:00\n" +
                "2020.10.21\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-20 19:00\n" +
                "结束时间:2020-10-20 22:00\n" +
                "2020.10.20\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-19 19:00\n" +
                "结束时间:2020-10-19 22:00\n" +
                "2020.10.19\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:休息日加班\n" +
                "开始时间:2020-10-18 09:00\n" +
                "结束时间:2020-10-18 18:00\n" +
                "2020.10.19\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-16 19:00\n" +
                "结束时间:2020-10-16 22:00\n" +
                "2020.10.16\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-14 19:00\n" +
                "结束时间:2020-10-14 22:00\n" +
                "2020.10.14\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:节假日加班\n" +
                "开始时间:2020-10-06 09:00\n" +
                "结束时间:2020-10-08 18:00\n" +
                "2020.10.14\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-10-09 19:00\n" +
                "结束时间:2020-10-09 22:00\n" +
                "2020.10.13\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:节假日加班\n" +
                "开始时间:2020-10-13 19:00\n" +
                "结束时间:2020-10-13 21:00\n" +
                "2020.10.13\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-28 19:00\n" +
                "结束时间:2020-09-28 21:00\n" +
                "2020.09.29\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-27 18:00\n" +
                "结束时间:2020-09-27 21:00\n" +
                "2020.09.27\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-24 19:00\n" +
                "结束时间:2020-09-24 22:00\n" +
                "2020.09.24\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-22 19:00\n" +
                "结束时间:2020-09-22 21:00\n" +
                "2020.09.24\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:休息日加班\n" +
                "开始时间:2020-09-20 09:00\n" +
                "结束时间:2020-09-20 20:00\n" +
                "2020.09.22\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-17 19:00\n" +
                "结束时间:2020-09-17 21:00\n" +
                "2020.09.19\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-14 19:00\n" +
                "结束时间:2020-09-14 21:00\n" +
                "2020.09.15\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:休息日加班\n" +
                "开始时间:2020-09-13 09:00\n" +
                "结束时间:2020-09-13 18:00\n" +
                "2020.09.15\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-09-08 19:00\n" +
                "结束时间:2020-09-08 21:00\n" +
                "2020.09.09\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-08-14 18:00\n" +
                "结束时间:2020-08-14 23:00\n" +
                "2020.08.19\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-08-13 18:00\n" +
                "结束时间:2020-08-13 20:01\n" +
                "2020.08.19\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-07-28 18:00\n" +
                "结束时间:2020-07-28 21:00\n" +
                "2020.07.28\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:工作日加班\n" +
                "开始时间:2020-07-13 18:00\n" +
                "结束时间:2020-07-13 22:00\n" +
                "2020.07.28\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:休息日加班\n" +
                "开始时间:2020-07-12 09:40\n" +
                "结束时间:2020-07-12 18:00\n" +
                "2020.07.12\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n" +
                "徐楷鑫提交的加班\n" +
                "加班类型:休息日加班\n" +
                "开始时间:2020-06-07 10:00\n" +
                "结束时间:2020-06-07 18:00\n" +
                "2020.06.07\n" +
                "\n" +
                "由徐楷鑫提交\n" +
                "审批通过\n";
        try {
            Test.exportExcel("徐楷鑫","广州研发部",txt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel(String username,String part,String txt) throws ParseException {
        List<Entity> list = new ArrayList<>();
        int index = 0;
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        while (true){
            Entity entity = new Entity();
            index = txt.indexOf("加班类型", index+1);
            if (index==-1){
                break;
            }
            String type = txt.substring(index + 5, index + 5 + 5);
            entity.setType(type);
            index = txt.indexOf("开始时间", index+1);
            String start = txt.substring(index + 5, index + 5 + 16);
            entity.setStart(sdf.parse(start+":00"));
            entity.setDate(start.split(" ")[0]);
            entity.setStartDate(start.split(" ")[1]);
            index = txt.indexOf("结束时间", index+1);
            String endDate = txt.substring(index + 16, index + 16 + 5);
            String end = txt.substring(index + 5, index + 5 + 16);
            entity.setEnd(sdf.parse(end+":00"));
            entity.setEndDate(endDate);
            entity.setUserName(username);
            entity.setPart(part);
            //计算间隔
            long time=entity.getEnd().getTime()-entity.getStart().getTime();
            BigDecimal a = new BigDecimal(time);
            BigDecimal b = new BigDecimal(1000*3600);
            BigDecimal divide = a.divide(b,2, BigDecimal.ROUND_HALF_UP);
            if (divide.longValue()>=10){
                divide=divide.subtract(new BigDecimal(3));
            }
            else if (divide.longValue()>=8){
                divide=divide.subtract(new BigDecimal(2));
            }
            entity.setJiange(divide.doubleValue());
            list.add(entity);
        }

        ExportActivityFlightDTO exportActivityFlightDTO = new ExportActivityFlightDTO();
        //exportFileName = new String(exportFileName.getBytes("ISO-8859-1"), "utf-8");
        String[] headers = {"姓名","部门","加班类型", "加班日期", "开始时间", "结束时间","加班工时"};
        int[] widths = {90,90,90, 120, 120, 90,90
                };
        String[] columns = {"userName","part","type", "date", "startDate", "endDate","jiange"

        };
        exportActivityFlightDTO.setExportFileName("test");
        exportActivityFlightDTO.setColumns(columns);
        exportActivityFlightDTO.setHeaders(headers);
        exportActivityFlightDTO.setWidths(widths);
        try {

            ExcelUtil.exportExcel(false, exportActivityFlightDTO.getExportFileName(),
                    list,
                    exportActivityFlightDTO.getHeaders(),
                    exportActivityFlightDTO.getWidths(),
                    exportActivityFlightDTO.getColumns());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
