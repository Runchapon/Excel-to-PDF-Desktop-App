package org.excel.pdf.model;

import java.util.List;

public enum ExcelHeader {

    ORDER("ลำดับที่"),
    ROOM("ห้อง"),
    NAME("รายชื่อ"),
    RENT("ค่าเช่า"),
    ELECTRIC("ค่าไฟ", 4),
    WATER("ค่าน้ำ", 4),
    COMMON_FEE("ส่วนกลาง"),
    OUTSTANDING("ค้าง"),
    WIFI("wifi"),
    TOTAL("สุทธิ");

    public static final List<ExcelHeader> excelHeaderList = List.of(
            ORDER,
            ROOM,
            NAME,
            RENT,
            ELECTRIC,
            WATER,
            COMMON_FEE,
            OUTSTANDING,
            WIFI,
            TOTAL
    );
    private final String name;
    private final int numMergeCell;

    public enum SubExcelHeader {
        UNIT_ELECTRIC(ELECTRIC,"หน่วย"),
        UNIT_WATER(WATER,"หน่วย");
        private final ExcelHeader parentHeader;
        private final String name;

        public static final List<SubExcelHeader> excelSubHeaderList = List.of(
                UNIT_ELECTRIC,
                UNIT_WATER
        );

        SubExcelHeader(ExcelHeader parentHeader, String name) {
            this.parentHeader = parentHeader;
            this.name = name;
        }

        public ExcelHeader getParentHeader() {
            return parentHeader;
        }

        public String getName() {
            return name;
        }
    }

    ExcelHeader(String name) {
        this.name = name;
        numMergeCell = 0;
    }

    ExcelHeader(String name, int numMergeCell) {
        this.name = name;
        this.numMergeCell = numMergeCell;
    }

    public String getName() {
        return name;
    }

    public int getNumMergeCell() {
        return numMergeCell;
    }
}
