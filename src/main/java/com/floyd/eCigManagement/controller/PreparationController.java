package com.floyd.eCigManagement.controller;

import com.floyd.eCigManagement.dto.PreparationDto;
import com.floyd.eCigManagement.model.Arome;
import com.floyd.eCigManagement.model.Preparation;
import com.floyd.eCigManagement.repositories.PreparationRepository;
import com.floyd.eCigManagement.translator.PreparationTranslator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/preparation")
public class PreparationController {

    @Autowired
    PreparationRepository preparationRepository;

    private PreparationTranslator translator = new PreparationTranslator();

    @ApiOperation(value = "Create a preparation")
    @PostMapping
    public @ResponseBody PreparationDto addNewPreparation(@RequestBody PreparationDto preparationDto) {
        return translator.translatePreparationToPreparationDto(preparationRepository.save(translator.translatePreparationDtoToPreparation(preparationDto)));
    }

    @ApiOperation(value = "Update the quantity of an preparation")
    @PutMapping(value = "/{id}/quantity")
    public @ResponseBody PreparationDto updatePreparation(@RequestBody int newQuantity, @PathVariable int id) {
        return preparationRepository.findById(id)
                .map(preparation -> {
                    preparation.setQuantity(newQuantity);
                    return translator.translatePreparationToPreparationDto(preparationRepository.save(preparation));
                })
                .orElse(null);
    }

    @ApiOperation(value = "Retrieve information about all preparations")
    @GetMapping
    public @ResponseBody List<PreparationDto> getAllPreparations() {
        return StreamSupport.stream(preparationRepository.findAll().spliterator(), false).map(translator::translatePreparationToPreparationDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve information about a specific preparatione")
    @GetMapping(value = "/{id}")
    public @ResponseBody PreparationDto getPreparationById(@PathVariable int id) {
        return preparationRepository.findById(id).map(translator::translatePreparationToPreparationDto).orElse(null);
    }

    @ApiOperation(value="Delete a preparation by its ID")
    @DeleteMapping(value = "/{id}")
    public @ResponseBody boolean deletePreparationById(@PathVariable int id) {
        // -- delete object in DB
        preparationRepository.deleteById(id);
        return true;
    }
}
