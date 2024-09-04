package com.tools.point.of.sale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.ProcessResult;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import com.tools.point.of.sale.dto.ToolDto;
import com.tools.point.of.sale.service.CheckoutService;
import com.tools.point.of.sale.service.ToolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PointOfSaleController.class)
public class PointOfSaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @MockBean
    private ToolService toolService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTool() throws Exception {
        ToolDto toolDto = new ToolDto();
        toolDto.setToolCode("LADW");
        toolDto.setToolType("Ladder");
        toolDto.setBrand("Werner");
        toolDto.setDailyCharge(1.99f);
        toolDto.setWeekdayCharge("Yes");
        toolDto.setHolidayCharge("Yes");
        toolDto.setWeekendCharge("No");

        when(toolService.addTool(any(ToolDto.class))).thenReturn("Saved");

        mockMvc.perform(post("/tool")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(toolDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved"));
    }

    @Test
    public void testAddTools() throws Exception {
        ToolDto toolDto = new ToolDto();
        toolDto.setToolCode("LADW");
        toolDto.setToolType("Ladder");
        toolDto.setBrand("Werner");
        toolDto.setDailyCharge(1.99f);
        toolDto.setWeekdayCharge("Yes");
        toolDto.setHolidayCharge("Yes");
        toolDto.setWeekendCharge("No");

        ToolDto toolDto1 = new ToolDto();
        toolDto1.setToolCode("CHNS");
        toolDto1.setToolType("Chainsaw");
        toolDto1.setBrand("Stihl");
        toolDto1.setDailyCharge(1.49f);
        toolDto1.setWeekdayCharge("Yes");
        toolDto1.setHolidayCharge("No");
        toolDto1.setWeekendCharge("Yes");

        ToolDto toolDto2 = new ToolDto();
        toolDto2.setToolCode("JAKD");
        toolDto2.setToolType("Jackhammer");
        toolDto2.setBrand("DeWalt");
        toolDto2.setDailyCharge(2.99f);
        toolDto2.setWeekdayCharge("Yes");
        toolDto2.setHolidayCharge("No");
        toolDto2.setWeekendCharge("No");

        List<ToolDto> toolDtos = Arrays.asList(toolDto, toolDto1, toolDto2);

        when(toolService.addTools(anyList())).thenReturn("Saved");

        mockMvc.perform(post("/tools")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(toolDtos)))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved"));
    }

    @Test
    public void testCheckoutWithValidData() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(3);
        checkoutDto.setDiscountPercent(25);
        checkoutDto.setCheckoutDate("2024-09-04");

        RentalAgreementDto rentalAgreementDto = new RentalAgreementDto();
        rentalAgreementDto.setProcessResult(ProcessResult.builder().status("Ok").message("Checkout Successful").build());

        when(checkoutService.checkoutTool(any(CheckoutDto.class))).thenReturn(rentalAgreementDto);

        mockMvc.perform(post("/checkout")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(checkoutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processResult.status").value("Ok"))
                .andExpect(jsonPath("$.processResult.message").value("Checkout Successful"));
    }

    @Test
    public void testCheckoutWithInvalidDiscount() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(3);
        checkoutDto.setDiscountPercent(105); // Invalid discount
        checkoutDto.setCheckoutDate("2024-09-04");

        RentalAgreementDto rentalAgreementDto = RentalAgreementDto.builder()
                .processResult(ProcessResult.builder().status("Error").message("Percentage should be between 0 to 100").build())
                .build();

        when(checkoutService.checkoutTool(any(CheckoutDto.class))).thenReturn(rentalAgreementDto);

        mockMvc.perform(post("/checkout")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(checkoutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processResult.status").value("Error"))
                .andExpect(jsonPath("$.processResult.message").value("Percentage should be between 0 to 100"));
    }

    @Test
    public void testCheckoutWithInvalidRentalDays() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(0); // Invalid rental days
        checkoutDto.setDiscountPercent(25);
        checkoutDto.setCheckoutDate("2024-09-04");

        RentalAgreementDto rentalAgreementDto = RentalAgreementDto.builder()
                .processResult(ProcessResult.builder().status("Error").message("rental days should be 1 day or more").build())
                .build();

        when(checkoutService.checkoutTool(any(CheckoutDto.class))).thenReturn(rentalAgreementDto);

        mockMvc.perform(post("/checkout")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(checkoutDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processResult.status").value("Error"))
                .andExpect(jsonPath("$.processResult.message").value("rental days should be 1 day or more"));
    }
}