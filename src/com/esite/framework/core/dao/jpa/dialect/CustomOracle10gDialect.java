package com.esite.framework.core.dao.jpa.dialect;

import org.hibernate.dialect.Oracle10gDialect;

public class CustomOracle10gDialect extends Oracle10gDialect {

	public CustomOracle10gDialect() {
		super();
		registerHibernateType(-9, "string");
	}
	
}
