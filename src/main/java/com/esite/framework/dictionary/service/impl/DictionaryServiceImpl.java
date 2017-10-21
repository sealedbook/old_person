package com.esite.framework.dictionary.service.impl;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.dictionary.dao.DictionaryDao;
import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;

public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryDao dictionaryDao;

    private Cache dictionaryCache;

    public void setDictionaryCache(Cache dictionaryCache) {
        this.dictionaryCache = dictionaryCache;
    }

    @Override
    @Transactional
    public List<DictionaryEntity> getSubDictionaryByParentCode(String code) {

//		DictionaryEntity d = new DictionaryEntity();
//		d.setDicCode("my_diccode");
//		d.setDicName("name");
//		dictionaryDao.save(d);
//		int a = 1/0;

        DictionaryEntity dictionary = getDictionaryByCode(code);
        if (null != dictionary) {
            return dictionary.getSubDictionary();
        }
        return null;
    }


    private DictionaryEntity getDictionaryByCode(String code) {
        String CACHE_KEY = "DICTIONARY_CACHE_KEY_" + code;
        Element element = dictionaryCache.get(CACHE_KEY);
        if (null == element) {
            DictionaryEntity dic = dictionaryDao.queryByDicCode(code);
            if (null != dic) {
                dictionaryCache.put(new Element(CACHE_KEY, dic));
                return dic;
            }
        } else {
            Object obj = element.getObjectValue();
            if (obj instanceof DictionaryEntity) {
                return (DictionaryEntity) obj;
            }
        }
        return null;
    }

    @Override
    public DictionaryEntity getDictionaryByParentIdAndCode(String parentId, String code) {
        String CACHE_KEY = "DICTIONARY_CACHE_KEY_" + parentId + "_" + code;
        Element element = dictionaryCache.get(CACHE_KEY);

        if (null == element) {
            DictionaryEntity dic = dictionaryDao.queryDictionaryByParentIdAndCode(parentId, code);
            if (null != dic) {
                dictionaryCache.put(new Element(CACHE_KEY, dic));
                return dic;
            }
        } else {
            Object obj = element.getObjectValue();
            if (obj instanceof DictionaryEntity) {
                return (DictionaryEntity) obj;
            }
        }
        return null;
    }

    @Override
    public void flashDictionary() {
        dictionaryCache.removeAll();
    }

    @Override
    public DictionaryEntity getDictionaryByParentIdAndName(String parentId, String name) {
        return this.dictionaryDao.queryByParentIdAndName(parentId, name);
    }
}
