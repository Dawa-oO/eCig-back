package com.floyd.eCigManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.floyd.eCigManagement.dto.AromeDto;
import com.floyd.eCigManagement.model.Arome;
import com.floyd.eCigManagement.repositories.AromeRepository;
import com.floyd.eCigManagement.repositories.PreparationRepository;
import com.floyd.eCigManagement.translator.AromeTranslator;
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
@RequestMapping("/arome")
public class AromeController {

    @Autowired
    private AromeRepository aromeRepository;
    @Autowired
    private PreparationRepository preparationRepository;

    @Autowired
    private Environment env;

    private AromeTranslator translator = new AromeTranslator();

    @ApiOperation(value = "Create an arome")
    @PostMapping
    public @ResponseBody AromeDto addNewArome(@RequestParam("arome") String model, @RequestParam(value = "file", required = false) MultipartFile file) {
        // Transform JSON in String to GameDto
        ObjectMapper mapper = new ObjectMapper();
        AromeDto aromeDto = null;

        try {
            aromeDto = mapper.readValue(model, AromeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        Arome arome = aromeRepository.save(translator.translateAromeDtoToArome(aromeDto));

        // Save the picture to external doc, using id for name
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File newFile = new File(env.getProperty("picture.path") + "images/arome/" + arome.getId() + ".jpg");

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
        }

        return translator.translateAromeToAromeDto(arome);
    }

    @ApiOperation(value = "Update the quantity of an arome")
    @PutMapping(value = "/{id}/quantity")
    public @ResponseBody AromeDto updateArome(@RequestBody int newQuantity, @PathVariable int id) {
        System.out.println("Updating arome [quantity: " + newQuantity + ", id: " + id + "]");
        return aromeRepository.findById(id)
                .map(arome -> {
                    arome.setQuantity(newQuantity);
                    return translator.translateAromeToAromeDto(aromeRepository.save(arome));
                })
                .orElse(null);
    }

    @ApiOperation(value = "Retrieve all aromes")
    @GetMapping
    public @ResponseBody
    List<AromeDto> getAllAromes() {
        return StreamSupport.stream(aromeRepository.findAll().spliterator(), false).map(translator::translateAromeToAromeDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve information about a specific arome")
    @GetMapping(value = "/{id}")
    public @ResponseBody AromeDto getAromeById(@PathVariable int id){
        return aromeRepository.findById(id).map(translator::translateAromeToAromeDto).orElse(null);
    }

    @ApiOperation(value="Delete an arome by its ID")
    @DeleteMapping(value = "/{id}")
    public @ResponseBody boolean deleteAromeById(@PathVariable int id) {
        Arome aromeToDelete = aromeRepository.findById(id).orElse(null);

        if (aromeToDelete == null) {
            return false;
        }

        // -- delete object in DB
        preparationRepository.deleteByArome(aromeToDelete);
        aromeRepository.deleteById(id);

        // -- delete file
        File file = new File(env.getProperty("picture.path") + "images/arome/" + id + ".jpg");
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
