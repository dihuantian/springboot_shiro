$('#register-button').on('click',function () {
    var username = $('#username').val();
    var password = $('#password').val();
    var repetitionPassword = $('#repetitionPassword').val();
    var email = $('#email').val();
    if (password != repetitionPassword){

    }else if ( username == '' || username == undefined) {

    }else if(password == '' || password == undefined){

    }else if(repetitionPassword == '' || repetitionPassword == undefined){

    }else if(email == undefined  || email == ''){

    }else{
        registerSubmit(username,password,email);
    }
});

function registerSubmit(username,password,email) {
    $.ajax({
        type:'POST',
        url:'/Register/RegisterVerification' ,
        data:{username:username,password:password,email:email},
        success:function (data) {

            if (data == 'true'){
                alert('注册成功！');
            }else{
                alert('注册失败！');
            }
        },
        error:function () {

            alert('网路异常，还是服务器异常！');
        }
    });
}