package com.dsoft.dto.userdto;

import com.dsoft.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;

    private String email;

    private String password;

    private Role role;
}