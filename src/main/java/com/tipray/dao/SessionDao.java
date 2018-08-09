package com.tipray.dao;

import com.tipray.bean.Session;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;
import org.apache.ibatis.annotations.MapKey;

import java.util.Date;
import java.util.Map;

@MyBatisAnno
public interface SessionDao extends BaseDao<Session> {
	/**
	 * 更新操作时间
	 * @param session
	 */
	void updateOperateDate(Session session);
	/**
	 * 删除session
	 * @param uuid
	 */
	void deleteByUUID(String uuid);
	/**
	 * 删除用户的所有session
	 * @param userId
	 */
	void deleteByUser(Long userId);
	/**
	 * 删除过期session
	 * @param timeoutDate  过期临界时间
	 */
	void deleteTimeOutSession(Date timeoutDate);
	/**
	 * 根据uuid获取session
	 * @param uuid
	 * @return
	 */
	Session getByUUID(String uuid);
	Session getByOldUUID(String uuid);
	/**
	 * 根据主键获取
	 * @param session
	 * @return
	 */
	Session getByUser(Long userId);

    /**
     * 获取session列表
     * @return
     */
	@MapKey("uuid")
    Map<String, Session> findSessions();
}
