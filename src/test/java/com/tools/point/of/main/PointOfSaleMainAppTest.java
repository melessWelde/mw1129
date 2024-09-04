package com.tools.point.of.main;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointOfSaleMainAppTest {

    private PointOfSaleMainApp pointOfSaleMainApp = new PointOfSaleMainApp();

    @Test
    void testPerformCheckouts() {
        List<CheckoutDto> checkoutDtoList = new ArrayList<>();
        // Arrange
        CheckoutDto dto1 = new CheckoutDto();
        dto1.setToolCode("JAKR");
        dto1.setRentalDays(3);
        dto1.setDiscountPercent(25);
        dto1.setCheckoutDate("09/04/2024");
        checkoutDtoList.add(dto1);

        CheckoutDto dto2 = new CheckoutDto();
        dto2.setToolCode("LADW");
        dto2.setRentalDays(5);
        dto2.setDiscountPercent(25);
        dto2.setCheckoutDate("09/05/2024");
        checkoutDtoList.add(dto2);

        CheckoutDto dto3 = new CheckoutDto();
        dto3.setToolCode("CHNS");
        dto3.setRentalDays(10);
        dto3.setDiscountPercent(15);
        dto3.setCheckoutDate("09/06/2024");
        checkoutDtoList.add(dto3);

        CheckoutDto dto4 = new CheckoutDto();
        dto4.setToolCode("JAKD");
        dto4.setRentalDays(5);
        dto4.setDiscountPercent(10);
        dto4.setCheckoutDate("09/01/2024");
        checkoutDtoList.add(dto4);

        CheckoutDto dto5 = new CheckoutDto();
        dto5.setToolCode("JAKD");
        dto5.setRentalDays(0);
        dto5.setDiscountPercent(10);
        dto5.setCheckoutDate("09/01/2024");
        checkoutDtoList.add(dto5);

        // Act
        List<RentalAgreementDto> rentalAgreementDtos =  pointOfSaleMainApp.performCheckouts(checkoutDtoList);
        assertNotNull(rentalAgreementDtos);
    }
}