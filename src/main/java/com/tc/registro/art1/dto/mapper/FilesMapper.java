package com.tc.registro.art1.dto.mapper;

import com.tc.registro.art1.dto.FilesDto;
import com.tc.registro.art1.model.Files;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FilesMapper {
    public Files createEntityFromDto(FilesDto filesDto){
        return new Files(null, filesDto.getNumberFile(), filesDto.getTitle(), filesDto.getExpirationDate(), null, filesDto.getState());
    }
    public FilesDto entityToDto(Files files){
        return new FilesDto(files.getId(), files.getNumberFile(), files.getTitle(), files.getExpirationDate(), files.getCreationDate(), files.getState());
    }
    public List<Object> listEntityToListDto(List<Files> files){
        return files.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Files updateEntityByDto(Files file, FilesDto filesDto) {
        Optional.of(file).stream().forEach((f)->{
            if(filesDto.getNumberFile() != null) f.setNumberFile(filesDto.getNumberFile());
            if(filesDto.getTitle() != null) f.setTitle(filesDto.getTitle());
            if(filesDto.getExpirationDate()!= null) f.setExpirationDate(filesDto.getExpirationDate());
            if(filesDto.getState()!= null) f.setState(filesDto.getState());
        });
        return file;
    }
}
