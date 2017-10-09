package com.esite.framework.core.dao.jpa.repository;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ICustomRepository<T, ID extends Serializable> extends Repository<T, ID> {

}
