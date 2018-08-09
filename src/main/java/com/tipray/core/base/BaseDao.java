package com.tipray.core.base;

import com.tipray.bean.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T extends BaseBean> {

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	Long add(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            对象
	 */
	void update(T entity);

	/**
	 * 根据ID删除实体对象
	 * 
	 * @param id
	 *            记录ID
	 */
	void delete(Long id);

	/**
	 * 根据ID获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	T getById(Long id);

	/**
	 * 获取所有的实体对象
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 根据条件获取实体数量
	 * 
	 * @param entity
	 * @return
	 */
	Long count(T entity);

	/**
	 * 分页查询实体对象
	 * 
	 * @param entity
	 * @param page
	 * @return
	 */
	List<T> findByPage(@Param("entity") T entity, @Param("page") Page page);
}
