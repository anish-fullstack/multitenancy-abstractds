package com.multitenancy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multitenancy.config.TenantContextHolder;
import com.multitenancy.dao.SampleDao;
import com.multitenancy.entity.User;

@Service
@Transactional(readOnly = true, transactionManager = "multiTenantTxManager")
public class SampleServiceImpl implements SampleService {
	
	@Autowired
	private SampleDao dao;

	@Override
	public User findUserById(long id) {
		TenantContextHolder.setTenantName("tenant2");
		return dao.findUserById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void createUser(User user) {
		dao.createUser(user);
		TenantContextHolder.setTenantName("tenant2");
		dao.createUser(user);
	}

}
