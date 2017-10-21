package com.esite.framework.dictionary.service;

import java.util.List;

import com.esite.framework.dictionary.entity.DictionaryEntity;


public interface DictionaryService {

	List<DictionaryEntity> getSubDictionaryByParentCode(String parentCode);

	DictionaryEntity getDictionaryByParentIdAndCode(String parentId,String code);
	
	void flashDictionary();
	
	DictionaryEntity getDictionaryByParentIdAndName(String parentId,String name);

}
