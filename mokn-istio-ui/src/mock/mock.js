import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import {Departments,Employees,LoginUsers,LoginUsers2, Users} from './data/system';
let _Users = Users;
let _Departments=Departments;
let _Employees=Employees;

export default {
  /**
   * mock bootstrap
   */
  bootstrap() {
    let mock = new MockAdapter(axios);

    // mock success request
    mock.onGet('/success').reply(200, {
      msg: 'success'
    });

    // mock error request
    mock.onGet('/error').reply(500, {
      msg: 'failure'
    });


    //登录
    mock.onPost('/login').reply(config => {
      let {username, password} = JSON.parse(config.data);
      return new Promise((resolve, reject) => {
        let user = null;
        setTimeout(() => {
          let hasUser = false;
          hasUser= LoginUsers.some(u => {
            if (u.username === username && u.password === password) {
              user = JSON.parse(JSON.stringify(u));
              user.password = undefined;
              return true;
            }
          });

          if(!hasUser){
            hasUser= LoginUsers2.some(u => {
            if (u.username === username && u.password === password) {
              user = JSON.parse(JSON.stringify(u));
              user.password = undefined;
              return true;
            }
            });
          }

          if (hasUser) {
            resolve([200, { code: 200, msg: '请求成功', user }]);
          } else {
            resolve([200, { code: 500, msg: '账号或密码错误' }]);
          }
        }, 1000);
      });
    });


    //获取部门树形数据
    mock.onGet('/system/department/list').reply(config => {
      let deps=depchilren(_Departments,0,'无');
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            departments: deps
          }]);
        }, 1000);
      });
    });

    //生成部门数据树递归方法
    function depchilren(data,pid,name){
      var result=[],temp;
      for(var i in data){
        if(data[i].parent_sysno==pid){
          data[i].label=data[i].departmentname;
          data[i].id=data[i].sysno;
          data[i].parent_departmentname=name;
          result.push(data[i]);
          temp=depchilren(data,data[i].sysno,data[i].departmentname);
          if(temp.length>0){
            data[i].children=temp;
          }
        }
      }
      return result;
    }
    //新增部门
    mock.onPost('/system/departments/add').reply(config => {
      let { sysno, parent_sysno, departmentname, status, isdel,version,created_at,updated_at } = JSON.parse(config.data);
      _Departments.push({
        sysno: sysno,
        parent_sysno: parent_sysno,
        departmentname: departmentname,
        status: status,
        isdel: isdel,
        version:version,
        created_at:created_at,
        updated_at:updated_at
      });
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '新增成功'
          }]);
        }, 500);
      });
    });
    //删除部门
    mock.onGet('/system/departments/remove').reply(config => {
      let { sysno } = config.params;
      let deps=[];
      _Departments.forEach(function(d){
        if(d.sysno!=sysno){

          deps.push(d);
        }
      });
      
      _Departments = deps;

      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '删除成功'
          }]);
        }, 500);
      });
    });
     //修改部门
    mock.onPost('/system/departments/edit').reply(config => {
      let { sysno, parent_sysno, departmentname, status, isdel,version,created_at,updated_at } = JSON.parse(config.data);
      _Departments.some(d=>{
        if(d.sysno===sysno){
          d.departmentname=departmentname,
          d.parent_sysno=parent_sysno,
          d.status=status,
          d.isdel=isdel,
          d.version=version,
          d.updated_at=updated_at
        }
      });

      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '修改成功'
          }]);
        }, 500);
      });
    });

    //获取员工列表（分页）
    mock.onGet('/system/employee/list').reply(config=>{
      let {currentPage,pageSize,employeename,employeesex,created_at,status}=config.params;
      let mockEmployees=_Employees.filter(employee=>{
        if(employeename && employee.employeename.indexOf(employeename)===-1) return false;
        if(employeesex!==-1 && employeesex!=='' && employee.employeesex!==employeesex) return false;
        if(status!==-1 && status!=='' && employee.status!==status) return false;
        if(created_at!==null && (created_at[0]>=employee.created_at || created_at[1]<=employee.created_at)) return false;
        return true;
      });
      let total=mockEmployees.length;
      mockEmployees=mockEmployees.filter((e,index)=>index<pageSize*currentPage && index >=pageSize * (currentPage-1));
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            total: total,
            employees: mockEmployees
          }]);
        }, 1000);
      });
    });

    //新增员工
    mock.onPost('/system/employee/add').reply(config=>{
      let {sysno,department_sysno,departmentname,employeeno,employeephoto,employeename,employeeage,employeesex,employeehiredate,employeejobtitle,
          status,isdel,version,created_at,updated_at}=JSON.parse(config.data);

          console.log(employeehiredate);
      _Employees.push({
        sysno:sysno,
        department_sysno:department_sysno,
        employeeno:employeeno,
        employeephoto:employeephoto,
        employeename:employeename,
        employeeage:employeeage,
        employeesex:employeesex,
        employeehiredate:employeehiredate,
        employeejobtitle:employeejobtitle,
        status:status,
        isdel:isdel,
        version:version,
        created_at:created_at,
        updated_at:updated_at
      });



      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '新增成功'
          }]);
        }, 500);
      });
    });

    //删除员工
    mock.onGet('/system/employee/remove').reply(config=>{
      let {sysno}=config.params;
      _Employees=_Employees.filter(e=>e.sysno!==sysno);
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '删除成功'
          }]);
        }, 500);
      });
    });

    //批量删除员工
    mock.onGet('/system/employee/batchremove').reply(config=>{
      let {sysnos} = config.params;
      sysnos=sysnos.split(',');
      let mockEmployees=[];
      _Employees.forEach(function(e){
         let isadd=true;
         sysnos.forEach(function(s){
           if(s==e.sysno){
            isadd=false;
           }
         });
         if(isadd){
           mockEmployees.push(e);
         }
      });
      _Employees=mockEmployees;
      //_Employees=_Employees.filter(e=>!sysnos.includes(e.sysno));
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '删除成功'
          }]);
        }, 500);
      });
    });

    //修改员工
    mock.onPost('/system/employee/edit').reply(config=>{
      let {sysno,department_sysno,departmentname,employeeno,employeephoto,employeename,employeeage,employeesex,employeehiredate,employeejobtitle,
          status,isdel,version,created_at,updated_at}=JSON.parse(config.data);
      _Employees.push({
        sysno:sysno,
        department_sysno:department_sysno,
        employeeno:employeeno,
        employeephoto:employeephoto,
        employeename:employeename,
        employeeage:employeeage,
        employeesex:employeesex,
        employeehiredate:employeehiredate,
        employeejobtitle:employeejobtitle,
        status:status,
        isdel:isdel,
        version:version,
        created_at:created_at,
        updated_at:updated_at
      });
      _Employees.some(e=>{
        if(e.sysno===sysno){
          e.department_sysno=department_sysno,
          e.employeeno=employeeno,
          e.employeephoto=employeephoto,
          e.employeename=employeename,
          e.employeeage=employeeage,
          e.employeesex=employeesex,
          e.employeehiredate=employeehiredate,
          e.employeejobtitle=employeejobtitle,
          e.status=status,
          e.isdel=isdel,
          e.version=version,
          e.updated_at=updated_at
        }
      });

      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            code: 200,
            msg: '编辑成功'
          }]);
        }, 500);
      });
    });


    //获取用户列表（分页）
    mock.onGet('/system/user/list').reply(config => {
      let {employeename,username,status,currentPage,pageSize}=config.params;
      let mockUsers=_Users.filter(user=>{
        if(employeename && user.employeename.indexOf(employeename)===-1) return false;
        if(username && user.username!==username) return false;
        if(status!==-1 && status!=='' && user.status!==status) return false;
        return true;
      });
      let total=mockUsers.length;
      mockUsers=mockUsers.filter((e,index)=>index<pageSize*currentPage && index>=pageSize * (currentPage-1));
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([200, {
            total: total,
            users: mockUsers
          }]);
        }, 1000);
      });
    });

  }
};