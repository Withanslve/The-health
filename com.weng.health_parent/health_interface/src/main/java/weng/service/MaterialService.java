package weng.service;

import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Material;

public interface MaterialService {
    void add(Material material);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    void edit(Material material);

    Material findById(Integer id);
}
