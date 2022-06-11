package weng.service;

import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.Ord;

public interface OrdService {
    void add(Ord ord, Integer id, String username);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void deleteById(Integer id);
}
