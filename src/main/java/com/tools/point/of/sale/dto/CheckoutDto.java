package com.tools.point.of.sale.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;


@ToString
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CheckoutDto implements Serializable {

    private String toolCode;
    @Min(1)
    private Integer rentalDays;
    @Min(0)
    @Max(100)
    private Integer discountPercent;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private String checkoutDate;

}
