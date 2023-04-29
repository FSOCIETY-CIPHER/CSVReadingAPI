package com.cipher.csvreadingapi.service;

import com.cipher.csvreadingapi.model.CSVContent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public interface CSVService {
    public void save(MultipartFile file);


    public List<CSVContent> getAllCsv();

    public ByteArrayInputStream load();

}
