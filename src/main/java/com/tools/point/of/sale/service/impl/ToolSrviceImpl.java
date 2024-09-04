package com.tools.point.of.sale.service.impl;

import com.tools.point.of.sale.dto.ToolDto;
import com.tools.point.of.sale.entity.Tool;
import com.tools.point.of.sale.repository.ToolRepository;
import com.tools.point.of.sale.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * This service handles operations related to tools, such as adding individual tools
 * and adding multiple tools to the repository.
 *
 * @author melessweldemichael
 */

@Component
public class ToolSrviceImpl implements ToolService {

    @Autowired
    private ToolRepository toolRepository;

    /**
     * Adds a single tool to the repository.
     * Converts the provided ToolDto to a Tool entity and saves it.
     *
     * @param tool Data Transfer Object containing the details of the tool to be added.
     * @return A confirmation message indicating that the tool has been saved.
     */
    @Override
    public String addTool(ToolDto tool) {
        toolRepository.save(createToolEntity(tool));
        return "Saved";
    }
    /**
     * Adds multiple tools to the repository.
     * Converts the provided list of ToolDto objects to a list of Tool entities and saves them.
     * @param tools List of Data Transfer Objects containing the details of the tools to be added.
     * @return A confirmation message indicating that the tools have been saved.
     */
    @Override
    public String addTools(List<ToolDto> tools) {
        toolRepository.saveAllAndFlush(createToolsEntity(tools));
        return "Saved";
    }
    /**
     * Converts a list of ToolDto objects to a list of Tool entities.
     * This method maps each ToolDto to a Tool entity by copying the relevant properties.
     * @param toolDtos List of Data Transfer Objects containing the tool details.
     * @return A list of Tool entities created from the provided ToolDto objects.
     */
    private Iterable<Tool> createToolsEntity(List<ToolDto> toolDtos) {
        List<Tool> tools = new ArrayList<>();
        for (ToolDto toolDto : toolDtos) {
            Tool toolEntity = new Tool();
            toolEntity.setToolCode(toolDto.getToolCode());
            toolEntity.setToolType(toolDto.getToolType());
            toolEntity.setBrand(toolDto.getBrand());
            toolEntity.setDailyCharge(toolDto.getDailyCharge());
            toolEntity.setWeekdayCharge(toolDto.getWeekdayCharge());
            toolEntity.setHolidayCharge(toolDto.getHolidayCharge());
            toolEntity.setWeekendCharge(toolDto.getWeekendCharge());
            tools.add(toolEntity);
        }
        return tools;
    }

    /**
     * Converts a single ToolDto object to a Tool entity.
     * This method maps the properties of the ToolDto to a new Tool entity using the builder pattern.
     * @param tool Data Transfer Object containing the tool details.
     * @return A Tool entity created from the provided ToolDto.
     */
    private Tool createToolEntity(ToolDto tool) {
        return Tool.builder().toolCode(tool.getToolCode())
                .toolType(tool.getToolType())
                .brand(tool.getBrand())
                .holidayCharge(tool.getHolidayCharge())
                .dailyCharge(tool.getDailyCharge())
                .weekdayCharge(tool.getWeekdayCharge())
                .weekendCharge(tool.getWeekendCharge())
                .build();
    }
}
