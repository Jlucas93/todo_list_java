package br.com.lucas.todo_list.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody TaskModel taksModel) {

    var task_created = this.taskRepository.save(taksModel);
    return ResponseEntity.status(200).body(task_created);
  }
}
