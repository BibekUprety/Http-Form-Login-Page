package com.example.demo.Auth;

import java.util.Optional;

public interface ApplicationUserDao {
  Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
