package com.esite.framework.dictionary.service;

import java.util.List;

import com.esite.framework.dictionary.entity.DictionaryEntity;


public interface DictionaryService {

	public List<DictionaryEntity> getSubDictionaryByParentCode(String parentCode);

	public DictionaryEntity getDictionaryByParentIdAndCode(String parentId,String code);
	
	public void flashDictionary();
	
	public DictionaryEntity getDictionaryByparentIdAndName(String parentId,String name);

}
