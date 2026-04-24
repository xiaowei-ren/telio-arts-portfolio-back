package com.telioarts.portfolio.bll;

import com.telioarts.portfolio.dal.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    AdminRepository  adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean login(String login, String rawPassword) {
        System.out.println(">>> 正在尝试登录. 用户名: " + login + " | 密码: " + rawPassword);
        //  Rechercher l'administrateur par son identifiant (login)
        return adminRepository.findByLogin(login)
                .map(admin -> passwordEncoder.matches(rawPassword, admin.getPassword()))
                .orElse(false);
    }
}
