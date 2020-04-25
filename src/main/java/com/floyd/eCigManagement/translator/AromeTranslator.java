package com.floyd.eCigManagement.translator;

import com.floyd.eCigManagement.dto.AromeDto;
import com.floyd.eCigManagement.model.Arome;

public class AromeTranslator {

    public Arome translateAromeDtoToArome(AromeDto dto) {
        Arome arome = new Arome();
        arome.setId(dto.getId());
        arome.setQuantity(dto.getQuantity());
        arome.setCapacity(dto.getCapacity());
        arome.setTaste(dto.getTaste());
        arome.setBrand(dto.getBrand());
        arome.setNote(dto.getNote());
        return arome;
    }

    public AromeDto translateAromeToAromeDto(Arome arome) {
        AromeDto dto = new AromeDto();
        dto.setId(arome.getId());
        dto.setQuantity(arome.getQuantity());
        dto.setCapacity(arome.getCapacity());
        dto.setTaste(arome.getTaste());
        dto.setBrand(arome.getBrand());
        dto.setNote(arome.getNote());
        return dto;
    }
}
