package com.sitera.pos.auth.service;

import com.sitera.pos.auth.model.request.ChangePasswordReq;

import java.security.Principal;

public interface AuthService {
    void changePassword(ChangePasswordReq request, Principal connectedUser);
}
