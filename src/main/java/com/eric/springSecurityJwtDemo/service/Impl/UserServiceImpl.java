package com.eric.springSecurityJwtDemo.service.Impl;

import com.eric.springSecurityJwtDemo.config.JwtService;
import com.eric.springSecurityJwtDemo.dto.*;
import com.eric.springSecurityJwtDemo.entity.UserEntity;
import com.eric.springSecurityJwtDemo.enums.Role;
import com.eric.springSecurityJwtDemo.repository.UserRepository;
import com.eric.springSecurityJwtDemo.service.EmailSenderService;
import com.eric.springSecurityJwtDemo.service.UserService;
import com.eric.springSecurityJwtDemo.util.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private  final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final EmailSenderService emailSenderService;
  public AuthResponse registerUser(RegisterRequestDto registerRequestDto){
    UserEntity userEntity = UserEntity.builder()
            .firstName(registerRequestDto.getFirstName())
            .lastName(registerRequestDto.getFirstName())
            .email(registerRequestDto.getEmail())
            .password(encoder.encode(registerRequestDto.getPassword()))
            .role(Role.USER)
            .build();

    UserEntity savedUser = userRepository.save(userEntity);
    // send email alert
    EmailDetails emailDetails = EmailDetails.builder()
            .recipient(savedUser.getEmail())
            .subject("ACCOUNT CREATION")
            .messageBody("Congratulation Your account has been successfully created")
            .build();


    var jwtToken = jwtService.generateToken(userEntity);

    return AuthResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
            .registrationInfo(RegistrationInfo.builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .email(userEntity.getEmail())
                    .token(jwtToken)
                    .build())
            .build();
  }

  @Override
  public LoginResponse logic(LoginRequestDto loginRequestDto) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(),
                    loginRequestDto.getPassword()
            )
    );
    UserEntity userEntity = userRepository.findByEmail(loginRequestDto.getEmail())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(userEntity);
    return LoginResponse.builder()
            .responseCode(AccountUtils.LOGIN_SUCCESS_CODE)
            .responseMessage(AccountUtils.LOGIN_SUCCESS_MESSAGE)
            .loginInfo(LoginInfo.builder()
                    .email(userEntity.getEmail())
                    .token(jwtToken)
                    .build())
            .build();
  }
}
