package com.example.demorg.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "journal_entries")
@Data
public class Journal {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDate date;
}
