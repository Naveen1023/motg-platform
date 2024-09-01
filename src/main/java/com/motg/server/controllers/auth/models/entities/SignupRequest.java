package com.motg.server.controllers.auth.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignupRequest {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String mobile;
  private String address;
}
