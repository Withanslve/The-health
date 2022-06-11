package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weng.dao.MaterialDao;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Material;
import org.springframework.beans.factory.annotation.Autowired;
import weng.service.MaterialService;
import weng.service.MemberService;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = MaterialService.class)
public class MaterialServiceimpl implements MaterialService {
    @Autowired
    private MaterialDao materialDao;
    @Override
    public void add(Material material) {
        material.setCreateTime(new Date());
        materialDao.add(material);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage, pageSize);
        Page<Material> page = materialDao.selectByCondition(queryString);
        long total = page.getTotal(); //总记录数
        List<Material> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public void deleteById(Integer id) {
        materialDao.deleteById(id);
    }

    @Override
    public void edit(Material material) {
        material.setUpdateTime(new Date());
        Date createTime = material.getCreateTime();
        material.setCreateTime(createTime);
        materialDao.edit(material);
    }

    @Override
    public Material findById(Integer id) {
        return materialDao.findById(id);
    }
}
