package com.weng.dao;

import com.github.pagehelper.Page;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Member;

import java.util.List;

public interface MemberDao {
    void add(Member member);

    void edit(Member member);

    Page<Member> selectByCondition(String queryString);

    Member findById(Integer id);

    List<Member> findAll();

    void deleteById(Integer id);

    Integer findMemberCountByMonths(String date);
}
