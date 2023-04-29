package com.cipher.csvreadingapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="file_content")
public class CSVContent {
    @Id
    @Column(name="Id")
    private Long id;

    @Column(name="Name")
    private String name;

    @Column(name="Description")
    private String description;

    @Column(name="Published")
    private boolean published;



    public CSVContent(Long id, String name, String description, boolean published) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.published = published;
    }

    public CSVContent() {

    }

    @Override
    public String toString() {
        return "FileContent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", published='" + published + '\'' +
                '}';
    }
}
