package com.tools.point.of.sale.util;

import com.tools.point.of.sale.dto.RentalAgreementDto;
import com.tools.point.of.sale.dto.ToolDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A static database class that provides a collection of tool data and rental agreements for a tool rental system.
 *
 * This class initializes a static list of tools with predefined values and provides methods for retrieving
 * tool details, adding new tools, and populating rental agreements. It serves as a temporary data store,
 * simulating a database for tools and rental agreements in a simplified manner.
 *
 * Attributes:
 * - tools: A static list that stores tool information, represented by ToolDto objects.
 * - rentalAgreements: A static list that stores rental agreement information, represented by RentalAgreementDto objects.
 *
 * @author melessweldemichael
 */

public class ToolsStaticDb {
    public static final List<ToolDto> tools = new ArrayList<>();
    public static final List<RentalAgreementDto> rentalAgreements = new ArrayList<>();

    public ToolsStaticDb() {
        tools.clear();
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

        ToolDto toolDto3 = new ToolDto();
        toolDto3.setToolCode("JAKR");
        toolDto3.setToolType("Jackhammer");
        toolDto3.setBrand("Ridgid");
        toolDto3.setDailyCharge(2.99f);
        toolDto3.setWeekdayCharge("Yes");
        toolDto3.setHolidayCharge("No");
        toolDto3.setWeekendCharge("No");

        List<ToolDto> toolDtos = Arrays.asList(toolDto, toolDto1, toolDto2, toolDto3);
        tools.addAll(toolDtos);
    }

    public ToolDto getToolDetails(String toolCode) {
        return tools.stream().filter(tool -> tool.getToolCode().equals(toolCode)).findFirst().get();
    }

    public void addTool(ToolDto toolDto) {
        tools.add(toolDto);
    }

    public void populateRentalAggreement(RentalAgreementDto rentalAgreementDto) {
        rentalAgreements.add(rentalAgreementDto);
    }
}
