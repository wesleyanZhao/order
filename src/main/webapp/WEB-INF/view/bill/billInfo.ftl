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
<fieldset class="layui-elem-field" >
    <legend>添加菜单</legend>
    <div class="layui-field-box" >
        <form class="layui-form">

            <div class="layui-form-item">
                <label class="layui-form-label">是否需要开发票</label>
                <div class="layui-input-block" >
                    <select  name="tpCd" id="isUsed" >
                        <option value="">请选择</option>
                        <option value="y" selected="">是</option>
                        <option value="n">否</option>

                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">商品价格</label>
                <div class="layui-input-block" id="prdSums">
                    <input type="hidden" id="prdSum" name="prdSum" value="1800"/>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">商品类别</label>
                <div class="layui-input-block">
                    <select  name="tpCd" id="tpCd" >
                        <option value="">请选择</option>
                        <option value="100" selected="">手机</option>
                        <option value="110">游戏机</option>
                        <option value="150">笔记本</option>
                    </select>
                </div>
            </div>


            <div class="layui-form-item">
                <label class="layui-form-label">是否个人</label>
                <div class="layui-input-block" id="isPeople">
                    <#--<input type="radio" id="isPerson" name="isPeople" value="y" title="是" checked>
                    <input type="radio" name="isPeople" value="n" title="否">-->
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">会员编号</label>
                <div class="layui-input-inline" >
                    <input type="text" id="vipNo" name="vipNo"   autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">发票金额</label>
                <div class="layui-input-inline" id="billAmounts">
                    <input type="text" id="billAmount" name="billAmount"   autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">发票抬头</label>
                <div class="layui-input-inline" id="billHeaders">
                    <input type="text" id="billHeader" name="billHeader"    autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">发票税号</label>
                <div class="layui-input-inline" id="billNumbers">
                    <input type="text" id="billNumber" name="billNumber"    autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">发票类型</label>
                <div class="layui-input-block"  id="ctrlRates">
                    <select  name="ctrlRate" id="ctrlRate" >
                        <option value="">请选择</option>
                        <option value="0" selected="">增值税专用发票</option>
                        <option value="1">增值税普通发票</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" id="appendMenu" lay-submit lay-filter="appendMenu">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</fieldset>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script type="text/javascript">

    layui.use(['form','jquery','layer'], function() {
        var $ = layui.jquery;
        var form = layui.form();
        var layer = layui.layer;
        //监听提交
//        form.on('select(selectMenu)', function(){
//
//            layer.msg("dfasdf");
//            //这里可以发起ajax请求进行登录验证
//            var selectMenu=$("#selectMenu").val();
//           // var selectMenu2=$("#selectMenu").[2].val();
//            layer.msg("----------")
//                });
        $("#testss").change(function(){
            console.log("123");
        });



       /* var isUsed=$('#isUsed').val();
        if (isUsed=='n'){
            layer.msg("dsadsa")
//            $("#prdSums").css("disabled","disabled");
//            $("#tpCd").css("disabled","disabled");
//            $("#isPeople").css("disabled", "disabled");
            $("#vipNo").css("disabled",'true');
//            $("#billAmounts").css("disabled", "disabled");
//            $("#billHeaders").css("disabled", "disabled");
//            $("#billNumbers").css("disabled", "disabled");
//            $("#ctrlRates").css("disabled", "disabled");
        };*/



//        $("#isPerson").on('click',function () {
//            $("#billHeader").css("disabled","disabled");
//            $("#billNumber").css("disabled","disabled");
//        });




            //监听提交
       /* form.on('submit(appendMenu)', function(data){
            //layer.msg(JSON.stringify(data.field));
            //这里可以发起ajax请求进行登录验证
            var data=$("form").serializeArray();
            var isUsed=$("#isUsed").val();

            $.ajax({
                url: "/bill/testBill.do",  //后台程序地址
                type: "POST",
                data: data,  //需要post的数据
                success: function (data) {           //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失败
                    if (data.result == 'success'){
                        if (isUsed==null){
                            layer.msg('请求错误');
                            $("appendMenu").css("disabled","disabled");
                        }
                    }else {
                        layer.msg('可以添加');
                    }
                }
            });
            return false;//不通过页面刷新方式提交表单
        });*/

    });
</script>
</body>
</html>