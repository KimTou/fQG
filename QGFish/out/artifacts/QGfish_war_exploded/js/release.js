//设置serverUrl
let serverUrl = 'http://localhost:8080/QGfish/'

//获取提交按钮
let form_btn = document.getElementById('submit_btn')


form_btn.onclick = function() {
    let data = {
        productSeller:$.cookie('userId'),
        productName: document.getElementById('product_name').value,
        productKind: document.getElementById('product_kind').value,
        productPrice: document.getElementById('product_price').value,
        productAmount: document.getElementById('product_amount').value
    }

    $.ajax({
        //请求的服务器地址
        url: serverUrl + "user/release",
        //请求方法，请求方法有get，post，put，delete等等
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
                $(window).attr("location", serverUrl + "releasePicture.jsp?productId="+data.data.productId);
            } else {
                alert(data.message)
            }
        }
    })

//     var fileObj = "";
//     var imgData = "";
//
// $("#product_picture").change(function () {
//      // 构造一个文件渲染对象
//      var reader = new FileReader();
//      // 得到文件列表数组
//      fileObj = $(this)[0].files[0];
//      // 拿到文件数据
//      reader.readAsDataURL(fileObj);
//      reader.onload = function() {
//         // 获取文件信息
//         imgData = reader.result;
//         // 显示图片
//         $("#showImg").attr("src", imgData);
//         $("#showImg").show();
//     }
// })
//
//  //给按钮添加点击事件
//  form_btn.onclick = function() {
//      let data = {
//          // productPicture:document.getElementById('product_picture').value,
//          productName: document.getElementById('product_name').value,
//          productKind: document.getElementById('product_kind').value,
//          productPrice: document.getElementById('product_price').value,
//          productAmount: document.getElementById('product_amount').value
//      }
//
//      var formData = new FormData();
//      formData.append("product_picture", $('#product_picture')[0].files[0]);
//     $.ajax({
//             method:"post",
//             timeout:5000,
//             url: serverUrl+"user/releasePic",
//             headers: {
//                 //告诉服务端解析的是json数据
//                 "Content-Type": "application/json"
//             },
//             cache: false,
//             processData: false,
//             contentType: false,
//             data:formData,
//             dataType:'json',
//             success: function(data){
//                 if(data.status == 1) {
//                     alert(data.message)
//                 }
//                 else
//                     alert(data.message)
//             }
//         })
}
