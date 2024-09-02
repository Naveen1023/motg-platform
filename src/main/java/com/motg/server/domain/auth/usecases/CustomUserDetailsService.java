package com.motg.server.domain.auth.usecases;

import com.motg.server.data.entities.User;
import com.motg.server.data.repos.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AuthRepository authRepository;

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = authRepository.findByEmail(email).get();
    List<String> roles = new ArrayList<>();
    roles.add("USER");

    UserDetails userDetails =
      org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .roles(roles.toArray(new String[0]))
        .build();

    return userDetails;
  }
}
