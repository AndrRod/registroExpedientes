package com.tc.registro.art1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Data @Entity
public class Files {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "can't be empty or null")
    @Column(unique = true)
    private String numberFile;
    @NotNull(message = "can't be empty or null")
    private String title;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private LocalDate creationDate;
}
