package wang.huaichao.data.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import wang.huaichao.data.entity.crm.MeetingRecord;
import wang.huaichao.utils.FileUtils;

import java.awt.*;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 10/25/2016.
 */
@Service
public class StatisticsService {
    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    @Qualifier("crmJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void exportMontholyPstnFeeSummarizedBySalse(int billingPeriod, OutputStream os) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/statistics/pstn-monthly-fee-summarized-by-salse.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("billingPeriod", billingPeriod);

        List<PstnFeeBySalse> pstnFeeBySalses = jdbcTemplate.query(sql, params, new RowMapper<PstnFeeBySalse>() {
            @Override
            public PstnFeeBySalse mapRow(ResultSet rs, int rowNum) throws SQLException {
                PstnFeeBySalse fee = new PstnFeeBySalse();
                fee.setSalesName(rs.getString("name"));
                fee.setEmail(rs.getString("email"));
                fee.setMins(rs.getInt("mins"));
                fee.setCost(rs.getBigDecimal("cost"));
                return fee;
            }
        });

        _createExcel(pstnFeeBySalses, os);
    }

    class PstnFeeBySalse {
        private String salesName;
        private String email;
        private int mins;
        private BigDecimal cost;

        public String getSalesName() {
            return salesName;
        }

        public void setSalesName(String salesName) {
            this.salesName = salesName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getMins() {
            return mins;
        }

        public void setMins(int mins) {
            this.mins = mins;
        }

        public BigDecimal getCost() {
            return cost;
        }

        public void setCost(BigDecimal cost) {
            this.cost = cost;
        }
    }

    private void _createExcel(List<PstnFeeBySalse> pstnFeeBySalses, OutputStream os) throws IOException {
        Workbook wb = new XSSFWorkbook();


        String safeSheetName = WorkbookUtil.createSafeSheetName("销售员电话费用");
        Sheet sheet = wb.createSheet(safeSheetName);

        CreationHelper createHelper = wb.getCreationHelper();

        CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        CellStyle floatFormat = wb.createCellStyle();
        floatFormat.setDataFormat(createHelper.createDataFormat().getFormat("###,##0.00"));

        CellStyle intFormat = wb.createCellStyle();
        intFormat.setDataFormat(createHelper.createDataFormat().getFormat("###,###,###"));

        CellStyle cellStyle = wb.createCellStyle();
        XSSFColor xssfColor = new XSSFColor("0088cc".getBytes());
        cellStyle.setFillBackgroundColor(xssfColor.getIndexed());

        int rowIdx = 0;
        int cellIdx = 0;
        Cell cell;
        String[] headers = "销售员姓名,销售员邮箱,电话分钟数,电话费用总计(元)".split(",");
        int[] headerWths = {15, 35, 20, 20};
        short[] headerAligns = {
            CellStyle.ALIGN_LEFT,
            CellStyle.ALIGN_LEFT,
            CellStyle.ALIGN_RIGHT,
            CellStyle.ALIGN_RIGHT
        };


        Row row = sheet.createRow(rowIdx++);


        for (int i = 0; i < headerWths.length; i++) {
            sheet.setColumnWidth(i, headerWths[i] * 256);
            Cell x = row.createCell(cellIdx++);
            x.setCellValue(headers[i]);
        }

        for (PstnFeeBySalse record : pstnFeeBySalses) {
            row = sheet.createRow(rowIdx++);

            cellIdx = 0;

            row.createCell(cellIdx++).setCellValue(record.getSalesName());
            row.createCell(cellIdx++).setCellValue(record.getEmail());

            cell = row.createCell(cellIdx++);
            cell.setCellValue(record.getMins());
            cell.setCellStyle(intFormat);

            cell = row.createCell(cellIdx++);
            cell.setCellValue(record.getCost().doubleValue());
            cell.setCellStyle(floatFormat);
        }

        wb.write(os);
    }
}
