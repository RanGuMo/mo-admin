package com.mo.sys.mapper;

import com.mo.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author morangu
 * @since 2023-05-29
 */
public interface UserMapper extends BaseMapper<User> {
  public List<String> getRoleNamesByUserId(Integer userId);
}
