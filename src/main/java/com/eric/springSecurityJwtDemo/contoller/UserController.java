package com.eric.springSecurityJwtDemo.contoller;

import com.eric.springSecurityJwtDemo.dto.AuthResponse;
import com.eric.springSecurityJwtDemo.dto.LoginRequestDto;
import com.eric.springSecurityJwtDemo.dto.LoginResponse;
import com.eric.springSecurityJwtDemo.dto.RegisterRequestDto;
import com.eric.springSecurityJwtDemo.service.Impl.UserServiceImpl;
import com.eric.springSecurityJwtDemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {
  private final UserService userService;
  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDto registerRequestDto){
    return  ResponseEntity.ok(userService.registerUser(registerRequestDto));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequestDto){
    return  ResponseEntity.ok(userService.logic(loginRequestDto));
  }



}



