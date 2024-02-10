package org.excel.pdf.exception;

public class ExcelSheetNotFound extends Exception{
    public ExcelSheetNotFound() {
    }

    public ExcelSheetNotFound(String message) {
        super(message);
    }

    public ExcelSheetNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelSheetNotFound(Throwable cause) {
        super(cause);
    }

    public ExcelSheetNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
