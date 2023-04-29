package com.cipher.csvreadingapi.repository;

import com.cipher.csvreadingapi.model.CSVContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSVContentRepository extends JpaRepository<CSVContent, Long> {

}
