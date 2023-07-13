package com.members.user.service.impl;

import com.members.user.entity.User;
import com.members.user.mapper.UserMapper;
import com.members.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author morangu
 * @since 2023-06-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
