import Mock from 'mockjs'
const LoginUsers = [
  {
    id: 1,
    username: 'admin',
    password: '123456',
    avatar: 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
    name: '杨春来',
    menus:[
                           {
      id:1,
      name:'Dashboard',
      path:'dashboard',
      icon:'el-icon-date',
      title:'Dashboard',
      children:[]
  },
  {
      id:3,
      name:'Notfound',
      path:'notfound',
      icon:'el-icon-menu',
      title:'404页',
      children:[]
  },
  {
      id:4,
      name:'Login',
      path:'login',
      icon:'el-icon-circle-check',
      title:'登录页',
      children:[]
  },
  {
      id:5,
      name:'System',
      path:'system',
      icon:'el-icon-setting',
      title:'系统管理',
      children:[
          {
            id:6,
            name:'Department',
            path:'department',
            icon:'',
            title:'部门管理',
            children:[]
          },
          {
            id:7,
            name:'Employee',
            path:'employee',
            icon:'',
            title:'员工管理',
            children:[]
          },
          {
            id:8,
            name:'User',
            path:'user',
            icon:'',
            title:'帐号管理',
            children:[]
          },
          {
            id:9,
            name:'Role',
            path:'role',
            icon:'',
            title:'角色管理',
            children:[]
          }
      ]
      }
    ]
  }
]

const LoginUsers2 = [
  {
    id: 1,
    username: 'guest',
    password: '123456',
    avatar: 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
    name: '杨春来2',
    menus:[
       {
          id:1,
          name:'Dashboard',
          path:'dashboard',
          icon:'el-icon-date',
          title:'Dashboard',
          children:[]
       },
       {
      id:5,
      name:'System',
      path:'system',
      icon:'el-icon-setting',
      title:'系统管理',
      children:[
          {
            id:6,
            name:'Department',
            path:'department',
            icon:'',
            title:'部门管理',
            children:[]
          },
          {
            id:7,
            name:'Employee',
            path:'employee',
            icon:'',
            title:'员工管理',
            children:[]
          },
          {
            id:8,
            name:'User',
            path:'user',
            icon:'',
            title:'帐号管理',
            children:[]
          },
          {
            id:9,
            name:'Role',
            path:'role',
            icon:'',
            title:'权限管理',
            children:[]
          }
      ]
      }
    ]
  }
 ]

const Departments=[
    {
    	sysno:1,
        parent_sysno:0,
        departmentname:'上海国烨跨境电子商务有限公司',
        status:1,
        isdel:false,
        version:Mock.Random.guid(),
        created_at:Mock.Random.date(),
        updated_at:Mock.Random.date()
    },
    {
    	sysno:2,
        parent_sysno:1,
        departmentname:'IT中心',
        status:1,
        isdel:false,
        version:Mock.Random.guid(),
        created_at:Mock.Random.date(),
        updated_at:Mock.Random.date()
    },
    {
    	sysno:3,
        parent_sysno:1,
        departmentname:'运营中心',
        status:1,
        isdel:false,
        version:Mock.Random.guid(),
        created_at:Mock.Random.date(),
        updated_at:Mock.Random.date()
    },
    {
    	sysno:4,
        parent_sysno:2,
        departmentname:'研发部',
        status:1,
        isdel:false,
        version:Mock.Random.guid(),
        created_at:Mock.Random.date(),
        updated_at:Mock.Random.date()
    }
];

const Employees = [];

for (let i = 0; i < 123; i++) {
    Employees.push(Mock.mock({
    sysno: i+1,
    department_sysno:1,
    departmentname:'IT中心',
    employeeno:'E00000001',
    employeephoto: 'http://pic.chinayie.com/cdn/headphoto.png',
    employeename: Mock.Random.cname(),
    'employeeage|18-60': 18,
    employeesex: Mock.Random.integer(0, 1),
    employeehiredate: Mock.Random.date(),
    employeejobtitle:'当上CEO',
    'status|0-1':1,
    isdel:false,
    version:Mock.Random.guid(),
    created_at:Mock.Random.date(),
    updated_at:Mock.Random.date()
  }));
}

const Users=[];

for(let i=0;i<50;i++){
    Users.push(Mock.mock({
        sysno:i+1,
        employee_sysno:1,
        employeename:Mock.Random.cname(),
        username:'username',
        password:'password',
        lastlogintime:Mock.Random.date(),
        'status|0-1':1,
        isdel:false,
        version:Mock.Random.guid(),
        created_at:Mock.Random.date(),
        updated_at:Mock.Random.date()
    }));
}

export {Departments,Employees,Users,LoginUsers,LoginUsers2};

