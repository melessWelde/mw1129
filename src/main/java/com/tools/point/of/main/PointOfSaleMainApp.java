package com.tools.point.of.main;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.RentalAgreementDto;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main application class for testing the checkout functionality of different tools.
 * This class contains the entry point to the application where various tool checkouts are tested.
 * It demonstrates how different tools (JackHammer, Ladder, Chainsaw) are processed with specified rental days and discounts.
 *
 * @author melessweldemichael
 */
public class PointOfSaleMainApp {
    private final CheckoutServiceFromMainClass checkoutService = new CheckoutServiceFromMainClass();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * The entry point of the application.
     * This method creates instances of `CheckoutDto` for different tools and tests the `checkoutTool` method
     * from the `CheckoutServiceFromMainClass` class.
     * It parses dates, sets rental details, and prints the checkout results for various tools.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        PointOfSaleMainApp app = new PointOfSaleMainApp();
        List<CheckoutDto> checkoutDtoList = new ArrayList<>();
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setCheckoutDate("09/04/2024");
        checkoutDto.setRentalDays(3);
        checkoutDto.setDiscountPercent(25);
        checkoutDto.setToolCode("JAKR");
        checkoutDtoList.add(checkoutDto);

        CheckoutDto checkoutDtoL = new CheckoutDto();
        checkoutDtoL.setCheckoutDate("09/05/2024");
        checkoutDtoL.setRentalDays(5);
        checkoutDtoL.setDiscountPercent(25);
        checkoutDtoL.setToolCode("LADW");
        checkoutDtoList.add(checkoutDtoL);

        CheckoutDto checkoutDtoC = new CheckoutDto();
        checkoutDtoC.setCheckoutDate("09/05/2024");
        checkoutDtoC.setRentalDays(10);
        checkoutDtoC.setDiscountPercent(15);
        checkoutDtoC.setToolCode("CHNS");
        checkoutDtoList.add(checkoutDtoC);

        CheckoutDto checkoutDtoJ = new CheckoutDto();
        checkoutDtoJ.setCheckoutDate("09/01/2024");
        checkoutDtoJ.setRentalDays(5);
        checkoutDtoJ.setDiscountPercent(15);
        checkoutDtoJ.setToolCode("JAKD");
        checkoutDtoList.add(checkoutDtoJ);

        CheckoutDto checkoutDtoJakInv = new CheckoutDto();
        try {
            checkoutDtoJakInv.setCheckoutDate("09/01/2024");
        } catch (DateTimeParseException e) {
            System.out.println("Failed to parse date: " + e.getMessage());
        }
        checkoutDtoJakInv.setRentalDays(0);
        checkoutDtoJakInv.setDiscountPercent(15);
        checkoutDtoJakInv.setToolCode("JAKD");
        checkoutDtoList.add(checkoutDtoJakInv);

        app.performCheckouts(checkoutDtoList);
    }

    public List<RentalAgreementDto> performCheckouts(List<CheckoutDto> checkoutDtoList) {
        List<RentalAgreementDto> rentalAgreementDtoList = new ArrayList<>();
        for(CheckoutDto checkoutDto : checkoutDtoList){
             rentalAgreementDtoList.add(performCheckout(checkoutDto));
        }
        return rentalAgreementDtoList;
    }

    private RentalAgreementDto performCheckout(CheckoutDto checkoutDto) {
        System.out.println("================== " + checkoutDto.getToolCode() + " checkout start ===================== ");
        RentalAgreementDto rentalAgreementDto = checkoutService.checkoutTool(checkoutDto);
        System.out.println(rentalAgreementDto);
        System.out.println("================== " + checkoutDto.getToolCode() + " checkout end ===================== ");
        System.out.println("\n");
        return rentalAgreementDto;
    }

}
