package com.cipher.csvreadingapi.service.serviceimpl;

import com.cipher.csvreadingapi.helper.CSVHelper;
import com.cipher.csvreadingapi.model.CSVContent;
import com.cipher.csvreadingapi.repository.CSVContentRepository;
import com.cipher.csvreadingapi.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {

    @Autowired
    CSVContentRepository repo;
    @Override
    public void save(MultipartFile file) {
        try{
            List<CSVContent> csvContents = CSVHelper.csvToContents(file.getInputStream());
            repo.saveAll(csvContents);
        }catch(IOException e){
            throw new RuntimeException("Failed to store csv data : "+ e.getMessage());
        }

    }

    @Override
    public List<CSVContent> getAllCsv() {
        return repo.findAll();
    }

    @Override
    public ByteArrayInputStream load() {
        List<CSVContent> csvContents = getAllCsv();
        ByteArrayInputStream in = CSVHelper.contentsToCSV(csvContents);
        return in;
    }
}
