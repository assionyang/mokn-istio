// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import {router} from './router'
import Vuex from 'vuex'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../static/css/icon.css';
import '../static/css/common.css';
import ECharts from 'vue-echarts/components/ECharts';
import locale from 'element-ui/lib/locale/lang/en';
import 'babel-polyfill';
import $ from 'jquery';

Vue.use(Vuex)
Vue.use(ElementUI)
Vue.use(ECharts)
Vue.filter('time', function (value) {//value为13位的时间戳
  console.log(value);
    if(value==null||value==''){
        return '';
    }else{
        return value.substr(0,10)+" "+value.substr(11,8);
        function add0(m) {
            return m < 10 ? '0' + m : m
        }
        var time = new Date(parseInt(value));
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var f = time.getMinutes();
        var s = time.getSeconds();
    
        return y + '-' + add0(m) + '-' + add0(d)+' '+add0(h)+':'+add0(f)+':'+add0(s);
    }
});
// 字符串转*号
Vue.filter('phone', function (value) {
    if(`${value}`.length > 10){
      const phone = value.substr(3,4);
      value = value.replace(phone,'****');}
    return value;
});
Vue.filter('timeIntercept',value=>{ //截取年份
  value = value.slice(5);
  return value;
});
Vue.filter('toFixed2',value=>{
  value = value.toFixed(2);
  return value;
});
Vue.filter('toFixed8',value=>{
  value = value.toFixed(8);
  return value;
});
Vue.filter('fourNumber',value => {
    value = value.toString();   //将输入的数字转换为字符串
    if(/^-?\d+\.?\d+$/.test(value)){  //判断输入内容是否为整数或小数
      if(/^-?\d+$/.test(value)){    //判断输入内容是否为整数
          value = value + ",00";   //将整数转为精度为2的小数，并将小数点换成逗号
      }else{
          value = value.replace(/\./,',');    //将小数的小数点换成逗号
      }

      while(/\d{4}/.test(value)){ //
          /***
           *判断是否有4个相连的数字，如果有则需要继续拆分，否则结束循环；
           *将4个相连以上的数字分成两组，第一组$1是前面所有的数字（负数则有符号），
           *第二组第一个逗号及其前面3个相连的数字；
           * 将第二组内容替换为“,3个相连的数字，”
           ***/
          value = value.replace(/(\d+)(\d{3}\,)/,'$1,$2');
      }

      value = value.replace(/\,(\d*)$/,'.$1');   //将最后一个逗号换成小数点
    }
    return value
});

// 科学计数法
Vue.filter('toNumber',value => {
    var m = value.toExponential().match(/\d(?:\.(\d*))?e([+-]\d+)/);
    return value.toFixed(Math.max(0, (m[1] || '').length - m[2]));
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})

Vue.filter('getYMD',function(input){
	return input.split(' ')[0];
})