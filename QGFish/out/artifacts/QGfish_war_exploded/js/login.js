/**
 * let和var都是变量声明的一种方式，两者有作用域上的区别，可以上网了解一下
 * 服务器地址用一个变量进行保存，假如服务器地址变更的时候就不需要一个个请求修改，比较方便
 */
let serverUrl = 'http://localhost:8080/QGfish/'

//获取两个提交按钮
let re_btn = document.getElementById('re')
let lg_btn = document.getElementById('lg')

/**
 * jq ajax版本
 */
re_btn.onclick = function() {
    //获取注册各个输入框的值，存放在data对象中
    let data = {
        userName: document.getElementById('re_username').value,
        password: document.getElementById('re_password').value,
        phone: document.getElementById('re_phone').value,
        email: document.getElementById('re_email').value,
        realName: document.getElementById('re_name').value,
        checkCode:document.getElementById('re_checkCode').value
    }
    $.ajax({
        //请求的服务器地址
        url: serverUrl + "user/register",
        //请求方法，请求方法有get，post，put，delete等等
        method: "POST",
        //请求头部
        headers: {
            //告诉服务端解析的是json数据
            "Content-Type": "application/json"
        },
        /**
         * data是json字符串，你发送的内容需要转变成json字符串，
         * 后台才能够解析，JSON.stringify就是对象变json字符串的方法
         * json是一种轻量的数据交互格式，有利于提高网络传输效率
         * promise
         */
        data: JSON.stringify(data),
        //选择同步请求，还是异步请求
        async: true,
        //请求成功响应后的回调函数
        success: function (data) {           //该data与上面的data不是同一个
            //data是成功回调后后端返回的数据
            if (data.status == 1) {
                alert(data.message)
                $(window).attr("location",serverUrl+"/login.jsp")    //携带用户id
            } else {
                alert(data.message)
            }
        }
    })

}
lg_btn.onclick = function () {
    //获取登陆各个输入框的值，存放在data对象中
    let data = {
        userName: document.getElementById('lg_username').value,
        password: document.getElementById('lg_password').value,
    }
    $.ajax({
        url: serverUrl + "user/login",
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        data: JSON.stringify(data),
        async: true,
        success: function (data) {
            if (data.status == 1) {
                alert(data.message + "\n欢迎进入QG闲鱼:" + data.data.userName);
                if (data.data.userId == 1) {
                    $(window).attr("location", serverUrl + "/managerUsing.jsp") ;   //管理员页面不用id，当然也可以加
                }
                else {
                    $(window).attr("location", serverUrl + "/using.jsp?userId=" + data.data.userId) ;  //携带用户id

                }
            }
            else {
                alert(data.message);
            }
        }
    })
}
