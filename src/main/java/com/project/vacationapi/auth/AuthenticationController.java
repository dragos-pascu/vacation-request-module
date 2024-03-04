package com.project.vacationapi.auth;

import com.project.vacationapi.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register-user")
  public ResponseEntity<AuthenticationResponse> registerUser(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request, Role.USER));
  }

  @PostMapping("/register-admin")
  public ResponseEntity<AuthenticationResponse> registerAdmin(
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request,Role.ADMIN));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

}
