<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="wesleyan">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>登录 - 惠买ivalue管理系统</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>

<body style="background-color: #f5f5f5;">
<fieldset class="layui-elem-field" style="width: 380px; margin:0 auto; margin-top: 10%; box-shadow: 0 0 10px #d9edf7;">
    <legend>
        登录
    </legend>
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">
                账号
            </label>
            <div class="layui-input-inline">
                <input type="text" name="username" required lay-verify="required" placeholder="请输入账号" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">
                密码
            </label>
            <div class="layui-input-inline">
                <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="loginForm">
                    登录
                </button>
                <button type="button" id="df_register" class="layui-btn layui-btn-primary">
                    注册
                </button>
            </div>
        </div>
    </form>
</fieldset>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script type="text/javascript">
    /**
     * 对layui进行全局配置
     */
    layui.config({
        base: '/resources/js/'
    });

    layui.use(['form','jquery'], function() {
        var $ = layui.jquery;
        var form = layui.form();
        //监听提交
        form.on('submit(loginForm)', function(data){
            //layer.msg(JSON.stringify(data.field));
            //这里可以发起ajax请求进行登录验证
            $.ajax({
                url: "/base/loginForm.do",  //后台程序地址
                type: "POST",
                data: data.field,  //需要post的数据
                success: function (msg) {           //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失败
                    if (msg.result == "success") {   //如果成功了
                        window.location.href = "/index/home.do?loginUser="+msg.loginUser;
                    } else {
                        layer.msg(msg.message);
                    }
                }
            });
            return false;
        });

        //修正登录框margin
        var fieldset = layui.jquery("fieldset").eq(0);
        fieldset.css("margin-top", (layui.jquery(window).height() - fieldset.height()) * 0.3 + "px");

        // 鉴定注册按钮
        $('#df_register').on('click', function(){
            layer.open({
                title: '注册 - 惠买ivalue管理系统'
                ,area: ['400px', '500px']
                ,type: 2 //content内容为一个连接
                ,content: '/base/register.do'
            });
        });
    });
</script>
</body>
</html>