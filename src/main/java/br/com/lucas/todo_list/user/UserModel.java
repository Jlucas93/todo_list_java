package br.com.lucas.todo_list.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "user_table")
public class UserModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(name = "user_name", unique = true)
  private String userName;
  private String name;
  private String password;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

}
