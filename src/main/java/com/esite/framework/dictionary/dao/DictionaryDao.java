package com.esite.framework.dictionary.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.framework.dictionary.entity.DictionaryEntity;

public interface DictionaryDao extends CrudRepository<DictionaryEntity, String>,
    JpaSpecificationExecutor<DictionaryEntity> {

    DictionaryEntity queryByDicCode(String code);

    @Query("from DictionaryEntity dic where dic.parent.id=?1 and dic.dicCode=?2")
    DictionaryEntity queryDictionaryByParentIdAndCode(String parentId, String code);

    @Query("from DictionaryEntity dic where dic.parent.id=?1 and dic.dicName=?2")
    DictionaryEntity queryByParentIdAndName(String parentId, String name);

}