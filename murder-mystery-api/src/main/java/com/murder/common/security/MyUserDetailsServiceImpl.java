package com.murder.common.security;

import com.murder.common.exception.UserCountLockException;
import com.murder.domain.User;
import com.murder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义UserDetails
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名或者密码错误！");
        }
        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),getUserAuthority());
    }

    public List<GrantedAuthority> getUserAuthority() {
        return new ArrayList<>();
    }
}
