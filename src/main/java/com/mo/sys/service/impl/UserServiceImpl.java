package com.mo.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.common.utils.JwtUtil;
import com.mo.sys.entity.Menu;
import com.mo.sys.entity.User;
import com.mo.sys.entity.UserRole;
import com.mo.sys.mapper.UserMapper;
import com.mo.sys.mapper.UserRoleMapper;
import com.mo.sys.service.IMenuService;
import com.mo.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author morangu
 * @since 2023-05-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;

//    密码未加密的登录逻辑
//    @Override
//    public Map<String, Object> login(User user) {
////        根据用户名和密码查询
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername,user.getUsername());
//        wrapper.eq(User::getPassword,user.getPassword());
//        User loginUser = this.baseMapper.selectOne(wrapper);
////        结果不为空，则生成token ，并将用户信息 存入redis
//        if(loginUser != null){
////            暂时使用uuid，终极方案是 jwt
//            String key = "user:"+ UUID.randomUUID();
////            存入redis
//            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);//有效时间30分钟
////            返回数据
//            Map<String, Object> data = new HashMap<>();
//            data.put("token",key);
//            return data;
//        }
//        return null;
//    }

//    密码加密后的登录逻辑
    @Override
    public Map<String, Object> login(User user) {
//        根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
//        结果不为空，并且密码和传入的密码是匹配的，则生成token ，并将用户信息 存入redis
//        passwordEncoder.matches 参数一：用户传入的密码（明文），参数二：数据库查询出来的密码（加密后的密码）
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
////            暂时使用uuid，终极方案是 jwt
//            String key = "user:"+ UUID.randomUUID();
////            存入redis
//            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);//有效时间30分钟
////            返回数据
//            Map<String, Object> data = new HashMap<>();
//            data.put("token",key);
//              return data;

//            jwt 方案

//            存入redis
            loginUser.setPassword(null);
            String token = jwtUtil.createToken(loginUser);
//            返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }



    @Override
    public Map<String, Object> getUserInfo(String token) {
//        根据token 获取用户信息，redis
//        Object obj = redisTemplate.opsForValue().get(token);
//        jwt 方案
        User loginUser=null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(loginUser!=null){
//            User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class); redis
            Map<String, Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());
//            角色
            List<String> roleList = this.baseMapper.getRoleNamesByUserId(loginUser.getId());
            data.put("roles",roleList);

//            权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList",menuList);

            return  data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
//        redisTemplate.delete(token); redis
    }

    @Override
    @Transactional //涉及到多张表的操作，使用事务注解
    public void addUser(User user) {
//       写入用户表
        this.baseMapper.insert(user);
//        写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList !=null){
            for(Integer roleId : roleIdList){
                userRoleMapper.insert(new UserRole(null,user.getId(),roleId));
            }
        }
    }

    @Override
    public User getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);

        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);
        List<Integer> roleIdList = userRoleList.stream()
                                               .map(userRole->{return userRole.getRoleId();})
                                               .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);

        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
//        更新用户表
        this.baseMapper.updateById(user);
//        清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(wrapper);
//        设置新角色
        //        写入用户角色表
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList !=null){
            for(Integer roleId : roleIdList){
                userRoleMapper.insert(new UserRole(null,user.getId(),roleId));
            }
        }

    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
//        删除用户表
        this.baseMapper.deleteById(id);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);

    }
}
