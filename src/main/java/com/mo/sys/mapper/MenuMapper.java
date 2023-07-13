package com.mo.sys.mapper;

import com.mo.sys.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author morangu
 * @since 2023-05-29
 */
public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> getMenuListByUserId(@Param("userId") Integer userId,
                                          @Param("pid") Integer pid);



}
