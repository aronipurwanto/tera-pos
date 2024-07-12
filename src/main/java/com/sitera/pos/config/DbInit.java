package com.sitera.pos.config;

import com.sitera.pos.auth.model.entity.RoleEntity;
import com.sitera.pos.auth.model.entity.UserEntity;
import com.sitera.pos.auth.repository.RoleRepo;
import com.sitera.pos.auth.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbInit implements CommandLineRunner {
    private final PasswordEncoder encoder;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    @Override
    public void run(String... args) throws Exception {
        initRole();
        initRole();
    }

    public void initRole(){
        if(roleRepo.count() > 0) {
            return;
        }

        try{
            var roleList = Arrays.asList(
                    new RoleEntity("ROLE_USER"),
                    new RoleEntity("ROLE_ADMIN"),
                    new RoleEntity("ROLE_SUPER_USER")
            );
            roleRepo.saveAllAndFlush(roleList);
        }catch(Exception e){
            log.error("Save role error: {}", e.getMessage());
        }
    }

    public void initUser(){
        if(userRepo.count() > 0) {
            return;
        }
        List<UserEntity> userList = new ArrayList<>();
        RoleEntity roleUser = roleRepo.findByName("ROLE_USER").orElse(null);
        if(roleUser != null) {
            userList.add(new UserEntity("user", "satu", "user01@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleUser)));
            userList.add(new UserEntity("user", "dua", "user02@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleUser)));
            userList.add(new UserEntity("user", "tiga", "user03@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleUser)));
        }

        RoleEntity roleAdmin = roleRepo.findByName("ROLE_ADMIN").orElse(null);
        if(roleAdmin != null) {
            userList.add(new UserEntity("admin", "satu", "admin01@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleAdmin)));
            userList.add(new UserEntity("admin", "dua", "admin02@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleAdmin)));
            userList.add(new UserEntity("admin", "tiga", "admin03@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleAdmin)));
        }

        RoleEntity roleSuperUser = roleRepo.findByName("ROLE_SUPER_USER").orElse(null);
        if(roleSuperUser != null) {
            userList.add(new UserEntity("super", "user", "super.user@gmail.com", encoder.encode("P@ssW0rd32!"), Arrays.asList(roleSuperUser)));
        }

        try {
            userRepo.saveAllAndFlush(userList);
        }catch (Exception e){
            log.error("Save user error: {}", e.getMessage());
        }
    }
}
