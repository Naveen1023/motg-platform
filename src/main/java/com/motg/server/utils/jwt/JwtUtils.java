package com.motg.server.utils.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Log4j2
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    log.info("Authorization Header: {}", bearerToken);
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // Remove Bearer prefix
    }
    return null;
  }

  public String generateTokenFromUsername(UserDetails userDetails) {
    String username = userDetails.getUsername();
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(key())
      .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
      .verifyWith((SecretKey) key())
      .build().parseSignedClaims(token)
      .getPayload().getSubject();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public boolean validateJwtToken(String authToken) {
    try {
      System.out.println("Validating JWT token....");
      Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
      return true;
    } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      System.out.println("Invalid JWT token: " + e.getMessage());
    }
    return false;
  }
}
