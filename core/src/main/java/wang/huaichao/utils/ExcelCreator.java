package wang.huaichao.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2/7/2017.
 */
public class ExcelCreator {
    public static final String DATE_FMT_STR = "yyyy-MM-dd hh:mm:ss";
    public static final String NUMER_FMT_STR = "#,##0.00";

    private Workbook workbook;
    private CreationHelper creationHelper;
    private Sheet sheet;
    private int rowIdx;
    private Map<Integer, CellStyle> cellStyleMap = new HashMap<>();
    private Map<Integer, Integer> columnWidthMap = new HashMap<>();
    private int counter = 0;

    public ExcelCreator() {
        workbook = new XSSFWorkbook();
        creationHelper = workbook.getCreationHelper();
    }

    public void createSheet(String name) {
        if (name == null) {
            counter++;
            name = "Sheet " + counter;
        }
        rowIdx = 0;
        sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(name));
    }

    public void setCellStyle(int colNum, CellStyle style) {
        cellStyleMap.put(colNum, style);
    }

    public void setCellStyle(int colNum, String fmt) {
        setCellStyle(colNum, createCellStyle(fmt));
    }

    public void setColumnWidth(int colNum, int width) {
        columnWidthMap.put(colNum, width);
        if (sheet != null) {
            sheet.setColumnWidth(colNum, width * 256);
        }
    }

    public void createRow(Object... rowData) {
        int col = 0;
        Row row = sheet.createRow(rowIdx++);
        for (Object d : rowData) {
            Cell cell = row.createCell(col);
            _setCellValue(cell, d);
            if (cellStyleMap.containsKey(col)) {
                cell.setCellStyle(cellStyleMap.get(col));
            }
            col++;
        }
    }

    public void createRow(List<Object> rowData) {
        createRow(rowData.toArray());
    }

    private void _setCellValue(Cell cell, Object obj) {

        // double
        if (obj instanceof Double) {
            cell.setCellValue((double) obj);
        }
        // float
        else if (obj instanceof Float) {
            cell.setCellValue((float) obj);
        }
        // int
        else if (obj instanceof Integer) {
            cell.setCellValue((int) obj);
        }
        // long
        else if (obj instanceof Long) {
            cell.setCellValue((long) obj);
        }
        // date
        else if (obj instanceof Date) {
            cell.setCellValue((Date) obj);
        }
        // calendar
        else if (obj instanceof Calendar) {
            cell.setCellValue((Calendar) obj);
        }
        // RichTextString
        else if (obj instanceof RichTextString) {
            cell.setCellValue((RichTextString) obj);
        }
        // string
        else if (obj instanceof String) {
            cell.setCellValue((String) obj);
        }
        // boolean
        else if (obj instanceof Boolean) {
            cell.setCellValue((boolean) obj);
        }
        // BigDecimal
        else if (obj instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) obj).floatValue());
        }
    }

    public void write(OutputStream os) throws IOException {
        workbook.write(os);
    }

    public CellStyle createCellStyle(String fmt) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(creationHelper.createDataFormat().getFormat(fmt));
        return style;
    }
}
