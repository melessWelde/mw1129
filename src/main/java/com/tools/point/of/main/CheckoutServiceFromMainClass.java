package com.tools.point.of.main;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.ProcessResult;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import com.tools.point.of.sale.dto.ToolDto;
import com.tools.point.of.sale.util.ToolsStaticDb;

import java.time.format.DateTimeFormatter;

import static com.tools.point.of.sale.util.ToolsRentalUtil.*;
/**
 * This class provides the checkout service for rental tools.
 * It uses a static database (`ToolsStaticDb`) to retrieve tool details and to save rental agreements.
 * It handles the processing of checkout requests by validating input data, calculating charges, and storing rental agreements.
 *
 * @author melessweldemichael
 */

public class CheckoutServiceFromMainClass {
    ToolsStaticDb toolsStaticDb = new ToolsStaticDb();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Processes a tool checkout request.
     * Validates the provided checkout details, calculates charges, and returns a `RentalAgreementDto` with the checkout results.
     * If validation fails, returns an error message within the `RentalAgreementDto`.
     *
     * @param checkoutDto The checkout details including tool code, rental days, discount percent, and checkout date.
     * @return A `RentalAgreementDto` containing the result of the checkout process, including rental charges and dates.
     */
    public RentalAgreementDto checkoutTool(CheckoutDto checkoutDto) {
        try {
            RentalAgreementDto rentalAgreementDto = validateCheckout(checkoutDto);
            // check for invalid checkout values
            if (rentalAgreementDto.getProcessResult() != null) {
                return rentalAgreementDto;
            }
            // Retrieve tool details from the static database
            ToolDto tool = toolsStaticDb.getToolDetails(checkoutDto.getToolCode());

            // Set details in the rental agreement DTO
            rentalAgreementDto.setRentalDays(checkoutDto.getRentalDays());
            rentalAgreementDto.setToolCode(checkoutDto.getToolCode());
            rentalAgreementDto.setDiscountPercent(checkoutDto.getDiscountPercent());
            rentalAgreementDto.setCheckOutDate(extractFormatedDate(checkoutDto.getCheckoutDate()));
            rentalAgreementDto.setToolType(tool.getToolType());
            rentalAgreementDto.setToolBrand(tool.getBrand());
            rentalAgreementDto.setDueDate(setDueDate(checkoutDto.getCheckoutDate(), checkoutDto.getRentalDays()));
            rentalAgreementDto.setDailyRentalCharge(tool.getDailyCharge());
            rentalAgreementDto.setChargeDays(getNumberOfChargeDays(tool.getToolType(), checkoutDto.getCheckoutDate(), checkoutDto.getRentalDays()));
            rentalAgreementDto.setPreDiscountCharge(calculatePreDiscountCharge(rentalAgreementDto.getDailyRentalCharge(), rentalAgreementDto.getChargeDays()));
            rentalAgreementDto.setDiscountPercent(checkoutDto.getDiscountPercent());
            rentalAgreementDto.setDiscountAmount(calculateDiscountAmount(checkoutDto.getDiscountPercent(), rentalAgreementDto.getPreDiscountCharge()));
            rentalAgreementDto.setFinalCharge(calculateFinalCharge(rentalAgreementDto.getPreDiscountCharge(), rentalAgreementDto.getDiscountAmount()));
            rentalAgreementDto.setProcessResult(ProcessResult.builder().status("Ok").message("Checkout Successful").build());
            saveCheckOutData(rentalAgreementDto);
            return rentalAgreementDto;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void saveCheckOutData(RentalAgreementDto rentalAgreement) {
        toolsStaticDb.populateRentalAggreement(rentalAgreement);
    }

}
