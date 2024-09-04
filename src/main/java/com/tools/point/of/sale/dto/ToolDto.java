package com.tools.point.of.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ToolDto implements Serializable {
    private String toolCode;
    private String toolType;
    private String brand;
    private float dailyCharge;
    private String weekdayCharge;
    private String weekendCharge;
    private String holidayCharge;
}
