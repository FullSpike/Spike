package org.example.week02.service;

import jakarta.validation.constraints.Pattern;

public interface adminService {

    void registerAdmin(String number, String password);

    void updatePassword(long id, String password);
}
