package weng.service;

import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.CheckGroup;
import com.weng.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    Setmeal findById(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);
}
