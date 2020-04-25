package com.floyd.eCigManagement.dto;

import com.floyd.eCigManagement.model.Arome;
import lombok.Data;

@Data
public class PreparationDto {

    private Integer id;
    private Arome arome;
    private Integer quantity;
    private Integer capacity;

}
