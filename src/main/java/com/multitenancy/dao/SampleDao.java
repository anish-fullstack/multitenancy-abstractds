package com.multitenancy.dao;

import com.multitenancy.entity.User;

public interface SampleDao {

	public User findUserById(long id);

	public void createUser(User user);

}
