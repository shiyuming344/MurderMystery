package com.murder.common.security;

import com.murder.common.constant.JwtConstant;
import com.murder.domain.CheckResult;
import com.murder.domain.User;
import com.murder.service.UserService;
import com.murder.util.JwtUtils;
import com.murder.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;

    private static final String URL_WHITELIST[] ={
            "/login",
            "/logout",
            "/captcha",
            "/password",
            "/test/**",
            "/image/**"
    } ;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        System.out.println("请求url:"+request.getRequestURI());
        // 如果token是空 或者 url在白名单里，则放行
        if(StringUtil.isEmpty(token) || new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }
        CheckResult checkResult = JwtUtils.validateJWT(token);
        if(!checkResult.isSuccess()){
            switch (checkResult.getErrCode()){
                case JwtConstant.JWT_ERRCODE_NULL:throw new JwtException("Token不存在");
                case JwtConstant.JWT_ERRCODE_FAIL:throw new JwtException("Token验证不通过");
                case JwtConstant.JWT_ERRCODE_EXPIRE:throw new JwtException("Token过期");
            }
        }
        Claims claims = JwtUtils.parseJWT(token);
        String username = claims.getSubject();
        User userser = userService.getByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,myUserDetailsService.getUserAuthority());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }
}
