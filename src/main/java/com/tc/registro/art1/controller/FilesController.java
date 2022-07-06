package com.tc.registro.art1.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tc.registro.art1.dto.FilesDto;
import com.tc.registro.art1.exception.MessagePag;
import com.tc.registro.art1.service.FileService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
@RestController
@RequestMapping("/file")
public class FilesController {
    @Autowired
    private FileService fileService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public FilesDto createEntity(@RequestBody FilesDto filesDto){
        return fileService.createFile(filesDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public FilesDto updateFile(@PathVariable Long id,@RequestBody FilesDto filesDto){
        return fileService.updateFileById(id , filesDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id){
        fileService.deleteFileById(id);
        return "the file was deleted";
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public MessagePag listFilesPagination(@RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) Integer descr,
                                          @RequestBody(required = false) Field field,
                                          String pathPage){
        if(page !=null) {return fileService.paginationFiles(page, "/file?page=");}
        return fileService.paginationFilesFindByDescription(descr, field.getDescription(), "/file?descr=");
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public FilesDto findById(@PathVariable Long id){
        return fileService.findFileDtoById(id);
    }

}
@Data
class Field {
    private String description;
}
