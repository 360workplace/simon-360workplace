package com.workplace.simon.dto;

import com.sun.istack.NotNull;

public class User {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    private String email;
}
