package com.floyd.eCigManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.floyd.eCigManagement.dto.BoosterDto;
import com.floyd.eCigManagement.model.Booster;
import com.floyd.eCigManagement.repositories.BoosterRepository;
import com.floyd.eCigManagement.translator.BoosterTranslator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/booster")
public class BoosterController {

    @Autowired
    private BoosterRepository boosterRepository;

    @Autowired
    private Environment env;
    
    private BoosterTranslator translator = new BoosterTranslator();

    @ApiOperation(value = "Create a booster")
    @PostMapping
    public @ResponseBody BoosterDto addNewBooster(@RequestParam("booster") String model, @RequestParam(value = "file", required = false) MultipartFile file) {
        // Transform JSON in String to GameDto
        ObjectMapper mapper = new ObjectMapper();
        BoosterDto boosterDto = null;

        try {
            boosterDto = mapper.readValue(model, BoosterDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        Booster booster = boosterRepository.save(translator.translateBoosterDtoToBooster(boosterDto));

        // Save the picture to external doc, using id for name
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File newFile = new File(env.getProperty("picture.path") + "images/booster/" + booster.getId() + ".jpg");

        try {
            inputStream = file.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return translator.translateBoosterToBoosterDto(booster);
    }

    @ApiOperation(value = "Update the quantity of a booster")
    @PutMapping(value = "/{id}/quantity")
    public @ResponseBody BoosterDto updateBooster(@RequestBody int newQuantity, @PathVariable int id) {
        System.out.println("Updating booster [quantity: " + newQuantity + ", id: " + id + "]");
        return boosterRepository.findById(id)
                .map(booster -> {
                    booster.setQuantity(newQuantity);
                    return translator.translateBoosterToBoosterDto(boosterRepository.save(booster));
                })
                .orElse(null);
    }

    @ApiOperation(value = "Retrieve all boosters")
    @GetMapping
    public @ResponseBody
    List<BoosterDto> getAllBoosters() {
        return StreamSupport.stream(boosterRepository.findAll().spliterator(), false).map(translator::translateBoosterToBoosterDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve information about a specific booster")
    @GetMapping(value = "/{id}")
    public @ResponseBody BoosterDto getBoosterById(@PathVariable int id){
        return boosterRepository.findById(id).map(translator::translateBoosterToBoosterDto).orElse(null);
    }

    @ApiOperation(value="Delete a booster by its ID")
    @DeleteMapping(value = "/{id}")
    public @ResponseBody boolean deleteBoosterById(@PathVariable int id) {
        // -- delete object in DB
        boosterRepository.deleteById(id);

        // -- delete file
        File file = new File(env.getProperty("picture.path") + "images/booster/" + id + ".jpg");
        try {
            if (Files.deleteIfExists(file.toPath())) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
