package com.tools.point.of.sale.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProcessResult implements Serializable {
    private String status;
    private String message;
}
