package com.esite.framework.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class JpaLikeQueryHelper {

	public static Predicate like(CriteriaBuilder cb,Path<String> path,String compare) {
		return cb.like(path, formatCompareString(compare),'/');
	}
	
	private static String formatCompareString(String str) {
		return "%" + str + "%";
	}
}
