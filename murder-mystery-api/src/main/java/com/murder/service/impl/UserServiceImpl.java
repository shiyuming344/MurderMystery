package com.murder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.murder.domain.User;
import com.murder.service.UserService;
import com.murder.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Cookie
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-20 23:24:33
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getByUsername(String name) {
        return getOne(new QueryWrapper<User>().eq("name",name));
    }

}




