package com.floyd.eCigManagement.translator;

import com.floyd.eCigManagement.dto.PreparationDto;
import com.floyd.eCigManagement.model.Preparation;

public class PreparationTranslator {

    public Preparation translatePreparationDtoToPreparation(PreparationDto dto) {
        Preparation prep = new Preparation();
        prep.setArome(dto.getArome());
        prep.setCapacity(dto.getCapacity());
        prep.setId(dto.getId());
        prep.setQuantity(dto.getQuantity());
        return prep;
    }

    public PreparationDto translatePreparationToPreparationDto(Preparation prep) {
        PreparationDto dto = new PreparationDto();
        dto.setId(prep.getId());
        dto.setArome(prep.getArome());
        dto.setCapacity(prep.getCapacity());
        dto.setQuantity(prep.getQuantity());
        return dto;
    }
}
