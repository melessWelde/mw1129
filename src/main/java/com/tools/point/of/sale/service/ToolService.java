package com.tools.point.of.sale.service;

import com.tools.point.of.sale.dto.ToolDto;

import java.util.List;

public interface ToolService {
    public String addTool(ToolDto tool);

    String addTools(List<ToolDto> tool);
}
