package com.tools.point.of.sale.repository;

import com.tools.point.of.sale.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, String> {
}
