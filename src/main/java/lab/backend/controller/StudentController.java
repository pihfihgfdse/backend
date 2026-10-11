package lab.backend.controller;

import lab.backend.model.Student;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class StudentController {

    private final List<Student> students = List.of(
            new Student(1L, "张三", 95.0),
            new Student(2L, "李四", 78.0)
    );

    @GetMapping("/api/students")
    public List<Student> list() {
        return students;
    }

    @GetMapping("/api/students/{id}")
    public Student get(@PathVariable Long id) {
        return students.stream()
                .filter(s -> s.id() == id)
                .findFirst()
                .orElseThrow();
    }
}