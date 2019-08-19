package com.mokn.istio.api.mapper;

import com.mokn.istio.api.model.db.User;

import java.util.List;

public interface UserMapper {
    public User selectByPrimaryKey(Long sysno);
    public Long deleteByPrimaryKey(Long sysno);
    public Long insert(User user);
    public Long updateByPrimaryKeySelective(User user);
    public List<User> selectList(User condition);
}
