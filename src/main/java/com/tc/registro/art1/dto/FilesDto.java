package com.tc.registro.art1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class FilesDto {
    private Long id;
    private String numberFile;
    @Lob
    @Column(length = 1000)
    private String title;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private LocalDate creationDate;
    private String state;
}
