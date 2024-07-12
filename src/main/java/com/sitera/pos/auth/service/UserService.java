package com.sitera.pos.auth.service;

import com.sitera.pos.auth.model.request.ChangePasswordReq;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface UserService extends UserDetailsService {
    void changePassword(ChangePasswordReq request, Principal connectedUser);
}
