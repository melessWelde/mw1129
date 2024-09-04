package com.tools.point.of.sale.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tools.point.of.sale.util.CurrencySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RentalAgreementDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private String checkOutDate;
    private String dueDate;
    @JsonSerialize(using = CurrencySerializer.class)
    private float dailyRentalCharge;
    private int chargeDays;
    @JsonSerialize(using = CurrencySerializer.class)
    private float preDiscountCharge;
    private float discountPercent;
    @JsonSerialize(using = CurrencySerializer.class)
    private float discountAmount;
    @JsonSerialize(using = CurrencySerializer.class)
    private float finalCharge;
    private ProcessResult processResult;

    @Override
    public String toString() {
        return "RentalAgreementDto{" + "\n" +
                " Tool code:" + toolCode + "\n" +
                " Tool type:" + toolType + "\n" +
                " Tool brand:" + toolBrand + "\n" +
                " Rental days:" + rentalDays + "\n" +
                " Check out date:" + checkOutDate + "\n" +
                " Due date:" + dueDate + "\n" +
                " Daily rental charge:" + currencyFormatter.format(dailyRentalCharge) + "\n" +
                " Charge days:" + chargeDays + "\n" +
                " Pre-discount charge:" + currencyFormatter.format(preDiscountCharge) + "\n" +
                " Discount percent:" + discountPercent + "%" + "\n" +
                " Discount amount:" + currencyFormatter.format(discountAmount) + "\n" +
                " Final charge:" + currencyFormatter.format(finalCharge) + "\n" +
                " processResult:" + processResult +
                '}';
    }
}
