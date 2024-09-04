package com.tools.point.of.sale.controller;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import com.tools.point.of.sale.dto.ToolDto;
import com.tools.point.of.sale.service.CheckoutService;
import com.tools.point.of.sale.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing the point of sale operations.
 * This controller handles HTTP requests related to tools and checkout operations.
 *
 *  @author melessweldemichael
 */
@RestController
public class PointOfSaleController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private ToolService toolService;

    /**
     * Endpoint to add a new tool.
     *
     * @param toolDto Data Transfer Object containing tool details to be added.
     * @return Response message indicating the result of the operation.
     */
    @PostMapping("/tool")
    public String addTool(@RequestBody ToolDto toolDto) {
        return toolService.addTool(toolDto);
    }

    /**
     * Endpoint to add multiple new tools.
     *
     * @param toolDto List of Data Transfer Objects containing details of tools to be added.
     * @return Response message indicating the result of the operation.
     */
    @PostMapping("/tools")
    public String addTools(@RequestBody List<ToolDto> toolDto) {
        return toolService.addTools(toolDto);
    }

    /**
     * Endpoint to perform a tool checkout operation.
     *
     * @param checkoutDto Data Transfer Object containing checkout details.
     * @return RentalAgreementDto containing details of the rental agreement or an error message.
     */
    @PostMapping("/checkout")
    public RentalAgreementDto checkout(@RequestBody CheckoutDto checkoutDto) {
        // Process the checkout and return the rental agreement details
        return checkoutService.checkoutTool(checkoutDto);
    }
}
