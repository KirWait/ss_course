package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.example.mapper.UserMapper;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.example.translator.TranslationService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TranslationService translationService;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    UserService userService, TranslationService translationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.translationService = translationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto requestDto) throws NotFoundException {


            String username = requestDto.getUsername();

            UserEntity user = userService.findByUsername(username);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            logger.info(String.format(translationService.getTranslation("Successfully authenticated with %s username"), username));

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            logger.info(String.format(translationService.getTranslation("Successfully created JWT token: %s"), token));



            return new ResponseEntity<>(String.format(translationService.getTranslation(
                    "You have successfully logged in with %s! Here is your token %s"), username, token), HttpStatus.OK);

    }

    @PostMapping("/register")
    @Operation(summary = "Customer registration endpoint")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {

        UserEntity userEntity = userMapper.userRequestDTOToUserEntity(userRequestDto);
        userService.register(userEntity);
        UserResponseDto userResponseDto = userMapper.userEntityToUserResponseDTO(userEntity);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

}

