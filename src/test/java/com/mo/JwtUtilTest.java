package com.mo;

import com.mo.common.utils.JwtUtil;
import com.mo.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;


    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("张三");
        user.setPhone("123454325");
        String token = jwtUtil.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhMWZlMmE4Ny01YTVjLTQxZmUtODNiYy0xMDcyZjVmZjZkMTQiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzNDU0MzI1XCIsXCJ1c2VybmFtZVwiOlwi5byg5LiJXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjg1NDM3MTk3LCJleHAiOjE2ODU0Mzg5OTd9.FnLj0SulXR4CrMGcGryZWX-xdSBA4zgARnGPJoMbKPs";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhMWZlMmE4Ny01YTVjLTQxZmUtODNiYy0xMDcyZjVmZjZkMTQiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzNDU0MzI1XCIsXCJ1c2VybmFtZVwiOlwi5byg5LiJXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjg1NDM3MTk3LCJleHAiOjE2ODU0Mzg5OTd9.FnLj0SulXR4CrMGcGryZWX-xdSBA4zgARnGPJoMbKPs";
        User user = jwtUtil.parseToken(token,User.class);
        System.out.println(user);
    }
}
