<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>
<body>
<fieldset class="layui-elem-field" >
    <legend>订单操作</legend>
  <div class="layui-field-box" >
        <form class="layui-form" >
            <input type="hidden" name="id" value="${params.id!''}">
            <div class="layui-form-item">
                <label class="layui-form-label">订单编号</label>
                <div class="layui-input-inline">
                    <input type="hidden"  value="${params.ordNo!''}" name="ordNo"   placeholder=" " autocomplete="off" class="layui-input" >
                    <input type="text"  value="${params.ordNo!''}" name="ordNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">会员编号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.vipNo!''}" name="vipNo"   placeholder=" " autocomplete="off" class="layui-input" disabled>
                </div>
            </div>
            <div  class="layui-inline">
                <label class="layui-form-label">订单区分</label>
                <div class="layui-input-inline">
                    <select id="ordTpCd" name="ordTpCd" >
                        <option value="100">一般订单</option>
                        <option value="200">退货单</option>
                        <option value="300">订货单</option>
                        <option value="400">补货单</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">订单时间</label>
                <div class="layui-inline">
                    <input name="ordCrtDate" value="${params.ordCrtDate!''}" class="layui-input" placeholder="订单时间" onclick="layui.laydate({elem: this})">
                </div>
            </div>

            <div  class="layui-inline">
                <label class="layui-form-label">订单状态</label>
                <div class="layui-input-inline">
                    <select id="ordStsCd" name="ordStsCd" >
                        <option value="100">未支付</option>
                        <option value="200">已支付</option>
                        <option value="300">已发货</option>
                        <option value="400">已出库</option>
                        <option value="500">已签收</option>
                        <option value="600">取消</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">订单金额</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.ordPrice!''}" name="ordPrice"   placeholder=" " autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">实际支付金额</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.realPrice!''}" name="realPrice"   placeholder=" " autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">发票金额</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.billPrice!''}" name="billPrice"   placeholder=" " autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">收件人名称</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.delName!''}" name="delName"   placeholder=" " autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">发票id</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.billId!''}" name="billId"   placeholder=" " autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">可售后时间</label>
                <div class="layui-inline">
                    <input class="layui-input" name="serviceDate" value="${params.serviceDate!''}" placeholder="可售后时间" onclick="layui.laydate({elem: this})">                </div>
            </div>
            <div class="layui-form-item">
            <label class="layui-form-label">会员地址</label>
            <div class="layui-input-inline">
                <select name="provinceId" lay-filter="province" id="provinceId">
                    <option value="0">请选择省份</option>
                <#list provinceList as prce>
                    <option value="${prce.provinceId}">${prce.province}</option>
                </#list>
                </select>
                <select name="cityId" id="cityId">

                    <option value="0">请选择城市</option>
                <#list cityList as item>
                    <option value="${item.cityId}">${item.city}</option>
                </#list>
                </select>
            </div>
        </div>
            <div class="layui-form-item">
                <label class="layui-form-label">详细收货地址</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.addrD!''}" name="addrD"   placeholder="支持模糊查询" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">面单号</label>
                <div class="layui-input-inline">
                    <input type="text"  value="${params.tNo!''}" name="tNo"   placeholder=" " autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="updateOrderInfoForm">确定</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</fieldset>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script>
    //Demo
    layui.use(['form','jquery','layer','laydate'], function(){
        var $ = layui.jquery;
        var form = layui.form();
        var layer = layui.layer;

        //修改菜单
        form.on('submit(updateOrderInfoForm)', function(params){
            var data = $("form").serializeArray();
            $.ajax({
                type: "POST",
                url: "/order/updateOrderInfoForm.do",
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
        var ordTpCd = "${params.ordTpCd!''}";
        var ordStsCd = "${params.ordStsCd !''}";
        var provinceId = "${params.addrP !''}";
        var cityId = "${params.addrC !''}";
        if(provinceId != '') {
            $("#provinceId").find("option[value = "+provinceId+"]").attr("selected","selected");
        }
        $("#cityId").find("option[value = \"${(params.addrC)!''}\"]").attr("selected","selected");

        // 菜单级别动态赋值
        if(ordTpCd == '100') {
            $("#ordTpCd").find("option[value = '100']").attr("selected","selected");
        } else if(ordTpCd == '200') {
            $("#ordTpCd").find("option[value = '200']").attr("selected","selected");
        } else if(ordTpCd == '300') {
            $("#ordTpCd").find("option[value = '300']").attr("selected","selected");
        }else if(ordTpCd == '400') {
            $("#ordTpCd").find("option[value = '400']").attr("selected","selected");
        } else if(ordTpCd == '999') {
            $("#ordTpCd").find("option[value = '999']").attr("selected","selected");
        }
        if(ordStsCd == '999') {
            $("#ordStsCd").find("option[value = '999']").attr("selected","selected");
        } else if(ordStsCd == '100') {
            $("#ordStsCd").find("option[value = '100']").attr("selected","selected");
        }else if(ordStsCd == '200') {
            $("#ordStsCd").find("option[value = '200']").attr("selected","selected");
        }else if(ordStsCd == '300') {
            $("#ordStsCd").find("option[value = '300']").attr("selected","selected");
        }else if(ordStsCd == '400') {
            $("#ordStsCd").find("option[value = '400']").attr("selected","selected");
        }else if(ordStsCd == '500') {
            $("#ordStsCd").find("option[value = '500']").attr("selected","selected");
        }else if(ordStsCd == '600') {
            $("#ordStsCd").find("option[value = '600']").attr("selected","selected");
        }
        //select更改事件
        form.on('select(province)', function(data){
            //console.log("123");
            var provinceId = $('#provinceId').val();
            if (provinceId != 0) {
                $.ajax({
                    type: "POST",
                    url:"/order/findCityList.do",  //后台程序地址
                    data: {"provinceId":provinceId},  //需要post的数据
                    success: function (data) {
                        //后台程序返回的标签，比如我喜欢使用1和0 表示成功或者失
                        $('#cityId').text("");
                        var op="<option value='0'>请选择城市</option>"
                        $('#cityId').append(op);
                        for (var i = 0; i < data.length; i++) {
                            var opt = "<option value = '" + data[i].cityId+ "'>" + data[i].city + "</option>"
                            $("#cityId").append(opt);
                        }
                        form.render();
                    }
                });
            }
        });
        // 重新渲染页面
        form.render();
    });
</script>
</body>
</html>


