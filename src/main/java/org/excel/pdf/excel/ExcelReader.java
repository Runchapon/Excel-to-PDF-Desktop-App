package org.excel.pdf.excel;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.excel.pdf.exception.ExcelSheetNotFound;
import org.excel.pdf.model.ExcelHeader;
import org.excel.pdf.model.TenantData;
import org.excel.pdf.pdf.PDFGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Stream;

import static org.excel.pdf.model.ExcelHeader.*;
import static org.excel.pdf.model.ExcelHeader.SubExcelHeader.excelSubHeaderList;

public class ExcelReader {

    public static final String FIRST_SHEET_NAME = "SEP21";
    /***
     How many rows that using for header
     */
    private int headerRow = 2;
    /***
     path to target excel file
     */
    private String filePath;
    /***
     The first data row is @param headerRow + 1
     */
    private int lastDataRow;

    public static void main(String[] args) throws IOException, ExcelSheetNotFound, URISyntaxException {
        var e = new ExcelReader();
        e.setFilePath("C:\\Users\\run10\\OneDrive - Chulalongkorn University\\Desktop\\test_file_feb24.xlsx");
        e.setLastDataRow(57);
        List<TenantData> tenantData = e.readTenantDataFromExcel();
        tenantData.forEach(System.out::println);

        var pdf = new PDFGenerator();
        pdf.generate();
    }

    public List<TenantData> readTenantDataFromExcel() throws IOException, ExcelSheetNotFound {
        var data = readExcel();
        Map<ExcelHeader, Integer> header = findHeader(data);
        System.out.println(data);
        List<TenantData> tenants = new ArrayList<>();
        data.forEach((k, v) -> {
            if (k > headerRow && k <= lastDataRow) {
                TenantData t = new TenantData();
                t.setName(v.get(header.get(NAME)));
                t.setRoomNumber(v.get(header.get(ROOM)));
                t.setElectricityUnit(getDataInExcelRow(v, header, ELECTRIC));
                t.setWaterUnit(getDataInExcelRow(v, header, WATER));
                t.setCommonFee(getDataInExcelRow(v, header, COMMON_FEE));
                t.setWifi(getDataInExcelRow(v, header, WIFI));
                t.setOutstandingBalance(getDataInExcelRow(v, header, OUTSTANDING));
                t.setTotalAmount(getDataInExcelRow(v, header, TOTAL));
                tenants.add(t);
            }
        });
        System.out.println(tenants);
        return tenants;
    }

    private static BigDecimal getDataInExcelRow(List<String> v, Map<ExcelHeader, Integer> header, ExcelHeader electric) {
        return new BigDecimal(String.valueOf(v.get(header.get(electric)) == null ? 0 : v.get(header.get(electric))));
    }

    private Map<ExcelHeader,Integer> findHeader(final Map<Integer, List<String>> data) {
        Map<ExcelHeader, Integer> map = new HashMap<>();
        for (int i = 1; i < headerRow + 1; i++) {
            switch (i) {
                case 1 -> {
                    for (ExcelHeader e : excelHeaderList) {
                        map.put(e, data.get(i).indexOf(e.getName()));
                    }
                }
                case 2 -> {
                    for (ExcelHeader.SubExcelHeader e : excelSubHeaderList) {
                        int numMergeCell = e.getParentHeader().getNumMergeCell();
                        Integer index = map.get(e.getParentHeader());
                        List<String> subList = data.get(i).subList(index, index + numMergeCell);
                        int value = subList.indexOf(e.getName()) + index + 1;
                        map.put(e.getParentHeader(), value);
                    }
                }
                default -> {
                }
            }
        }
        return map;
    }

    private Map<Integer, List<String>> readExcel() throws IOException, ExcelSheetNotFound {
        Map<Integer, List<String>> rawData = new HashMap<>();

        try (FileInputStream file = new FileInputStream(filePath); ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Optional<Sheet> dataSheet = wb.getSheets().filter(s -> s.getName().equals(FIRST_SHEET_NAME)).findAny();
            if (dataSheet.isEmpty()) {
                throw new ExcelSheetNotFound(String.format("Sheet %s is could not be found", FIRST_SHEET_NAME));
            }
            try (Stream<Row> rows = dataSheet.get().openStream()) {
                rows.forEach(r -> {
                    rawData.put(r.getRowNum(), new ArrayList<>());

                    for (Cell cell : r) {
                        if (!Objects.isNull(cell)) {
                            rawData.get(r.getRowNum()).add(cell.getRawValue());
                        }
                    }
                });
            }

            return rawData;
        }
    }

    public int getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(int headerRow) {
        this.headerRow = headerRow;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLastDataRow() {
        return lastDataRow;
    }

    public void setLastDataRow(int lastDataRow) {
        this.lastDataRow = lastDataRow;
    }
}
