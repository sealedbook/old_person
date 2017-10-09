package com.esite.framework.core.dao.jpa.factory;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;

import com.esite.framework.core.dao.jpa.repository.CustomRepository;

public class CustomRepositoryFactory extends JpaRepositoryFactory {

	public CustomRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}
	
	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {
		JpaEntityInformation<?, Serializable> entityInfo = getEntityInformation(metadata.getDomainType());
		return new CustomRepository(entityInfo, entityManager);
	}


	@Override  
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {   
		return CustomRepository.class;
	}

}
