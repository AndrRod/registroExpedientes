package com.tc.registro.art1.service.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tc.registro.art1.dto.UserPartDto;
import com.tc.registro.art1.dto.UserTokenResponseDto;
import com.tc.registro.art1.dto.mapper.UserMapper;
import com.tc.registro.art1.exception.BadRequestException;
import com.tc.registro.art1.exception.MessageGeneral;
import com.tc.registro.art1.exception.NotFoundException;
import com.tc.registro.art1.model.User;
import com.tc.registro.art1.repository.UserRepository;
import com.tc.registro.art1.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserAuthService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserPartDto updateUser(Long id, UserPartDto userPartDto) {
        User user = getUserEntity(id);
        return userMapper.userEntityToPartDto(userRepository.save(userMapper.updateUserFromDto(user, userPartDto)));
    }
    @Override
    public UserPartDto getUserDto(Long id) {
        return userMapper.userEntityToPartDto(getUserEntity(id));
    }

    @Override
    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));

    }

    @Override
    public String deleteUser(Long id, HttpServletRequest request){
        userRepository.delete(getUserEntity(id));
            return "the perfil was deleted succesful";
    }

    @Override
    public Authentication authenticationFilter(String email, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication autenticacionFilter = authenticationManager.authenticate(authenticationToken);
        return autenticacionFilter;
    }
    public User findUserByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new NotFoundException("user not found"));
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        Collection<SimpleGrantedAuthority> authorizations = Collections.singleton(new SimpleGrantedAuthority("none"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorizations);
    }
    @Override
    public UserTokenResponseDto userLogin(String email, String password, HttpServletRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = findUserByEmail(email);
        if(password == null) throw new BadRequestException("password cannot be empty");
        if(!passwordEncoder.matches(password, user.getPassword())) throw new NotFoundException("the password is not the same");
        org.springframework.security.core.userdetails.User userAut = (org.springframework.security.core.userdetails.User) authenticationFilter(email, password).getPrincipal();

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create()
                .withSubject(userAut.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 180 * 60 * 1000)) // 2 horas
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role",userAut.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String update_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutos
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        return new UserTokenResponseDto(user.getEmail(), access_token,  update_token);
    }

    @Override
    public void refreshToken(UserTokenResponseDto form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(form.getRefresh_Token() == null || !form.getRefresh_Token().startsWith("Bearer ")) throw new BadRequestException("token refresh error");
        try {
            String refresh_token = form.getRefresh_Token().substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String email = decodedJWT.getSubject();
            User user = findUserByEmail(email);
            String acceso_token = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 minutos
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("role", Collections.singletonList("none"))
                    .sign(algorithm);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),  new HashMap<>(){{put("message", "the user " + user.getEmail()+ " refresh the token succesfully"); put("access_token", acceso_token); put("update_token", refresh_token);}});
        }catch (Exception exception){
            response.setStatus(FORBIDDEN.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), new MessageGeneral(exception.getMessage(), request.getRequestURI()));
        }
    }
    @Override
    public MessageGeneral registerUser(UserPartDto userDtoCreate, HttpServletRequest request) {
        if (userRepository.existsByEmail(userDtoCreate.getEmail()))throw new BadRequestException("email already exist");
        if(userDtoCreate.getPassword()== null) throw new BadRequestException("password is not present");
        User user = userMapper.dtoUserPartCreateToEntity(userDtoCreate);
        userRepository.save(user);
        return new MessageGeneral("User created", request.getRequestURI());
    }

}
