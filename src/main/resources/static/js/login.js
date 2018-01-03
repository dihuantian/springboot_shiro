/**
 * Created by EumJi on 2017/4/9.
 */

$('#login-button').on('click',function () {
    loginSubmit();
});

function loginSubmit(){

    var username = $("#username").val();
    var password = $("#password").val();
    if (username.length > 0 && password.length > 0) {
        $.ajax({
            type: "POST",
            url: '/Login/LoginVerification',
            data: {username: username, password: password},
            success: function (data) {///去更新cookies
                if (data == "true")
                    window.location.href='/Home/Index'
                else{
                    alert("登录失败！");
                }
            },
            error:function (data) {
                alert("服务器错误或者没有网络！")
            }
        });
    } else {
        layer.alert('账号或者密码不能为空', {
            icon: 2,
        })
    }
}

///忘记密码
$("#iforget").click(function () {
    $("#login_model").hide();
    $("#forget_model").show();

});

//返回
$("#denglou").click(function () {
    $("#usrmail").val("");
    $("#username").val("");
    $("#userpwd").val("");
    $("#login_model").show();
    $("#forget_model").hide();

});