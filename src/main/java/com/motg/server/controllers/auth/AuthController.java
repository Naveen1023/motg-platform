package com.motg.server.controllers.auth;

import com.motg.server.commons.ResponseBase;
import com.motg.server.controllers.auth.models.entities.SignupRequest;
import com.motg.server.domain.auth.usecases.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ResponseBase<Object>> signup(@RequestBody SignupRequest request) {
    try{
      Object res = authService.registerUser(request);
      return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseBase.successMapper(res, "success"));
    }
    catch (Exception e) {
      log.error("Exception occurred while registering user: "+e);
      return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(ResponseBase.failureMapper(null, e.getMessage()));
    }
  }
}
