package com.tools.point.of.sale.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalAgreement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private String checkOutDate;
    private String dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private double discountPercent;
    private double discountAmount;
    private double finalCharge;
}
