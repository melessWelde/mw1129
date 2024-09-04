package com.tools.point.of.sale.service.impl;

import com.tools.point.of.sale.dto.ToolDto;
import com.tools.point.of.sale.entity.Tool;
import com.tools.point.of.sale.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ToolSrviceImplTest {
    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolSrviceImpl toolService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTool() {
        // Arrange
        ToolDto toolDto = new ToolDto();
        toolDto.setToolCode("JAKD");
        toolDto.setToolType("Jackhammer");
        toolDto.setBrand("DeWalt");
        toolDto.setDailyCharge(2.99f);
        toolDto.setWeekdayCharge("Yes");
        toolDto.setHolidayCharge("No");
        toolDto.setWeekendCharge("No");

        Tool tool = Tool.builder()
                .toolCode(toolDto.getToolCode())
                .toolType(toolDto.getToolType())
                .brand(toolDto.getBrand())
                .dailyCharge(toolDto.getDailyCharge())
                .weekdayCharge(toolDto.getWeekdayCharge())
                .holidayCharge(toolDto.getHolidayCharge())
                .weekendCharge(toolDto.getWeekendCharge())
                .build();

        when(toolRepository.save(any(Tool.class))).thenReturn(tool);

        // Act
        String result = toolService.addTool(toolDto);

        // Assert
        assertEquals("Saved", result);
        verify(toolRepository, times(1)).save(any(Tool.class));
    }

    @Test
    public void testAddTools() {
        // Arrange
        ToolDto toolDto1 = new ToolDto();
        toolDto1.setToolCode("LADW");
        toolDto1.setToolType("Ladder");
        toolDto1.setBrand("Werner");
        toolDto1.setDailyCharge(1.99f);
        toolDto1.setWeekdayCharge("Yes");
        toolDto1.setHolidayCharge("Yes");
        toolDto1.setWeekendCharge("No");

        ToolDto toolDto2 = new ToolDto();
        toolDto2.setToolCode("CHNS");
        toolDto2.setToolType("Chainsaw");
        toolDto2.setBrand("Stihl");
        toolDto2.setDailyCharge(1.49f);
        toolDto2.setWeekdayCharge("Yes");
        toolDto2.setHolidayCharge("No");
        toolDto2.setWeekendCharge("Yes");

        List<ToolDto> toolDtos = Arrays.asList(toolDto1, toolDto2);

        Tool tool1 = Tool.builder()
                .toolCode(toolDto1.getToolCode())
                .toolType(toolDto1.getToolType())
                .brand(toolDto1.getBrand())
                .dailyCharge(toolDto1.getDailyCharge())
                .weekdayCharge(toolDto1.getWeekdayCharge())
                .holidayCharge(toolDto1.getHolidayCharge())
                .weekendCharge(toolDto1.getWeekendCharge())
                .build();

        Tool tool2 = Tool.builder()
                .toolCode(toolDto2.getToolCode())
                .toolType(toolDto2.getToolType())
                .brand(toolDto2.getBrand())
                .dailyCharge(toolDto2.getDailyCharge())
                .weekdayCharge(toolDto2.getWeekdayCharge())
                .holidayCharge(toolDto2.getHolidayCharge())
                .weekendCharge(toolDto2.getWeekendCharge())
                .build();

        when(toolRepository.saveAllAndFlush(anyIterable())).thenReturn(Arrays.asList(tool1, tool2));

        // Act
        String result = toolService.addTools(toolDtos);

        // Assert
        assertEquals("Saved", result);
        verify(toolRepository, times(1)).saveAllAndFlush(anyIterable());
    }
}