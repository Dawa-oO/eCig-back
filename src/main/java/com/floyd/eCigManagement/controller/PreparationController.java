package com.floyd.eCigManagement.controller;

import com.floyd.eCigManagement.dto.PreparationDto;
import com.floyd.eCigManagement.repositories.PreparationRepository;
import com.floyd.eCigManagement.translator.PreparationTranslator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody
    String addNewPreparation(@RequestBody PreparationDto preparationDto) {
        preparationRepository.save(translator.translatePreparationDtoToPreparation(preparationDto));
        return "Preparation saved";
    }

    @ApiOperation(value = "Update the quantity of an preparation")
    @PutMapping(value = "/{id}/quantity")
    public @ResponseBody String updatePreparation(@RequestBody int newQuantity, @PathVariable int id) {
        return preparationRepository.findById(id)
                .map(preparation -> {
                    preparation.setQuantity(newQuantity);
                    return preparationRepository.save(preparation);
                })
                .orElse(null) != null ? "Preparation updated" : "Preparation not found";
    }

    @ApiOperation(value = "Retrieve information about all preparations")
    @GetMapping
    public @ResponseBody
    List<PreparationDto> getAllPreparations() {
        return StreamSupport.stream(preparationRepository.findAll().spliterator(), false).map(translator::translatePreparationToPreparationDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve information about a specific preparatione")
    @GetMapping(value = "/{id}")
    public @ResponseBody PreparationDto getPreparationById(@PathVariable int id) {
        return preparationRepository.findById(id).map(translator::translatePreparationToPreparationDto).orElse(null);
    }
}
