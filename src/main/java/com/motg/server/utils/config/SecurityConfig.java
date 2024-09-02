package com.motg.server.utils.config;

import com.motg.server.domain.auth.usecases.CustomUserDetailsService;
import com.motg.server.utils.jwt.AuthEntryPointJwt;
import com.motg.server.utils.jwt.AuthTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Autowired
  private CustomUserDetailsService userDetailsService;
  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeRequests ->
      authorizeRequests.requestMatchers("/h2-console/**").permitAll()
        .requestMatchers("/health").permitAll()
        .requestMatchers("/auth/signin").permitAll()
        .requestMatchers("/auth/signup").permitAll()
        .anyRequest().authenticated());
    http.sessionManagement(
      session ->
        session.sessionCreationPolicy(
          SessionCreationPolicy.STATELESS)
    );
    http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
    //http.httpBasic(withDefaults());
    http.headers(headers -> headers
      .frameOptions(frameOptions -> frameOptions
        .sameOrigin()
      )
    );
    http.csrf(csrf -> csrf.disable());
    http.addFilterBefore(authenticationJwtTokenFilter(),
      UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
