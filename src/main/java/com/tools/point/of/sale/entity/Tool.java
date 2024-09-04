package com.tools.point.of.sale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Tool implements Serializable {
    @Id
    private String toolCode;
    private String toolType;
    private String brand;
    private float dailyCharge;
    private String weekdayCharge;
    private String weekendCharge;
    private String holidayCharge;
}
