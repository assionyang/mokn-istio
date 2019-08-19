package com.mokn.istio.api.service.db;

import com.mokn.istio.api.mapper.UserMapper;
import com.mokn.istio.api.model.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(String username, String password) {
        User condition=new User();
        condition.setUsername(username);
        condition.setPassword(password);
        List<User> users=userMapper.selectList(condition);
        if(users==null || users.size()!=1){
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean create(User user) {
        return userMapper.insert(user)>0;
    }

    @Override
    public boolean delete(Long sysno){
        return userMapper.deleteByPrimaryKey(sysno)>0;
    }

    @Override
    public boolean update(User user){
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    @Override
    public User get(Long sysno) {
        return userMapper.selectByPrimaryKey(sysno);
    }

    @Override
    public List<User> list(User condition) {
        return userMapper.selectList(condition);
    }
}
