package weng.service;


import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Member;

import java.util.List;

public interface MemberService {

    void add(Member member);

    void edit(Member member);

    PageResult pageQuery(QueryPageBean queryPageBean);

    Member findById(Integer id);

    List<Member> findAll();

    void deleteById(Integer id);

    List<Integer> findMemberCountByMonths(List<String> months);
}
