package org.excel.pdf.model;

import java.math.BigDecimal;
import java.util.Objects;

public class TenantData {
    private String name;
    private String roomNumber;
    private BigDecimal electricityUnit;
    private BigDecimal waterUnit;
    //ส่วนกลาง
    private BigDecimal commonFee;
    private BigDecimal wifi;
    //คงค้าง
    private BigDecimal outstandingBalance;
    private BigDecimal totalAmount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getElectricityUnit() {
        return electricityUnit;
    }

    public void setElectricityUnit(BigDecimal electricityUnit) {
        this.electricityUnit = electricityUnit;
    }

    public BigDecimal getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(BigDecimal waterUnit) {
        this.waterUnit = waterUnit;
    }

    public BigDecimal getCommonFee() {
        return commonFee;
    }

    public void setCommonFee(BigDecimal commonFee) {
        this.commonFee = commonFee;
    }

    public BigDecimal getWifi() {
        return wifi;
    }

    public void setWifi(BigDecimal wifi) {
        this.wifi = wifi;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantData that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(roomNumber, that.roomNumber) && Objects.equals(electricityUnit, that.electricityUnit) && Objects.equals(waterUnit, that.waterUnit) && Objects.equals(commonFee, that.commonFee) && Objects.equals(wifi, that.wifi) && Objects.equals(outstandingBalance, that.outstandingBalance) && Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, roomNumber, electricityUnit, waterUnit, commonFee, wifi, outstandingBalance, totalAmount);
    }

    @Override
    public String toString() {
        return "TenentData{" +
                "name='" + name + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", electricityUnit=" + electricityUnit +
                ", waterUnit=" + waterUnit +
                ", commonFee=" + commonFee +
                ", wifi=" + wifi +
                ", outstandingBalance=" + outstandingBalance +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
