package com.tc.registro.art1.service;
import com.tc.registro.art1.dto.UserPartDto;
import com.tc.registro.art1.dto.UserTokenResponseDto;
import com.tc.registro.art1.exception.MessageGeneral;
import com.tc.registro.art1.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserAuthService {
    Authentication authenticationFilter(String email, String password) throws AuthenticationException;
    UserTokenResponseDto userLogin(String email, String password, HttpServletRequest request);
    void refreshToken(UserTokenResponseDto responseDto, HttpServletRequest request, HttpServletResponse response) throws IOException;
    public MessageGeneral registerUser(UserPartDto personDtoCreate, HttpServletRequest request);
 
    public UserPartDto updateUser(Long id, UserPartDto personPartDto);
    public UserPartDto getUserDto(Long id);
    public User getUserEntity(Long id);
    public String deleteUser(Long id, HttpServletRequest request);
}
