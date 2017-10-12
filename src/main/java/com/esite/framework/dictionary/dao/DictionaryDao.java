package com.esite.framework.dictionary.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.framework.dictionary.entity.DictionaryEntity;

public interface DictionaryDao extends CrudRepository<DictionaryEntity, String>,JpaSpecificationExecutor<DictionaryEntity> {

	public DictionaryEntity queryByDicCode(String code);

	@Query("from DictionaryEntity dic where dic.parent.id=?1 and dic.dicCode=?2")
	public DictionaryEntity getDictionaryByParentIdAndCode(String parentId,String code);
	
	@Query(" from DictionaryEntity dic where dic.parent.id=?1 and dic.dicName like ?2 ")
	public DictionaryEntity getDictionaryByparentIdAndName(String parentId,String name);

}