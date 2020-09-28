package com.multitenancy.service;

import com.multitenancy.entity.User;

public interface SampleService {

	public User findUserById(long id);

	public void createUser(User user);

}
