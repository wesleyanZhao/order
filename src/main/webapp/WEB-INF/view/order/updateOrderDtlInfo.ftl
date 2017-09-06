<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<body>
<fieldset class="layui-elem-field" >
    <legend>订单明细操作</legend>
  <div class="layui-field-box" >
        <form class="layui-form" >
            <input type="hidden" name="id" value="${params.id!''}">
            <div class="layui-form-item">
                <label class="layui-form-label">会员编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.vipNo!''}" name="vipNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">订单编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.ordNo!''}" name="ordNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">订单明细编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.ordDtlNo!''}" name="ordDtlNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.prdNo!''}" name="prdNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品明细编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.prdDtlNo!''}" name="prdDtlNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">成本价格</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.ordSum!''}" name="ordSum"   placeholder=" " autocomplete="off" class="layui-input" disabled >
                </div>
            </div>

            <div  class="layui-inline">
                <label class="layui-form-label"> 是否支持退换货</label>
                <div class="layui-input-inline">
                    <select id="isReturn" name="isReturn" >
                        <option value="y">支持</option>
                        <option value="n">不支持</option>

                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">销售价格</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.saleSum!''}" name="saleSum"   placeholder=" " autocomplete="off" class="layui-input" >
                </div>
            </div>


            <div class="layui-form-item">
                <label class="layui-form-label">企划编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.spNo!''}" name="spNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">余额支付</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.cashAmt!''}" name="cashAmt"   placeholder=" " autocomplete="off" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">积分支付</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.igAmt!''}" name="igAmt"   placeholder=" " autocomplete="off" class="layui-input" >
                </div>
            </div>

            <div  class="layui-inline">
                <label class="layui-form-label">是否可用</label>
                <div class="layui-input-inline">
                    <select id="isUsed" name="isUsed" >
                        <option value="y">可用</option>
                        <option value="n">不可用</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="updateOrderDtlInfoForm">确定</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</fieldset>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script>
    layui.use(['form','jquery','layer','laydate'], function(){
        var $ = layui.jquery;
        var form = layui.form();
        var layer = layui.layer;
        //修改菜单
        form.on('submit(updateOrderDtlInfoForm)', function(params){
            var data = $("form").serializeArray();
            $.ajax({
                type: "POST",
                url: "/order/updateOrderDtlInfoForm.do",
                data: data,  //需要post的数据
                    success: function(data){
                if (data.result == 'success'){
                        layer.msg('更改成功',{
                            icon: 1,
                            time: 1000
                        },function(){
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        });
                    }else{
                    layer.msg('更改失败',{
                        icon: 1,
                        time: 1000
                    },function(){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    });
                }
                }
            });
            return false;//return false 表示不通过页面刷新方式提交表单
        });
        //定义一个全局变量
        //带回页面的select参数进行动态赋值
        var isReturn = "${params.isReturn!''}";
        var isUsed = "${params.isUsed !''}";
        // 菜单级别动态赋值
        if(isReturn == 'y') {
            $("#isReturn").find("option[value = 'y']").attr("selected","selected");
        } else if(isReturn == 'n') {
            $("#isReturn").find("option[value = 'n']").attr("selected","selected");
        }
        if(isUsed == 'y') {
            $("#isUsed").find("option[value = 'y']").attr("selected","selected");
        } else if(isUsed == 'n') {
            $("#isUsed").find("option[value = 'n']").attr("selected","selected");
        }
        // 重新渲染页面
        form.render();
    });
</script>
</body>
</html>


