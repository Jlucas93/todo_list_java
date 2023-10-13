package br.com.lucas.todo_list.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public TaskModel create(@RequestBody TaskModel taksModel, HttpServletRequest request) {

    var user_id = request.getAttribute("UserId");

    taksModel.setUserId((UUID) user_id);

    var task_created = this.taskRepository.save(taksModel);
    return task_created;

  }
}
