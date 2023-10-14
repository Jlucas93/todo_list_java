package br.com.lucas.todo_list.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.todo_list.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

    var user_id = request.getAttribute("UserId");

    var current_date = LocalDateTime.now();

    if (current_date.isAfter(taskModel.getStaterd_at()) || current_date.isAfter(taskModel.getEnded_at())) {
      return ResponseEntity.status(400).body("Invalid started date!");
    }
    if (taskModel.getStaterd_at().isAfter(taskModel.getEnded_at())) {
      return ResponseEntity.status(400).body("Started date must be before the end date!");
    }

    taskModel.setIdUser((UUID) user_id);

    var task_created = this.taskRepository.save(taskModel);
    return ResponseEntity.status(200).body(task_created);

  }

  @GetMapping("/")
  public List<TaskModel> listUserTasks(HttpServletRequest request) {
    var user_id = request.getAttribute("UserId");

    return this.taskRepository.findByIdUser((UUID) user_id);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

    var task = this.taskRepository.findById(id).orElse(null);
    var user_id = request.getAttribute("UserId");

    if (task == null) {
      return ResponseEntity.status(404).body("Task not found!");
    }

    if (!task.getIdUser().equals(user_id)) {
      return ResponseEntity.status(401).body("User not authorized to update this task!");
    }

    Utils.copyNonNullProperties(taskModel, task);

    var task_updated = this.taskRepository.save(task);

    return ResponseEntity.status(200).body("Task updated!" + task_updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {

    var task = this.taskRepository.findById(id).orElse(null);

    if (task == null) {
      return ResponseEntity.status(404).body("Task not found!");
    }

    this.taskRepository.delete(task);
    return ResponseEntity.status(200).body("Task deleted!");
  }
}
