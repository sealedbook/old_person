package com.esite.framework.core.dao.jpa.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

public class CustomRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ICustomRepository<T, ID> {

	private final EntityManager entityManager;
	
	public CustomRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager = em;
	}

	public CustomRepository(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

}
