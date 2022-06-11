package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weng.dao.MemberDao;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Member;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import weng.service.MemberService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public void add(Member member) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        Date parse = null;
        try {
            parse = df.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        member.setRegTime(parse);
        memberDao.add(member);
    }

    @Override
    public void edit(Member member) {
        memberDao.edit(member);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //查询条件
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Member> page = memberDao.selectByCondition(queryString);
        long total = page.getTotal(); //总记录数
        List<Member> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public Member findById(Integer id) {
        return memberDao.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        memberDao.deleteById(id);
    }

    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> allCount = new ArrayList<>();
        for (String month : months) {
            String date = month + "-31";
            Integer sigleMonthCount = memberDao.findMemberCountByMonths(date);
            allCount.add(sigleMonthCount);
        }
        return allCount;

    }


}
