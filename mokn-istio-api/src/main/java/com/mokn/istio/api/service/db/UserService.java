package com.mokn.istio.api.service.db;

import com.mokn.istio.api.model.db.User;

import java.util.List;

public interface UserService {
    public User userLogin(String username, String password);
    public boolean create(User user);
    public boolean delete(Long sysno);
    public boolean update(User user);
    public User get(Long sysno);
    public List<User> list(User condition);
}
