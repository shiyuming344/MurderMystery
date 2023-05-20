package com.murder.service;

import com.murder.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Cookie
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-20 23:24:33
*/
public interface UserService extends IService<User> {

    User getByUsername(String name);

}
