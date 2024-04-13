package com.eric.springSecurityJwtDemo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
  private  String responseCode;
  private String responseMessage;
  private RegistrationInfo registrationInfo;
}
