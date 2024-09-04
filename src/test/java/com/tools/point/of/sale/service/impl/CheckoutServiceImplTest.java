package com.tools.point.of.sale.service.impl;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import com.tools.point.of.sale.entity.RentalAgreement;
import com.tools.point.of.sale.entity.Tool;
import com.tools.point.of.sale.repository.RentalAgreementRepository;
import com.tools.point.of.sale.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckoutServiceImplTest {
    @Mock
    private ToolRepository toolRepository;

    @Mock
    private RentalAgreementRepository rentalAgreementRepository;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckoutToolForJackHammerSuccess() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(3);
        checkoutDto.setDiscountPercent(25);
        checkoutDto.setCheckoutDate("09/04/2024");

        Tool tool = new Tool();
        tool.setToolCode("JAKR");
        tool.setToolType("Jackhammer");
        tool.setBrand("DeWalt");
        tool.setDailyCharge(2.99f);
        tool.setWeekdayCharge("Yes");
        tool.setWeekendCharge("No");
        tool.setHolidayCharge("No");

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Populate rentalAgreement with expected values

        when(toolRepository.getById("JAKR")).thenReturn(tool);
        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenReturn(rentalAgreement);

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("JAKR", result.getToolCode());
        assertEquals(3, result.getRentalDays());
        assertEquals(25, result.getDiscountPercent());
        assertEquals(LocalDate.of(2024, 9, 7).format(formatter), result.getDueDate());
        assertEquals(5.98f, result.getPreDiscountCharge());
        assertEquals(1.50f, result.getDiscountAmount());
        assertEquals(4.48f, result.getFinalCharge());
        verify(rentalAgreementRepository, times(1)).save(any(RentalAgreement.class));
    }

    @Test
    public void testCheckoutToolJAKInvalidDiscountPercent_Test1() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(5);
        checkoutDto.setDiscountPercent(101);
        checkoutDto.setCheckoutDate("09/03/2015");

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("Error", result.getProcessResult().getStatus());
        assertEquals("Percentage should be between 0 to 100", result.getProcessResult().getMessage());
    }

    @Test
    public void testCheckoutToolJAKInvalidDiscountPercent_Test4() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKD");
        checkoutDto.setRentalDays(6);
        checkoutDto.setDiscountPercent(0);
        checkoutDto.setCheckoutDate("09/02/2015");

        Tool tool = new Tool();
        tool.setToolCode("JAKD");
        tool.setToolType("Jackhammer");
        tool.setBrand("DeWalt");
        tool.setDailyCharge(2.99f);
        tool.setWeekdayCharge("Yes");
        tool.setWeekendCharge("No");
        tool.setHolidayCharge("No");

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Populate rentalAgreement with expected values

        when(toolRepository.getById("JAKD")).thenReturn(tool);
        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenReturn(rentalAgreement);

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("JAKD", result.getToolCode());
        assertEquals(6, result.getRentalDays());
        assertEquals(0, result.getDiscountPercent());
        assertEquals(LocalDate.of(2015, 9, 8).format(formatter), result.getDueDate());
        assertEquals(8.97f, result.getPreDiscountCharge());
        assertEquals(0.0f, result.getDiscountAmount());
        assertEquals(8.97f, result.getFinalCharge());
        verify(rentalAgreementRepository, times(1)).save(any(RentalAgreement.class));
    }

    @Test
    public void testCheckoutToolJAKInvalidDiscountPercent_Test5() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(9);
        checkoutDto.setDiscountPercent(0);
        checkoutDto.setCheckoutDate("07/02/2015");

        Tool tool = new Tool();
        tool.setToolCode("JAKR");
        tool.setToolType("Jackhammer");
        tool.setBrand("Ridgid");
        tool.setDailyCharge(2.99f);
        tool.setWeekdayCharge("Yes");
        tool.setWeekendCharge("No");
        tool.setHolidayCharge("No");

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Populate rentalAgreement with expected values

        when(toolRepository.getById("JAKR")).thenReturn(tool);
        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenReturn(rentalAgreement);

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("JAKR", result.getToolCode());
        assertEquals(9, result.getRentalDays());
        assertEquals(0, result.getDiscountPercent());
        assertEquals(LocalDate.of(2015, 7, 11).format(formatter), result.getDueDate());
        assertEquals(14.95f, result.getPreDiscountCharge());
        assertEquals(0.0f, result.getDiscountAmount());
        assertEquals(14.95f, result.getFinalCharge());
        verify(rentalAgreementRepository, times(1)).save(any(RentalAgreement.class));
    }

    @Test
    public void testCheckoutToolJAKInvalidDiscountPercent_Test6() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("JAKR");
        checkoutDto.setRentalDays(4);
        checkoutDto.setDiscountPercent(50);
        checkoutDto.setCheckoutDate("07/02/2020");

        Tool tool = new Tool();
        tool.setToolCode("JAKR");
        tool.setToolType("Jackhammer");
        tool.setBrand("Ridgid");
        tool.setDailyCharge(2.99f);
        tool.setWeekdayCharge("Yes");
        tool.setWeekendCharge("No");
        tool.setHolidayCharge("No");

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Populate rentalAgreement with expected values

        when(toolRepository.getById("JAKR")).thenReturn(tool);
        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenReturn(rentalAgreement);

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("JAKR", result.getToolCode());
        assertEquals(4, result.getRentalDays());
        assertEquals(50, result.getDiscountPercent());
        assertEquals(LocalDate.of(2020, 7, 6).format(formatter), result.getDueDate());
        assertEquals(2.99f, result.getPreDiscountCharge());
        assertEquals(1.5f, result.getDiscountAmount());
        assertEquals(1.49f, result.getFinalCharge());
        verify(rentalAgreementRepository, times(1)).save(any(RentalAgreement.class));
    }

    @Test
    public void testCheckoutTool_InvalidRentalDays() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("T001");
        checkoutDto.setRentalDays(0);
        checkoutDto.setDiscountPercent(10);
        checkoutDto.setCheckoutDate("09/01/2024");

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("Error", result.getProcessResult().getStatus());
        assertEquals("checkoutDto days should be 1 day or more", result.getProcessResult().getMessage());
    }

    @Test
    public void testCheckoutTool_ToolNotFound() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("T999");
        checkoutDto.setRentalDays(5);
        checkoutDto.setDiscountPercent(10);
        checkoutDto.setCheckoutDate("09/01/2024");

        when(toolRepository.findById("T999")).thenReturn(Optional.empty());

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCheckoutToolForChainSawTest3() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("CHNS");
        checkoutDto.setRentalDays(5);
        checkoutDto.setDiscountPercent(25);
        checkoutDto.setCheckoutDate("07/02/2015");

        Tool tool = new Tool();
        tool.setToolType("Chainsaw");
        tool.setBrand("Stihl");
        tool.setDailyCharge(1.49f);
        tool.setWeekdayCharge("Yes");
        tool.setWeekendCharge("No");
        tool.setHolidayCharge("Yes");

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Populate rentalAgreement with expected values

        when(toolRepository.getById("CHNS")).thenReturn(tool);
        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenReturn(rentalAgreement);

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("CHNS", result.getToolCode());
        assertEquals(5, result.getRentalDays());
        assertEquals(25, result.getDiscountPercent());
        assertEquals(LocalDate.of(2015, 7, 7).format(formatter), result.getDueDate());
        assertEquals(4.47f, result.getPreDiscountCharge());
        assertEquals(1.12f, result.getDiscountAmount());
        assertEquals(3.35f, result.getFinalCharge());
        verify(rentalAgreementRepository, times(1)).save(any(RentalAgreement.class));
    }

    @Test
    public void testCheckoutToolForLadderTest2() {
        // Arrange
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setToolCode("LADW");
        checkoutDto.setRentalDays(3);
        checkoutDto.setDiscountPercent(10);
        checkoutDto.setCheckoutDate("07/02/2020");

        Tool tool = new Tool();
        tool.setToolType("Ladder");
        tool.setBrand("Werner");
        tool.setDailyCharge(1.99f);
        tool.setWeekdayCharge("Yes");
        tool.setWeekendCharge("Yes");
        tool.setHolidayCharge("No");

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Populate rentalAgreement with expected values

        when(toolRepository.getById("LADW")).thenReturn(tool);
        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenReturn(rentalAgreement);

        // Act
        RentalAgreementDto result = checkoutService.checkoutTool(checkoutDto);

        // Assert
        assertNotNull(result);
        assertEquals("LADW", result.getToolCode());
        assertEquals(3, result.getRentalDays());
        assertEquals(10, result.getDiscountPercent());
        assertEquals(LocalDate.of(2020, 7, 5).format(formatter), result.getDueDate());
        assertEquals(3.98f, result.getPreDiscountCharge());
        assertEquals(0.4f, result.getDiscountAmount());
        assertEquals(3.58f, result.getFinalCharge());
        verify(rentalAgreementRepository, times(1)).save(any(RentalAgreement.class));
    }
}