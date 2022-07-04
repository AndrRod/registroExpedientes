package com.tc.registro.art1.controller;
import com.tc.registro.art1.dto.UserDtoLogin;
import com.tc.registro.art1.dto.UserPartDto;
import com.tc.registro.art1.dto.UserTokenResponseDto;
import com.tc.registro.art1.dto.mapper.UserMapper;
import com.tc.registro.art1.exception.MessageGeneral;
import com.tc.registro.art1.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = {"http://127.0.0.1:3000"}
        , methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE}
        ,allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserMapper personMapper;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public MessageGeneral registerUser(@RequestBody UserPartDto personDtoCreate, HttpServletRequest request){
        return userAuthService.registerUser(personDtoCreate, request);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserTokenResponseDto loginUser(@RequestBody UserDtoLogin user, HttpServletRequest request){
        return userAuthService.userLogin(user.getEmail(), user.getPassword(), request);
    }
    @GetMapping("/logoutsuccess")
    @ResponseStatus(HttpStatus.OK)
    public MessageGeneral logout (HttpServletRequest request){
        return new MessageGeneral("user was logout", request.getRequestURI());
    }
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(@RequestBody UserTokenResponseDto form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        userAuthService.refreshToken(form, request, response);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserPartDto updateUserById(@PathVariable Long id, @RequestBody UserPartDto userPartDto){
        return userAuthService.updateUser(id, userPartDto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delteUserById(Long id, HttpServletRequest request){
        return userAuthService.deleteUser(id, request);
    }
}