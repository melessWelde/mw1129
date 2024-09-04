package com.tools.point.of.sale.util;

import com.tools.point.of.sale.dto.CheckoutDto;
import com.tools.point.of.sale.dto.ProcessResult;
import com.tools.point.of.sale.dto.RentalAgreementDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

/**
 * Utility class for handling various operations related to tool rentals.
 * this is used by both the API and Main class
 *
 * @author melessweldemichael
 */
@Slf4j
public class ToolsRentalUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Calculates the number of chargeable days for a Jackhammer tool.
     * Excludes weekends and holidays such as Labor Day and Independence Day.
     *
     * @param startDate The start date of the rental period.
     * @param rentalDays The number of days the tool is rented.
     * @return The number of chargeable days for the Jackhammer.
     */
    public static int getChargeableDaysForJackHammer(String startDate, int rentalDays) {

        LocalDate billingStartDate =  dateFormatter(startDate).plusDays(1);
        LocalDate endDate = dateFormatter(startDate).plusDays(rentalDays);
        LocalDate laborDay = getLaborDay(dateFormatter(startDate).getYear());
        LocalDate independenceDay = getObservedIndependenceDay(dateFormatter(startDate).getYear());

        int chargeableDays = 0;

        for (LocalDate date = billingStartDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (isBusinessDayForJackHammer(date, laborDay, independenceDay)) {
                chargeableDays = chargeableDays + 1;
            }
        }

        return chargeableDays;
    }

    /**
     * Calculates the number of chargeable days for a Ladder tool.
     * Excludes specific holidays such as Labor Day and Independence Day.
     *
     * @param startDate The start date of the rental period.
     * @param rentalDays The number of days the tool is rented.
     * @return The number of chargeable days for the Ladder.
     */
    public static int getChargeableDaysForLadder(String startDate, int rentalDays) {
        LocalDate billingStartDate = dateFormatter(startDate).plusDays(1);
        LocalDate endDate = dateFormatter(startDate).plusDays(rentalDays);
        LocalDate laborDay = getLaborDay(dateFormatter(startDate).getYear());
        LocalDate independenceDay = getObservedIndependenceDay(dateFormatter(startDate).getYear());

        int chargeableDays = 0;

        for (LocalDate date = billingStartDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (isBusinessDayForLadder(date, laborDay, independenceDay)) {
                chargeableDays = chargeableDays + 1;
            }
        }

        return chargeableDays;
    }

    /**
     * Calculates the number of chargeable days for a Chainsaw tool.
     * Excludes weekends but includes all holidays.
     *
     * @param startDate The start date of the rental period.
     * @param rentalDays The number of days the tool is rented.
     * @return The number of chargeable days for the Chainsaw.
     */
    public static int getChargeableDaysForChainSaw(String startDate, int rentalDays) {
        LocalDate billingStartDate = dateFormatter(startDate).plusDays(1);
        LocalDate endDate = dateFormatter(startDate).plusDays(rentalDays);

        int chargeableDays = 0;

        for (LocalDate date = billingStartDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (isBusinessDayForChainSaw(date)) {
                chargeableDays = chargeableDays + 1;
            }
        }

        return chargeableDays;
    }

    /**
     * Checks if a given date is a business day for a Jackhammer.
     * Excludes weekends and holidays such as Labor Day and Independence Day.
     *
     * @param date The date to check.
     * @param laborDay The Labor Day date for the year.
     * @param independenceDay The observed Independence Day date for the year.
     * @return True if the date is a business day, otherwise false.
     */
    public static boolean isBusinessDayForJackHammer(LocalDate date, LocalDate laborDay, LocalDate independenceDay) {
        return !(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.equals(laborDay) ||
                date.equals(independenceDay));
    }

    /**
     * Checks if a given date is a business day for a Ladder.
     * Excludes specific holidays such as Labor Day and Independence Day.
     *
     * @param date The date to check.
     * @param laborDay The Labor Day date for the year.
     * @param independenceDay The observed Independence Day date for the year.
     * @return True if the date is a business day, otherwise false.
     */
    private static boolean isBusinessDayForLadder(LocalDate date, LocalDate laborDay, LocalDate independenceDay) {
        return !(date.equals(laborDay) ||
                date.equals(independenceDay));
    }

    /**
     * Checks if a given date is a business day for a Chainsaw.
     * Excludes weekends but includes all holidays.
     *
     * @param date The date to check.
     * @return True if the date is a business day, otherwise false.
     */
    private static boolean isBusinessDayForChainSaw(LocalDate date) {
        return !(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY);
    }

    /**
     * Calculates the observed date for Independence Day.
     * If July 4th falls on a weekend, the observed date is adjusted to the nearest weekday.
     *
     * @param year The year to calculate for.
     * @return The observed Independence Day date.
     */
    private static LocalDate getObservedIndependenceDay(int year) {
        LocalDate date = LocalDate.of(year, Month.JULY, 4);
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return date.minusDays(1);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return date.plusDays(1);
        } else {
            return date;
        }
    }

    /**
     * Calculates the date for Labor Day in September.
     * Labor Day is observed on the first Monday of September.
     *
     * @param year The year to calculate for.
     * @return The Labor Day date.
     */
    private static LocalDate getLaborDay(int year) {
        LocalDate firstDayOfSeptember = LocalDate.of(year, Month.SEPTEMBER, 1);
        return firstDayOfSeptember.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    /**
     * Validates the rental days
     *
     * @param rentalDays The number of rental days to validate.
     * @return True if the rental days are valid, otherwise false.
     */
    public static boolean validateRentalDays(Integer rentalDays) {
        return rentalDays != null && rentalDays > 0;
    }

    /**
     * Validates the discount percentage.
     * @param percentage The discount percentage to validate.
     * @return True if the discount percentage is valid, otherwise false.
     */
    public static boolean validDiscountPercent(Integer percentage) {
        return percentage != null && percentage >= 0
                && percentage <= 100;
    }

    /**
     * Validates the checkout data.
     *
     * @param checkoutDto Data Transfer Object containing checkout information.
     * @return A RentalAgreementDto object with validation results.
     */
    public static RentalAgreementDto validateCheckout(CheckoutDto checkoutDto) {
        if (!validDiscountPercent(checkoutDto.getDiscountPercent())) {
            return RentalAgreementDto.builder().processResult(ProcessResult.builder().status("Error").message("Percentage should be between 0 to 100").build()).build();
        } else if (!validateRentalDays(checkoutDto.getRentalDays())) {
            return RentalAgreementDto.builder().processResult(ProcessResult.builder().status("Error").message("checkoutDto days should be 1 day or more").build()).build();
        } else return RentalAgreementDto.builder().build();
    }

    /**
     * Formats a date to a string in the format MM/dd/yyyy.
     *
     * @param checkoutDate The date to format.
     * @return The formatted date string.
     */
    public static String extractFormatedDate(String checkoutDate) {
        LocalDate localDate = LocalDate.now();
        try {
            localDate = dateFormatter(checkoutDate);
        } catch (DateTimeParseException e) {
            log.error("date parsing exception {} " , e.getMessage());
            e.printStackTrace();
        }
        return localDate.format(formatter);
    }

    /**
     * Calculates the final charge after applying the discount.
     *
     * @param preDiscountCharge The charge before discount.
     * @param discountAmount The discount amount to subtract.
     * @return The final charge after discount.
     */
    public static float calculateFinalCharge(float preDiscountCharge, float discountAmount) {
        return preDiscountCharge - discountAmount;
    }

    /**
     * Calculates the discount amount based on the percentage and pre-discount charge.
     *
     * @param discountPercent The discount percentage.
     * @param preDiscountCharge The charge before applying the discount.
     * @return The calculated discount amount.
     */
    public static float calculateDiscountAmount(Integer discountPercent, float preDiscountCharge) {
        double discountAmount = (discountPercent * preDiscountCharge) / 100;
        BigDecimal bigDecimalValue = new BigDecimal(discountAmount);
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        BigDecimal bigDecimal = bigDecimalValue.setScale(scale, roundingMode);
        return bigDecimal.floatValue();
    }

    /**
     * Calculates the pre-discount charge based on the daily rental charge and the number of chargeable days.
     *
     * @param dailyRentalCharge The daily rental charge.
     * @param chargeDays The number of chargeable days.
     * @return The pre-discount charge.
     */
    public static float calculatePreDiscountCharge(float dailyRentalCharge, int chargeDays) {
        double value = dailyRentalCharge * chargeDays;
        BigDecimal bigDecimalValue = new BigDecimal(value);
        int scale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        BigDecimal bigDecimal = bigDecimalValue.setScale(scale, roundingMode);
        return bigDecimal.floatValue();
    }

    /**
     * Calculates the number of chargeable days based on the tool type.
     *
     * @param toolType The type of tool (e.g., Jackhammer, Ladder, Chainsaw).
     * @param checkoutDate The start date of the rental period.
     * @param rentalDays The number of rental days.
     * @return The number of chargeable days.
     */
    public static int getNumberOfChargeDays(String toolType, String checkoutDate, Integer rentalDays) {
        return switch (toolType) {
            case "Jackhammer" -> getChargeableDaysForJackHammer(checkoutDate, rentalDays);
            case "Ladder" -> getChargeableDaysForLadder(checkoutDate, rentalDays);
            case "Chainsaw" -> getChargeableDaysForChainSaw(checkoutDate, rentalDays);
            default -> 0;
        };
    }

    /**
     * Sets the due date for the rental by adding the rental days to the checkout date in the format MM/dd/yyyy.
     *
     * @param checkoutDate The start date of the rental period.
     * @param rentalDays The number of rental days.
     * @return The due date formatted as a string.
     */
    public static String setDueDate(String checkoutDate, Integer rentalDays) {
        try {
            LocalDate dueDate = dateFormatter(checkoutDate).plusDays(rentalDays);
            return dueDate.format(formatter);
        } catch (DateTimeException ex) {
            log.error("parsing exception {} " , ex.getMessage());
        }
        return "checkoutDate";
    }

    public static final LocalDate dateFormatter(String checkoutDate) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
        };
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(checkoutDate, formatter);
            } catch (DateTimeParseException e) {
                // Ignore the exception and try the next format
            }
        }
        return LocalDate.now();
    }
}
