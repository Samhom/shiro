$().ready(function () {
    $("#login_form").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 6
            }
        },
        messages: {
            username: "请输入姓名",
            password: {
                required: "请输入密码",
                minlength: $.validator.format("密码不能小于6个字符")
            }
        }
    });

    $("#register_form").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 6
            },
            rpassword: {
                equalTo: "#register_password"
            }
        },
        messages: {
            username: "请输入姓名",
            password: {
                required: "请输入密码",
                minlength: $.validator.format("密码不能小于6个字符")
            },
            rpassword: {
                equalTo: "两次密码不一样"
            }
        }
    })
});

$(function () {
    $("#register_btn").click(function () {
        $("#register_form").css("display", "block");
        $("#login_form").css("display", "none");
    });

    $("#back_btn").click(function () {
        $("#login_form").css("display", "block");
        $("#register_form").css("display", "none");
    });
});