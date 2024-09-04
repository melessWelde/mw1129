package com.tools.point.of.sale.service.impl;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.ProcessResult;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import com.tools.point.of.sale.entity.RentalAgreement;
import com.tools.point.of.sale.entity.Tool;
import com.tools.point.of.sale.repository.RentalAgreementRepository;
import com.tools.point.of.sale.repository.ToolRepository;
import com.tools.point.of.sale.service.CheckoutService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static com.tools.point.of.sale.util.ToolsRentalUtil.*;

/**
 *  This service handles the checkout process for tools, including validation,
 * calculation of charges and (returning and saving) rental agreements.
 *
 * @author melessweldemichael
 */
@Component
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private ToolRepository toolRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    /**
     * Processes the checkout of a tool based on the provided checkout details.
     * This method validates input, performs calculations, and returns a RentalAgreementDto
     * with details of the rental agreement or an error message.
     *
     * @param checkoutDto Data Transfer Object containing the checkout details.
     * @return RentalAgreementDto containing the results of the checkout process.
     */
    @Transactional
    @Override
    public RentalAgreementDto checkoutTool(CheckoutDto checkoutDto) {
        try {
            RentalAgreementDto rentalAgreementDto = validateCheckout(checkoutDto);
            // check for invalid checkout values
            if (rentalAgreementDto.getProcessResult() != null) {
                return rentalAgreementDto;
            }
            Tool tool = toolRepository.getById(checkoutDto.getToolCode());
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
            saveCheckOutData(buildRentalAgreement(rentalAgreementDto));
            return rentalAgreementDto;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Saves the rental agreement information to the repository.
     * @param rentalAgreement The RentalAgreement object to be saved.
     */
    private void saveCheckOutData(RentalAgreement rentalAgreement) {
        rentalAgreementRepository.save(rentalAgreement);
    }

    /**
     * Builds a RentalAgreement entity from the provided RentalAgreementDto.
     * @param rentalAgreementDto Data Transfer Object containing rental agreement details.
     * @return RentalAgreement entity built from the DTO.
     */
    private RentalAgreement buildRentalAgreement(RentalAgreementDto rentalAgreementDto) {

        return RentalAgreement.builder().toolCode(rentalAgreementDto.getToolCode())
                .toolType(rentalAgreementDto.getToolType())
                .toolBrand(rentalAgreementDto.getToolBrand())
                .rentalDays(rentalAgreementDto.getRentalDays())
                .dueDate(rentalAgreementDto.getDueDate())
                .checkOutDate(rentalAgreementDto.getCheckOutDate())
                .dailyRentalCharge(rentalAgreementDto.getDailyRentalCharge())
                .chargeDays(rentalAgreementDto.getChargeDays())
                .preDiscountCharge(rentalAgreementDto.getPreDiscountCharge())
                .discountPercent(rentalAgreementDto.getDiscountPercent())
                .discountAmount(rentalAgreementDto.getDiscountAmount())
                .finalCharge(rentalAgreementDto.getFinalCharge())
                .build();
    }
}
