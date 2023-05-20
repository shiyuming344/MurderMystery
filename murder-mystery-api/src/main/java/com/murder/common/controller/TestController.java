package com.murder.common.controller;

import com.murder.domain.R;
import com.murder.domain.User;
import com.murder.service.UserService;
import com.murder.util.JwtUtils;
import com.murder.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public R userList(@RequestHeader(required = false)String token){
        if(StringUtil.isNotEmpty(token)){
            Map<String,Object> resutlMap=new HashMap<>();
            List<User> userList = userService.list();
            resutlMap.put("userList",userList);
            return R.ok(resutlMap);
        }else{
            return R.error(401,"没有权限访问");
        }

    }

    @RequestMapping("/login")
    public R login(){
        String token = JwtUtils.genJwtToken("cookie");
        return R.ok().put("token",token);
    }
}
