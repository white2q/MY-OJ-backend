package com.ppf.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ppf.oj.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库操作
 *
 * @author <a href="https://github.com/white2q">ppf</a>
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




