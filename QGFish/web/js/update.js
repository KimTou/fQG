//设置serverUrl
let serverUrl = 'http://localhost:8080/QGfish/'

//获取提交按钮
let form_btn = document.getElementById('submit_btn')


//给按钮添加点击事件
form_btn.onclick = function() {
    let data = {
        userId:document.getElementById('user_id').value,
        userName: document.getElementById('user_name').value,
        email: document.getElementById('user_email').value,
        phone: document.getElementById('user_phone').value,
        realName: document.getElementById('user_realName').value
    }

    $.ajax({
        //请求的服务器地址
        url: serverUrl + "user/update",
        method: "POST",
        //请求头部
        headers: {
            //告诉服务端解析的是json数据
            "Content-Type": "application/json"
        },
        data: JSON.stringify(data),
        //选择同步请求，还是异步请求
        async: true,
        //请求成功响应后的回调函数
        success: function (data) {           //该data与上面的data不是同一个
            //data是成功回调后后端返回的数据
            if (data.status == 1) {
                alert(data.message)
                //继续定位user的id
                $(window).attr("location", serverUrl + "/using.jsp?userId="+data.data.userId);
            } else {
                alert(data.message)
            }
        }
    })
}
