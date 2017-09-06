<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>商品管理系统 惠买ivalue后台管理</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>

<body>
<!-- 布局容器 -->
<div class="layui-layout layui-layout-admin">
    <!-- 头部 -->
    <div class="layui-header">
        <div class="layui-main">
            <!-- logo -->
            <a href="/" style="color: #c2c2c2; font-size: 18px; line-height: 60px;">商品管理系统</a>
            &nbsp;<button id="showLeftnaviBar" class="layui-btn layui-btn-mini" title="隐藏/显示" >〓</button>
            <!-- 水平导航 -->
            <ul class="layui-nav" style="position: absolute; top: 0; right: 0; background: none;">
                <!-- <li class="layui-nav-item">
                    <a href="javascript:;">
                        进入前台
                    </a>
                </li> -->
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <span>${loginUser}</span>
                    </a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a  id="userInfo" href="javascript:;">
                                个人信息
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:;" id="alterPwd">
                                修改密码
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:;" id="exit" data-method="notice" class="layui-btn">
                                退出登录
                            </a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <!-- 侧边栏 -->
    <div id="leftsidenavi" class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="left-nav" style="border-radius: 0;">
            </ul>
        </div>
    </div>

    <!-- 主体 -->
    <div class="layui-body">
        <!-- 顶部切换卡 -->
        <div class="layui-tab layui-tab-brief" lay-filter="top-tab" lay-allowClose="true" style="margin: 0;">
            <ul class="layui-tab-title"></ul>
            <div class="layui-tab-content"></div>
        </div>
    </div>

    <!-- 底部 -->
    <div class="layui-footer" style="text-align: center; line-height: 44px;">
        Copyright © 2017 <a href="http://www.lanou3g.com" target="_blank">lanou</a> Powered by lanou
    </div>
</div>

<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script type="text/javascript">
    /**
     * 对layui进行全局配置
     */
    layui.config({
        base: '/resources/js/'
    });

    /**
     * 初始化整个lanou框架
     */
    layui.use('lanou', function() {
        var lanou = layui.lanou('left-nav', 'top-tab');
        var $ = layui.jquery;

        // 首页初始化加载左侧导航栏数据
        // 将后台传回的String类型字符串转换成jsonarray
        var array = JSON.parse('${result}');// 注意这里一定是但引号，因为后台传过来的json字符串是双引号的

        lanou.addNav(array, 0, 'id', 'pid', 'node', 'url');

        lanou.bind(60 + 41 + 20 + 44); //头部高度 + 顶部切换卡标题高度 + 顶部切换卡内容padding + 底部高度

        lanou.clickLI(0);

        //隐藏、显示左侧菜单栏
        $("#showLeftnaviBar").on("click", function(){
            if ($('.layui-side').width() == 200) {
                $('.layui-body').animate({
                    left: '0'
                });
                $('.layui-side').animate({
                    width: '0'
                });
            } else {
                $('.layui-body').animate({
                    left: '200px'
                });
                $('.layui-side').animate({
                    width: '200px'
                });
            }
        });
        //个人信息弹出窗口
        $('#userInfo').on('click', function(){
            layer.open({
                title: '个人信息'
                ,area: ['500px', '400px']
                ,type: 2 //content内容为一个连接
                ,content: '/user/getuserInfo.do?loginUser=' + "${loginUser}"
            });
        });
        $('#alterPwd').on('click', function(){
            layer.open({
                title: '修改密码'
                ,area: ['400px', '500px']
                ,type: 2 //content内容为一个连接
                ,content: '/user/alterPwd.do?loginUser=' + "${loginUser}"
            });
        });
        $('#exit').on('click', function(){
            layer.confirm('是否退出?', function(index){
                //do something
                window.location.href = "/base/login.do";
                layer.close(index);
            });
        });
    });
</script>
</body>
</html>