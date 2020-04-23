package com.example.demo.Security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public enum ApplicationUserPermission {
    STUDENT_READ("student_read"),
    STUDENT_WRITE("student_write"),
    COURSE_WRITE("course_write"),
    COURSE_READ("course_read");

    private final String permission;



    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
    public String getPermission(){return permission; }
}
