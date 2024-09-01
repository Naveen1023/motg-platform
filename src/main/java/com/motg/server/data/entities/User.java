package com.motg.server.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity@Builder
@ToString
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name")
  private String firstname;
  @Column(name = "last_name")
  private String lastname;
  @Column(name = "email")
  private String email;
  @Column(name = "mobile")
  private String mobile;
  @Column(name = "password")
  private String password;
  @Column(name = "address")
  private String address;
  @Column(name = "is_active")
  private Boolean isActive;
  @Column(name = "created_at")
  private Instant createdAt;
  @Column(name = "updated_at")
  private Instant updatedAt;
}
