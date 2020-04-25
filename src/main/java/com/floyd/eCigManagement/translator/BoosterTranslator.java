package com.floyd.eCigManagement.translator;

import com.floyd.eCigManagement.dto.BoosterDto;
import com.floyd.eCigManagement.model.Booster;

public class BoosterTranslator {

    public Booster translateBoosterDtoToBooster(BoosterDto dto) {
        Booster booster = new Booster();
        booster.setCapacity(dto.getCapacity());
        booster.setId(dto.getId());
        booster.setPgvg(dto.getPgvg());
        booster.setQuantity(dto.getQuantity());
        return booster;
    }

    public BoosterDto translateBoosterToBoosterDto(Booster booster) {
        BoosterDto dto = new BoosterDto();
        dto.setCapacity(booster.getCapacity());
        dto.setId(booster.getId());
        dto.setPgvg(booster.getPgvg());
        dto.setQuantity(booster.getQuantity());
        return dto;
    }
}
