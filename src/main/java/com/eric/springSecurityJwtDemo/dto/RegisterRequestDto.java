package com.eric.springSecurityJwtDemo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
