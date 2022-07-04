package com.tc.registro.art1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UserPartDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
