package com.tc.registro.art1.dto.mapper;

import com.tc.registro.art1.dto.UserPageDto;
import com.tc.registro.art1.dto.UserPartDto;
import com.tc.registro.art1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Object> listUsersToListDtoPart(List<User> userList){return userList.stream().map(u-> userEntityToPartDto(u)).collect(Collectors.toList());}
    public User dtoUserPartCreateToEntity(UserPartDto user){return new User(null, user.getFirstName(), user.getLastName(), user.getEmail(), passwordEncoder.encode(user.getPassword()), LocalDate.now(), false);};
    public UserPartDto userEntityToPartDto(User user){return new UserPartDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());}
    public UserPageDto userEntittyToPageDto(User user){return new UserPageDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCreationDate());}
    public List<Object> listUserEntityToPageDto(List<User> usersList){return usersList.stream().map(u -> userEntittyToPageDto(u)).collect(Collectors.toList());}
    public User updateUserFromDto(User user, UserPartDto userPartDto){
         Optional.of(user).stream().forEach(
                (e)-> {
                    if(userPartDto.getFirstName() != null) e.setFirstName(userPartDto.getFirstName());
                    if(userPartDto.getLastName() != null) e.setLastName(userPartDto.getLastName());
                    if(userPartDto.getEmail() != null)  e.setEmail(userPartDto.getEmail());
                    if(userPartDto.getPassword()!= null) e.setPassword(passwordEncoder.encode(userPartDto.getPassword()));
                }
        );
        return user;
    }
}
