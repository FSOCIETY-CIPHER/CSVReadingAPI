package com.cipher.csvreadingapi.helper;

import com.cipher.csvreadingapi.model.CSVContent;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {
            "Id",
            "Name",
            "Description",
            "Published"
    };

    public static boolean hasCSVFormat (MultipartFile file){
        if(TYPE.equals(file.getContentType())){
            return true;
        }
        return  false;
    }

    public static List<CSVContent> csvToContents(InputStream is){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(br,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
                 List<CSVContent> contents = new ArrayList<CSVContent>();

                 Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                 for(CSVRecord csvRecord :csvRecords){
                     CSVContent csv = new CSVContent(
                             Long.parseLong(csvRecord.get("Id")),
                             csvRecord.get("Name"),
                             csvRecord.get("Description"),
                             Boolean.parseBoolean(csvRecord.get("Published"))
                     );
                     contents.add(csv);
                 }
                 return contents;
        }catch(IOException e){
                 throw  new RuntimeException("Failed to parse CSV file : "+ e.getMessage());
        }
    }

    public static ByteArrayInputStream contentsToCSV(List<CSVContent> contents){
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try(ByteArrayOutputStream out =new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);){
        for(CSVContent csvContent : contents) {
            List<String> data = Arrays.asList(
                    String.valueOf(csvContent.getId()),
                    csvContent.getName(),
                    csvContent.getDescription(),
                    String.valueOf(csvContent.isPublished())
            );
            csvPrinter.printRecord(data);
        }
        csvPrinter.flush();
        return new ByteArrayInputStream(out.toByteArray());
        }catch (IOException e){
        throw new RuntimeException("Failed to import data to CSV file");
    }

    }
}