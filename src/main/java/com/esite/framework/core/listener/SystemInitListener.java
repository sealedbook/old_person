package com.esite.framework.core.listener;

import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.esite.framework.core.factory.WebApplicationContextUtil;
import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.organize.service.OrganizeCacheUtil;
import com.esite.framework.organize.service.impl.OrganizeService;
import com.esite.framework.security.service.SystemMenuService;
import com.esite.framework.security.service.ResourceService;

public class SystemInitListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(SystemInitListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SystemMenuService systemMenuService = (SystemMenuService) WebApplicationContextUtil
            .getBean("systemMenuService");
        //init system menus
        systemMenuService.initMenuWithRole();

        ResourceService resourceService = (ResourceService) WebApplicationContextUtil.getBean("resourceService");
        //init global resource
        resourceService.initGlobalResource();

        OrganizeService organizeService = (OrganizeService) WebApplicationContextUtil.getBean("organizeService");
        Iterable<OrganizeEntity> allOrganize = organizeService.getAllOrganizeEntity();
        OrganizeCacheUtil organizeCacheUtil = OrganizeCacheUtil.getInstance();
        Iterator<OrganizeEntity> iterator = allOrganize.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            OrganizeEntity organizeEntity = iterator.next();
            organizeCacheUtil.setOrganize(organizeEntity.getId(), organizeEntity);
            ++count;
        }
        logger.info("======Organize缓存,总数:" + count + ".");

    }

}
