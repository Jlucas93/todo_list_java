package br.com.lucas.todo_list.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "task_table")
public class TaskModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private UUID idUser;
  @Column(length = 50)
  private String title;
  private String description;
  private String priority;

  @CreationTimestamp
  private LocalDateTime created_at;
  private LocalDateTime staterd_at;
  private LocalDateTime ended_at;

  public void setTitle(String title) throws Exception {
    if (title.length() > 50) {
      throw new Exception("Title must be less than 50 characters");
    }
    this.title = title;
  }
}
