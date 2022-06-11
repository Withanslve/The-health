package weng.service;

import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.User;

public interface UserService {

    User findByUsername(String username);

    void editPassword(User user);

    PageResult pageQuery(QueryPageBean queryPageBean);
}
