package com.motg.server.domain.auth.usecases;

import com.motg.server.controllers.auth.models.entities.SignupRequest;
import com.motg.server.data.entities.User;
import com.motg.server.data.repos.AuthRepository;
import com.motg.server.utils.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Service
@Log4j2
public class AuthService {
  @Autowired
  private AuthRepository authRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private AuthenticationManager authenticationManager;


  public String registerUser(SignupRequest request) throws Exception {
    if (authRepository.existsByEmail(request.getEmail())) {
      throw new Exception("User Already exists with email : "+request.getEmail());
    }

    User user = User.builder()
      .firstname(request.getFirstName())
      .lastname(request.getLastName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .mobile(request.getMobile())
      .address(request.getAddress())
      .isActive(true)
      .build();

    authRepository.save(user);
    return "User registered successfully!";
  }
}
