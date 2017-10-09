package com.esite.framework.organize.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;

import com.esite.framework.organize.dao.OrganizeDao;
import com.esite.framework.organize.dao.OrganizeViewDao;
import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.organize.service.OrganizeCacheUtil;
import com.esite.framework.organize.service.OrganizeService;
import com.esite.framework.util.StringHelper;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.service.IOldPersonService;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.IOperatorService;

public class OrganizeServiceImpl implements OrganizeService {
	@Autowired
	private OrganizeViewDao organizeViewDao;
	
	@Autowired
	private OrganizeDao organizeDao;
	
	@Autowired
	private IOldPersonService oldPersonService;
	
	@Autowired
	private IOperatorService operatorService;

	@Override
	public List<OrganizeViewEntity> loadTreeByParentId(String parentId) {
		if(StringHelper.isEmpty(parentId)) {
			return this.organizeViewDao.queryByParentIdIsNull();
		}
		return this.organizeViewDao.queryByParentId(parentId);
	}

	@Override
	public List<OrganizeEntity> loadTreeByParentIdEntity(String parentId) {
		if(StringHelper.isEmpty(parentId)) {
			return this.organizeDao.queryByParentIdIsNull();
		}
		return this.organizeDao.queryByParentId(parentId);
	}
	
	@Override
	public OrganizeViewEntity getOrganizeById(String id) {
		if(StringHelper.isNotEmpty(id)) {
			id = id.trim();
		}
		return this.organizeViewDao.findOne(id);
	}

	@Override
	public List<OrganizeViewEntity> loadAllTreeById(String id) {
		OrganizeViewEntity organize = getOrganizeById(id);
		organize.setChecked(true);
		return initOrganize(organize);
	}
	
	private List<OrganizeViewEntity> initOrganize(OrganizeViewEntity organize) {
		List<OrganizeViewEntity> list = new ArrayList<OrganizeViewEntity>();
		if(!organize.isRoot()) {
			OrganizeViewEntity parent = organize.getParent();
			List<OrganizeViewEntity> sub = loadTreeByParentId(parent.getId());
			for(OrganizeViewEntity org : sub) {
				if(!org.equals(organize)) {
					parent.getChildren().add(org);
				} else {
					parent.getChildren().add(organize);
				}
			}
			return initOrganize(parent);
		}
		list.add(organize);
		return list;
	}

	@Override
	public OrganizeViewEntity getCode(String parentId, String name) {
		return this.organizeViewDao.getCode(parentId, name);
	}

	@Override
	public Page<OrganizeViewEntity> findOrganizeLikeName(Pageable instance, String name) {
		return this.organizeViewDao.findByNameLike(name,instance);
	}

	@Override
	public OrganizeViewEntity getOrganizeByName(String name) {
		return this.organizeViewDao.queryByName(name);
	}

	@Override
	public void addNewOrganize(OrganizeEntity organize) {
		try {
			this.organizeDao.save(organize);
		} catch(JpaSystemException e) {
			throw new IllegalArgumentException("当前区域下已经有一个名为【" + organize.getName() + "】的区域.");
		}
		
	}

	@Override
	public void edit(OrganizeEntity organize) {
		try {
			this.organizeDao.save(organize);
		} catch(JpaSystemException e) {
			throw new IllegalArgumentException("当前区域下已经有一个名为【" + organize.getName() + "】的区域.");
		}
	}
	
	@Override
	public void remove(String id) {
		long oldPersonCount = oldPersonService.count(id);
		if(oldPersonCount > 0) {
			throw new IllegalArgumentException("操作失败,所选的地区下有" + oldPersonCount + "位老年人,请先将这些老年人转到其他地区.");
		}
		long operatorCount = operatorService.count(id);
		if(operatorCount > 0) {
			throw new IllegalArgumentException("操作失败,所选的地区下有" + operatorCount + "位操作员,请先将这些操作员转到其他地区.");
		}
		OrganizeEntity organizeEntity = this.organizeDao.findOne(id);
		organizeEntity.setStatus("del");
		this.organizeDao.save(organizeEntity);
	}

	@Override
	public List<OrganizeViewEntity> getOrganizeByIdArray(String[] idArray) {
		if(null == idArray || idArray.length <= 0) {
			return null;
		}
		List<OrganizeViewEntity> list = new ArrayList<OrganizeViewEntity>(idArray.length);
		for(String organizeId : idArray) {
			list.add(this.getOrganizeById(organizeId));
		}
		return list;
	}

	@Override
	public void restore(String id) {
		OrganizeEntity organizeEntity = this.organizeDao.findOne(id);
		organizeEntity.setStatus("");
		this.organizeDao.save(organizeEntity);
	}

	@Override
	public Iterable<OrganizeViewEntity> loadAllTreeByIdArray(String[] idArray) {
		List<OrganizeViewEntity> orgList = this.organizeViewDao.queryByParentIdIsNull();
		setCheckStatus(orgList,idArray);
		return orgList;
	}

	private void setCheckStatus(List<OrganizeViewEntity> orgList,String[] idArray) {
		for(OrganizeViewEntity org : orgList) {
			if(null != org.getChildren() && org.getChildren().size() > 0) {
				setCheckStatus(org.getChildren(),idArray);
			} else {
				for(String id : idArray) {
					if(id.equals(org.getId())) {
						org.setChecked(true);
					}
				}
			}
		}
	}

	@Override
	public OrganizeViewEntity getRootOrganize() {
		return this.getOrganizeById("1");
	}

	@Override
	public List<OrganizeEntity> getOrganizeEntityByIdArray(String[] idArray) {
		if(null == idArray || idArray.length <= 0) {
			return null;
		}
		//TODO 加缓存
		List<OrganizeEntity> list = new ArrayList<OrganizeEntity>(idArray.length);
		for(String organizeId : idArray) {
			OrganizeEntity org = OrganizeCacheUtil.getInstance().getOrganize(organizeId);
			if(null == org) {
				org = this.organizeDao.findOne(organizeId);
			}
			
			list.add(org);
		}
		return list;
	}

	@Override
	public Iterable<OrganizeEntity> getAllOrganizeEntity() {
		return organizeDao.findAll();
	}

	@Override
	public OrganizeEntity getOrganizeByIdForEntity(String id) {
		if(StringHelper.isNotEmpty(id)) {
			id = id.trim();
		}
		return this.organizeDao.findOne(id);
	}

}
