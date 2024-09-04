package com.tools.point.of.sale.service;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import jakarta.transaction.Transactional;

public interface CheckoutService {

    @Transactional
    RentalAgreementDto checkoutTool(CheckoutDto rental);
}
