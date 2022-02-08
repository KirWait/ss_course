package org.example.security;

import javassist.NotFoundException;
import org.example.security.jwt.JwtFactory;
import org.example.security.jwt.JwtUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private final UserService userService;
    @Autowired
    public JwtUserDetailsService(UserService userService){
        this.userService = userService;
    }



    @Override
    public UserDetails loadUserByUsername(String userName){

        JwtUser jwtUser = null;
        try {
            jwtUser = JwtFactory.create(userService.findByUsername(userName));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return jwtUser;
    }
}
