package com.eric.springSecurityJwtDemo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationInfo {
  private String firstName;
  private String lastName;
  private String email;
  private String token;
}
