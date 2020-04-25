package com.floyd.eCigManagement.dto;

import lombok.Data;

@Data
public class AromeDto {
    private Integer id;
    private Integer quantity;
    private Integer capacity;
    private String taste;
    private String brand;
    private Double note;
}
