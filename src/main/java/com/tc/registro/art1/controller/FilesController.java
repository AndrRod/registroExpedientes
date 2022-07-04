package com.tc.registro.art1.controller;

import com.tc.registro.art1.dto.FilesDto;
import com.tc.registro.art1.exception.MessagePag;
import com.tc.registro.art1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    public MessagePag listFilesPagination(@RequestParam int page){
        return fileService.paginationFiles(page);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public FilesDto findById(@PathVariable Long id){
        return fileService.findFileDtoById(id);
    }

}
