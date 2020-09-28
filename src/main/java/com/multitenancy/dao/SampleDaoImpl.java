package com.multitenancy.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.multitenancy.entity.User;

@Repository
public class SampleDaoImpl implements SampleDao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public User findUserById(long id) {
		Query query = manager.createQuery("SELECT user FROM User user WHERE user.id =:id");
		query.setParameter("id", id);
		User user = (User) query.getSingleResult();
		return user;
	}

	@Override
	public void createUser(User user) {
		manager.persist(user);
	}

}
