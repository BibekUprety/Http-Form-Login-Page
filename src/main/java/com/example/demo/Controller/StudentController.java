package com.example.demo.Controller;

import com.example.demo.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students/")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
      new Student(1,"Bibek"),
            new Student(2,"Abc"),
            new Student(3,"xyz")
    );

    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {

        return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId()))
                .findFirst().orElseThrow((() -> new IllegalStateException("Student invalid id"+ studentId)));
    }

}
