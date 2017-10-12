package com.esite.framework.dictionary.controller;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.dictionary.dao.DictionaryDao;
import com.esite.framework.dictionary.entity.DictionaryEntity;
import com.esite.framework.dictionary.service.DictionaryService;
import com.esite.framework.util.PagerRequest;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping("/{code}/sub")
	public @ResponseBody List<DictionaryEntity> loadSubDictionary(@PathVariable String code) {
		List<DictionaryEntity> dictionaryEntityCollection = dictionaryService.getSubDictionaryByParentCode(code);
		return dictionaryEntityCollection;
	}

	@RequestMapping("/{parentId}/{code}")
	public @ResponseBody DictionaryEntity loadDictionary(@PathVariable String parentId,@PathVariable String code) {
		DictionaryEntity dictionaryEntity = dictionaryService.getDictionaryByParentIdAndCode(parentId,code);
		return dictionaryEntity;
	}
	
	
	@Autowired
	private DictionaryDao dictionaryDao;

}
