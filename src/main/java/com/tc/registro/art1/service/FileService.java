package com.tc.registro.art1.service;

import com.tc.registro.art1.dto.FilesDto;
import com.tc.registro.art1.exception.MessagePag;
import com.tc.registro.art1.model.Files;

import javax.servlet.http.HttpServletRequest;

public interface FileService {
    FilesDto createFile(FilesDto filesDto);
    FilesDto findFileDtoById(Long id);
    Files findFileById(Long id);
    void deleteFileById(Long id);
    MessagePag paginationFiles(Integer page, String pathPage);
    MessagePag paginationFilesFindByDescription(Integer page, String description, String pathPage);
    FilesDto updateFileById(Long id, FilesDto filesDto);

}
