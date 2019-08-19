package com.mokn.istio.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mokn.istio.api.common.JwtUtil;
import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.model.db.User;
import com.mokn.istio.api.model.domain.JsonResult;
import com.mokn.istio.api.model.domain.NamespaceAndNameAndAppRequest;
import com.mokn.istio.api.model.domain.UserLoginRequest;
import com.mokn.istio.api.model.domain.UserLoginResponse;
import com.mokn.istio.api.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<User> list(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                                 @RequestParam(value = "pageSize",required = false) Integer pageSize){
        JsonResult<User> result=new JsonResult<>();
        PageHelper.startPage(pageNo,pageSize);
        User condition=new User();
        PageInfo<User> pageInfo=new PageInfo<>(userService.list(condition));
        return result.success(pageInfo.getList(),pageInfo.getTotal());
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/create")
    public JsonResult cerate(@RequestBody User user){
        JsonResult result=new JsonResult();
        if(user.getUsername()==null || user.getUsername().equals("")){
            return result.fail("用户名不能为空");
        }
        if(user.getPassword()==null || user.getPassword().equals("")){
            return result.fail("密码不能为空");
        }
        if(user.getName()==null || user.getName().equals("")){
            return result.fail("姓名不能为空");
        }
        if(user.getRole()==null || user.getRole().equals("")){
            return result.fail("角色不能为空");
        }

        User condition=new User();
        condition.setUsername(user.getUsername());
        if(userService.list(condition).size()>0){
            return result.fail("用户名已存在");
        }

        user.setCreatedAt(new Date());
        if(userService.create(user)){
            return result.success("创建成功");
        }else{
            return result.fail("创建失败");
        }
    }

    @LoginPassport(valid = true,role = "admin")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestBody NamespaceAndNameAndAppRequest request){
        JsonResult result=new JsonResult();
        if(request.getSysno()==1){
            return result.fail("默认帐号不能删除");
        }
        if(userService.delete(request.getSysno())){
            return result.success("删除成功");
        }else{
            return result.fail("删除失败");
        }
    }

    @LoginPassport
    @PostMapping(value = "/update/password")
    public JsonResult updatePassword(@RequestBody User user,
                                     @RequestHeader(value = "Authorization",required = false) String auth){
        JsonResult result=new JsonResult();
        if(user.getPassword()==null || user.getPassword().equals("")){
            return result.fail("密码不能为空");
        }

        Long sysno=Long.valueOf(jwtUtil.getValueFromToken(auth,"sysno").toString());
        User updateUser=new User();
        updateUser.setSysno(sysno);
        updateUser.setPassword(user.getPassword());
        if(userService.update(updateUser)){
            return result.success("修改成功");
        }else{
            return result.fail("修改失败");
        }
    }

    @PostMapping(value = "/login")
    public JsonResult<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequest){
        JsonResult<UserLoginResponse> result=new JsonResult<>();
        User user=userService.userLogin(loginRequest.getUsername(),loginRequest.getPassword());
        if(user!=null){
            Map<String,Object> claims=new HashMap<String, Object>();
            claims.put("sysno",user.getSysno());
            claims.put("username",user.getUsername());
            claims.put("role",user.getRole());
            UserLoginResponse response=new UserLoginResponse();
            response.setName(user.getName());
            response.setUsername(user.getUsername());
            response.setToken(jwtUtil.createdToken(claims));
            return result.success(response);
        }
        return result.fail("登录失败");
    }
}
