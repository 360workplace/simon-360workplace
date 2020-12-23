package com.workplace.simon.service;

public interface SecurityService {
    boolean isAuthenticated();

    void autoLogin(String username, String password);
}
