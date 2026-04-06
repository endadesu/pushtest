package com.memapp.todo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin
public class TodoController {

    private final TodoMapper mapper;

    public TodoController(TodoMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping
    public List<Todo> getAll() {
        return mapper.findAll();
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo request) {
        if (request.getText() == null || request.getText().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Todo todo = new Todo(request.getText().trim());
        mapper.insert(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable long id, @RequestBody Todo request) {
        return mapper.findById(id)
                .map(todo -> {
                    todo.setCompleted(request.isCompleted());
                    mapper.update(todo);
                    return ResponseEntity.ok(todo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        int deleted = mapper.deleteById(id);
        if (deleted == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
