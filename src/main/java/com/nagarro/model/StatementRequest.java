package com.nagarro.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class StatementRequest {

    @Pattern(regexp = "^[0-9]+", message = "account number should be in digits")
    private String accountId;

    @Pattern(regexp = "^[0-3][0-9].[0-3][0-9].(?:[0-9][0-9])?[0-9][0-9]", message = "invalid from date format provided")
    private String fromDate;

    @Pattern(regexp = "^[0-3][0-9].[0-3][0-9].(?:[0-9][0-9])?[0-9][0-9]", message = "invalid to date format provided")
    private String endDate;

    @DecimalMin(value = "0", message = "invalid amount provided")
    private BigDecimal fromAmt;

    @DecimalMin(value = "0", message = "invalid amount provided")
    private BigDecimal toAmt;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getFromAmt() {
        return fromAmt;
    }

    public void setFromAmt(BigDecimal fromAmt) {
        this.fromAmt = fromAmt;
    }

    public BigDecimal getToAmt() {
        return toAmt;
    }

    public void setToAmt(BigDecimal toAmt) {
        this.toAmt = toAmt;
    }
}
