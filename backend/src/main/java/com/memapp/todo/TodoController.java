package com.memapp.todo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo request) {
        if (request.getText() == null || request.getText().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Todo todo = new Todo(request.getText().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(todo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable long id, @RequestBody Todo request) {
        return repository.findById(id)
                .map(todo -> {
                    todo.setCompleted(request.isCompleted());
                    return ResponseEntity.ok(repository.save(todo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
