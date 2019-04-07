package com.chyl.mytest.export;

import com.chyl.mytest.export.annotation.ExcelAttribute;
import com.chyl.mytest.export.annotation.ExcelElement;
import com.chyl.mytest.export.config.ElementTypePath;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author only-lilei
 * @create 2018-01-23 上午9:50
 **/
public class ExportMergeExcel extends AbstractExprotMergeExcel {
    private static Logger log = LoggerFactory.getLogger(ExportMergeExcel.class);

    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    private boolean flag = true;

    private Row row;

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param cls   实体对象，通过annotation.ExportField获取标题
     */
    public ExportMergeExcel(String title, Class<?> cls) {
        // 得到所有的字段
        List<Field> fields = getAllField(cls, null);
        List<String> headerList = new ArrayList<>();
        List<ExcelAttribute> excelAttributes = new ArrayList<>();
        for (Field field : fields) {
            ExcelAttribute attr = field.getAnnotation(ExcelAttribute.class);
            excelAttributes.add(attr);
        }
        Collections.sort(excelAttributes, (a, b) -> (a.column() - b.column()));
        excelAttributes.forEach(e -> {
            headerList.add(e.title());
        });
        initialize(title, headerList);
    }

    public <E> ExportMergeExcel setDataList(List<E> list) {
        int rowStart = 2;
        for (E e : list) {
            setData(e);
            rowStart = mergedRegio(e, rowStart);
        }
        return this;
    }

    public void setData(Object t) {
        if(flag){
            row = this.addRow();
        }
        Field[] fields = t.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    // 设置私有属性为可访问
                    field.setAccessible(true);
                }
                if (field.isAnnotationPresent(ExcelAttribute.class) && !field.isAnnotationPresent(ExcelElement.class)) {
                    ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
                    //log.debug("当前列:" + ea.column() + "====>value:" + field.get(t));
                    flag = true;
                    addCell(row, ea.column(), field.get(t));
                } else if (field.isAnnotationPresent(ExcelElement.class)) {
                    flag = false;
                    //log.debug("泛型：=====>" + getClass(field.getGenericType(), 0));
                    switch (ElementTypePath.getElementTypePath(field.getType().getName())) {
                        case SET:
                            Set<?> set = (Set<?>) field.get(t);
                            if (set != null) {
                                for (Object object : set) {
                                    setData(object);
                                }
                            }
                            break;
                        case LIST:
                            List<?> list = (List<?>) field.get(t);
                            if (list != null) {
                                for (Object object : list) {
                                    setData(object);
                                }
                            }
                            break;
                        case MAP:
                            break;
                        default:
                            setData(field.get(t));
                            break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("write error");
        }
        flag = true;
    }

    /**
     * 合并数据
     */
    public int mergedRegio(Object t, int rowStart) {
        //获取子节点的数目
        Field[] fields = t.getClass().getDeclaredFields();
        int rowEnd = rowStart + childNodes(t, 0) - 1;
//        log.debug(rowStart + "====>" + rowEnd);
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelAttribute.class)) {
                ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
                CellRangeAddress cellRangeAddress = new CellRangeAddress(rowStart, rowEnd, ea.column(),ea.column());
                sheet.addMergedRegion(cellRangeAddress);
//                setMergeStyle(cellRangeAddress);
            } else if (field.isAnnotationPresent(ExcelElement.class) && !field.isAnnotationPresent(ExcelAttribute.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                int childRowStart = rowStart;
                try {
                    switch (ElementTypePath.getElementTypePath(field.getType().getName())) {
                        case SET:
                            Set<?> set = (Set<?>) field.get(t);
                            if (set != null) {
                                for (Object object : set) {
                                    childRowStart = mergedRegio(object, childRowStart);
                                }
                            }
                            break;
                        case LIST:
                            List<?> list = (List<?>) field.get(t);
                            if (list != null) {
                                for (Object object : list) {
                                    childRowStart = mergedRegio(object, childRowStart);
                                }
                            }
                            break;
                        case MAP:
                            break;
                        default:
                            childRowStart = mergedRegio(field.get(t), childRowStart);
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return rowEnd + 1;

    }

    /**
     * 统计子节点的叶子数目
     *
     * @param t
     * @param childNodeNum
     * @return int 子节点的数目
     * @throws
     * @Description: TODO(一个类只支持包含一个集合)
     */
    private int childNodes(Object t, int childNodeNum) {
        Field[] fields = t.getClass().getDeclaredFields();
        boolean childNodeFlag = true;
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelElement.class) && !field.isAnnotationPresent(ExcelAttribute.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                childNodeFlag = false;
                try {
                    switch (ElementTypePath.getElementTypePath(field.getType().getName())) {
                        case SET:
                            Set<?> set = (Set<?>) field.get(t);
                            if (set != null) {
                                if (set.size() == 0) {
                                    childNodeFlag = true;
                                } else {
                                    for (Object object : set) {
                                        childNodeNum = childNodes(object, childNodeNum);
                                    }
                                }

                            } else {
                                childNodeFlag = true;
                            }
                            break;
                        case LIST:
                            List<?> list = (List<?>) field.get(t);
                            if (list != null) {
                                if (list.size() == 0) {
                                    childNodeFlag = true;
                                } else {
                                    for (Object object : list) {
                                        childNodeNum = childNodes(object, childNodeNum);
                                    }
                                }
                            } else {
                                childNodeFlag = true;
                            }
                            break;
                        case MAP:
                            break;
                        default:
                            childNodeNum = childNodes(field.get(t), childNodeNum);
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        if (childNodeFlag) {
            childNodeNum++;
        }
        return childNodeNum;
    }

    /**
     * 初始化函数
     *
     * @param title      表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    private void initialize(String title, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet("Export");
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size() - 1));
        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 5000 ? 5000 : colWidth);
        }
        log.debug("Initialize success.");
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        styles.put("data3", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    public ExportMergeExcel write(OutputStream os) throws IOException {
        wb.write(os);
        return this;
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val) {
        Cell cell = row.createCell(column);
        String cellFormatString = "@";
        try {
            if (val == null) {
                cell.setCellValue("");
            } else {
                if (val instanceof String) {
                    cell.setCellValue((String) val);
                } else if (val instanceof Integer) {
                    cell.setCellValue((Integer) val);
                    cellFormatString = "0";
                } else if (val instanceof Long) {
                    cell.setCellValue((Long) val);
                    cellFormatString = "0";
                } else if (val instanceof Double) {
                    cell.setCellValue((Double) val);
                    cellFormatString = "0.00";
                } else if (val instanceof Float) {
                    cell.setCellValue((Float) val);
                    cellFormatString = "0.00";
                } else if (val instanceof Date) {
                    cell.setCellValue((Date) val);
                    cellFormatString = "yyyy-MM-dd HH:mm:ss";
                } else if (val instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) val).setScale(2).toString());//保留两位小数
                    cellFormatString = "0.00";
                } else {
                    cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                            "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod("setValue", Object.class).invoke(null, val));
                }
            }
            if (val != null) {
                CellStyle style = styles.get("data_column_" + column);
                if (style == null) {
                    style = wb.createCellStyle();
                    style.cloneStyleFrom(styles.get("data2"));
                    style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
                    styles.put("data_column_" + column, style);
                }
                cell.setCellStyle(style);
            } else {
                CellStyle style = wb.createCellStyle();
                style.cloneStyleFrom(styles.get("data2"));
                cell.setCellStyle(style);
            }
        } catch (Exception ex) {
            log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        return cell;
    }

    /**
     * 设置合并后单元格的样式
     * @param cellRangeAddress
     */
    public void setMergeStyle(CellRangeAddress cellRangeAddress){
        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet,wb); // 下边框
        RegionUtil.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(),cellRangeAddress,sheet,wb);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet,wb); // 左边框
        RegionUtil.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(),cellRangeAddress,sheet,wb);
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet,wb); // 有边框
        RegionUtil.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(),cellRangeAddress,sheet,wb);
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet,wb); // 上边框
        RegionUtil.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex(),cellRangeAddress,sheet,wb);
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public ExportMergeExcel write(HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public ExportMergeExcel write(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        //判断浏览器类型
        String agent = request.getHeader("User-Agent");         //获取浏览器头文件
        boolean isMSIE = ((agent != null && agent.indexOf("MSIE") != -1) || (null != agent && -1 != agent.indexOf("like Gecko")));    //判断版本,后边是判断IE11的
        // 解决下载时候中文名字乱码
        if (isMSIE) {
            //针对ie
            fileName = Encodes.urlEncode(fileName);
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
        }
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     *
     * @param fileName 输出文件名
     */
    public ExportMergeExcel writeFile(String fileName) throws IOException {
        FileOutputStream os = new FileOutputStream(fileName);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    public ExportMergeExcel dispose() {
        wb.dispose();
        return this;
    }

}
