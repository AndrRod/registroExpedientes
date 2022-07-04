package com.tc.registro.art1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class UserDtoLogin {
    private String email;
    private String password;
}
