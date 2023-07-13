package com.mo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mo.sys.entity.Role;
import com.mo.sys.entity.RoleMenu;
import com.mo.sys.mapper.RoleMapper;
import com.mo.sys.mapper.RoleMenuMapper;
import com.mo.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author morangu
 * @since 2023-05-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

//    @Autowired 这个也行
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional //涉及到多个表的操作，添加事务注解
    public void addRole(Role role) {
//      写入角色表
        this.baseMapper.insert(role);
//       写入角色菜单关系表
        if(role.getRoleId() !=null){
            for(Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }
    }

    @Override
    public Role getRoleById(Integer id) {
        Role role = this.baseMapper.selectById(id);
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @Transactional //涉及到多个表的操作，添加事务注解
    public void updateRole(Role role) {
//        修改角色表
        this.baseMapper.updateById(role);
//        删除权限表原有的权限（x_role_menu）
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getRoleId());
        roleMenuMapper.delete(wrapper);
//        新增权限表（x_role_menu）
        if(role.getRoleId() !=null){
            for(Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(), menuId));
            }
        }
    }

    @Override
    @Transactional //涉及到多个表的操作，添加事务注解
    public void deleteRoleById(Integer id) {
        this.baseMapper.deleteById(id);
        // 删除权限表原有的权限（x_role_menu）
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(wrapper);

    }
}
