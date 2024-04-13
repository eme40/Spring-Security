package com.eric.springSecurityJwtDemo.service;

import com.eric.springSecurityJwtDemo.dto.AuthResponse;
import com.eric.springSecurityJwtDemo.dto.LoginRequestDto;
import com.eric.springSecurityJwtDemo.dto.LoginResponse;
import com.eric.springSecurityJwtDemo.dto.RegisterRequestDto;

public interface UserService {
   AuthResponse registerUser(RegisterRequestDto registerRequestDto);
   LoginResponse logic(LoginRequestDto loginRequestDto);
}
