package com.example.demo.Model;

public class Student {
    private final Integer studentId;
    private final String studentName;

    public Student(Integer id, String studentName) {
        this.studentId = id;
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
