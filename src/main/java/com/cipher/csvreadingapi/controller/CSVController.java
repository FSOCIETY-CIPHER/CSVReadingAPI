package com.cipher.csvreadingapi.controller;

import com.cipher.csvreadingapi.helper.CSVHelper;
import com.cipher.csvreadingapi.message.ResponseMessage;
import com.cipher.csvreadingapi.model.CSVContent;
import com.cipher.csvreadingapi.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Controller

@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    CSVService service;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file){
        String message =""
;
    if(CSVHelper.hasCSVFormat(file)){
    try{
        service.save(file);

        message =  file.getOriginalFilename() + "Successfully Uploaded  ";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }catch(Exception e){
        message = "Could not upload the file: "+ file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

    }
    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CSVContent>> getAllCsv(){
        try{
            List<CSVContent> csvContents = service.getAllCsv();

            if(csvContents.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(csvContents, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity <Resource> getFile(){
        String filename= "contents.csv";
        InputStreamResource file = new InputStreamResource(service.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);

    }

}
