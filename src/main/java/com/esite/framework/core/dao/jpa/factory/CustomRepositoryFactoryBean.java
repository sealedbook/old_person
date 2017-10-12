package com.esite.framework.core.dao.jpa.factory;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.esite.framework.core.dao.jpa.entitymanager.CustomEntityManager;

public class CustomRepositoryFactoryBean<T extends JpaRepository<Object, Serializable>> extends JpaRepositoryFactoryBean<T, Object, Serializable> {

	@Override  
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		//return new CustomRepositoryFactory(em);
		return new CustomRepositoryFactory(new CustomEntityManager(em));
	}
	
}
