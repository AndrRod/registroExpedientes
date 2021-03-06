package com.tc.registro.art1.service.serviceImpl;

import com.tc.registro.art1.dto.FilesDto;
import com.tc.registro.art1.dto.mapper.FilesMapper;
import com.tc.registro.art1.exception.MessagePag;
import com.tc.registro.art1.exception.NotFoundException;
import com.tc.registro.art1.exception.PaginationMessage;
import com.tc.registro.art1.model.Files;
import com.tc.registro.art1.repository.FileRepository;
import com.tc.registro.art1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class FilesServiceImpl implements FileService {
    private static final int SIZE_MAX = 10;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PaginationMessage paginationMessage;
    @Autowired
    private FilesMapper filesMapper;

    @Override
    public FilesDto createFile(FilesDto filesDto) {
        return filesMapper.entityToDto(fileRepository.save(filesMapper.createEntityFromDto(filesDto)));
    }

    @Override
    public FilesDto findFileDtoById(Long id) {
        return filesMapper.entityToDto(findFileById(id));
    }

    @Override
    public Files findFileById(Long id) {
        return fileRepository.findById(id).orElseThrow(()-> new NotFoundException("file not found"));
    }

    @Override
    public void deleteFileById(Long id) {
        fileRepository.delete(findFileById(id));
    }

    @Override
    public MessagePag paginationFilesFindByDescription(Integer page, String description, String pathPage) {
        Page<Files> files = fileRepository.findByDescriptionFile(description, PageRequest.of(page, SIZE_MAX));
        return paginationMessage.messageInfo(files, filesMapper.listEntityToListDto(files.getContent()), pathPage);
    }


    @Override
    public MessagePag paginationFiles(Integer page, String pathPage) {
        Page<Files> files = fileRepository.findAll(PageRequest.of(page, SIZE_MAX));
        return paginationMessage.messageInfo(files, filesMapper.listEntityToListDto(files.getContent()), pathPage);
    }

    @Override
    public FilesDto updateFileById(Long id, FilesDto filesDto) {
        Files file = findFileById(id);
        return filesMapper.entityToDto(fileRepository.save(filesMapper.updateEntityByDto(file, filesDto)));
    }
}
