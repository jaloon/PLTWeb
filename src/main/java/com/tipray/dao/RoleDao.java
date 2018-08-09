package com.tipray.dao;


import com.tipray.bean.Page;
import com.tipray.bean.Role;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisAnno
public interface RoleDao extends BaseDao<Role>{
	/**
	 * 统计角色数量
	 * @param role
	 * @return
	 */
	Long countByVo(@Param("role")Role role);
	/**
	 * 查询角色集合
	 * @param role
	 * @param page 
	 * @return
	 */
	List<Role> findByVo(@Param("role")Role role, @Param("page")Page page);
	/**
	 * 根据角色名称获取角色
	 * @param name
	 * @return
	 */
	Role getByName(String name);
	/**
	 * 根据Id和权限名查找角色
	 * @param id
	 * @param ename
	 * @return
	 */
	Role getByIdAndPermission(@Param("id")Long id, @Param("ename")String permissionEname);
	/**
	 * 根据Id集合获取角色
	 * @param ids
	 * @return
	 */
	List<Role> findByIds(String ids);
	
}
