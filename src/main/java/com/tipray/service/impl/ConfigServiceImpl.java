package com.tipray.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tipray.bean.Config;
import com.tipray.service.ConfigService;

@Transactional
@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	@Resource
	private com.tipray.dao.ConfigDao ConfigDao;
	
	@Override
	public Config addConfig(Config bean){
		if(bean!=null){
			ConfigDao.add(bean);
		}
		return bean;
	}

	@Override
	public Config updateConfig(Config bean){
		if(bean!=null){
			ConfigDao.update(bean);
		}
		return bean;
	}

	@Override
	public Config getConfigById(Long id) {
		if(id==null){
			return null;
		}
		return ConfigDao.getById(id);
	}
	
	@Override
	public List<Config> findAllConfigs() {
		return ConfigDao.findAll();
	}
}
