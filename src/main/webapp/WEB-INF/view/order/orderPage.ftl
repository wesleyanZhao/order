<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>订单管理系统 惠买ivalue后台管理</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>

<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>筛选条件</legend>
</fieldset>
<form id="pageSubmit" class="layui-form" method="post">
    <input type="hidden" id="currentPage" name="currentPage" >
    <div class="layui-form-item">
        <label class="layui-form-label">订单编号</label>
        <div class="layui-input-inline">
            <input type="text"  value="${params.ordNo!''}" name="ordNo"   placeholder=" " autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">会员编号</label>
        <div class="layui-input-inline">
            <input type="text"  value="${params.vipNo!''}" name="vipNo"   placeholder=" " autocomplete="off" class="layui-input">
        </div>
    </div>
    <div  class="layui-inline">
        <label class="layui-form-label">订单区分</label>
        <div class="layui-input-inline">
            <select id="ordTpCd" name="ordTpCd" >
                <option value="999">请选择</option>
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
            <input name="orderDateStart"   class="layui-input" placeholder="订单开始时间" onclick="layui.laydate({elem: this})">
        </div>-
        <div class="layui-inline">
            <input name="orderDateEnd"   class="layui-input" placeholder="订单结束时间" onclick="layui.laydate({elem: this})">
        </div>
    </div>

    <div  class="layui-inline">
        <label class="layui-form-label">订单状态</label>
        <div class="layui-input-inline">
            <select id="ordStsCd" name="ordStsCd" >
                <option value="999">请选择</option>
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
                <option value="999">请选择省份</option>
            <#list provinceList as prce>
                <option value="${prce.provinceId}">${prce.province}</option>
            </#list>
            </select>
        </div>
        <div class="layui-input-inline">
            <select name="cityId" id="cityId">
                <option value="999">请选择城市</option>
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
    <div class="layui-inline">
        <button class="layui-btn" lay-submit lay-filter="">搜索</button>
        <button class="layui-btn layui-btn-primary" type="reset">重置</button>
    </div>
</form>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>订单列表</legend>
</fieldset>
<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
            <col width="100">
        </colgroup>
        <thead>
        <tr>
            <th>ID</th>
            <th>订单编号</th>
            <th>会员编号</th>
            <th>订单区分</th>
            <th>订单日期</th>
            <th>订单状态</th>
            <th>订单金额</th>
            <th>实际支付金额</th>
            <th>发票金额</th>
            <th>收件人名称</th>
            <th>发票id</th>
            <th>可售后时间</th>
            <th>收货省编号</th>
            <th>收货市编号</th>
            <th>详细收货地址</th>
            <th>面单号</th>
            <th>操作</th>

        </tr>
        </thead>
        <tbody>
        <#list list as item>
        <tr>
            <td>${item.id! ''}</td>
            <td>${item.ordNo! ''}</td>
            <td>${item.vipNo! ''}</td>
            <td>
                <#if item.ordTpCd=='100'>一般订单
                <#elseif item.ordTpCd=='200'>退货单
                <#elseif item.ordTpCd=='300'>订货单
                <#elseif item.ordTpCd=='400'>补货单
                </#if>
            </td>
            <td>${item.ordCrtDate! ''}</td>
            <td>
                <#if item.ordStsCd==100>未支付
                <#elseif item.ordStsCd==200>已支付
                <#elseif item.ordStsCd==300>已发货
                <#elseif item.ordStsCd==400>已出库
                <#elseif item.ordStsCd==500>已签收
                <#elseif item.ordStsCd==600>取消
                </#if>
            </td>
            <td>${item.ordPrice! ''}</td>
            <td>${item.realPrice! ''}</td>
            <td>${item.billPrice! ''}</td>
            <td>${item.delName! ''}</td>
            <td>${item.billId! ''}</td>
            <td>${item.serviceDate! ''}</td>
            <td>${item.addrP! ''}</td>
            <td>${item.addrC! ''}</td>
            <td>${item.addrD! ''}</td>
            <td>${item.tNo! ''}</td>
            <td>
                <a href="javascript:;" class="layui-btn layui-btn-radius updateOrder"  var="${item.id!''}">修改</a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<div class="layui-form">
    <span id="form_page"></span>&nbsp;共${page.total}条数据
</div>
<script type="text/javascript" src="/resources/layui/layui.js"></script>
<script type="text/javascript">
    layui.define([ 'element', 'form', 'layer', 'laypage' ,'laydate'], function(exports) {
        var element = layui.element();
        var form = layui.form();
        var layer = layui.layer;
        var laypage = layui.laypage;
        var $ = layui.jquery;
        var pindex = "${page.pageNum}";// 当前页
        var ptotalpages = "${page.pages}";// 总页数
        var pcount = "${page.total}";// 数据总数
        // 分页
        laypage({
            cont : 'form_page', // 页面上的id
            pages : ptotalpages,//总页数
            curr : pindex,//当前页。
            skip : true,
            jump : function(obj, first) {
                $("#currentPage").val(obj.curr);//设置当前页
                //$("#size").val(psize);
                //防止无限刷新,
                //只有监听到的页面index 和当前页不一样是才出发分页查询
                if (obj.curr != pindex ) {
                    $("#pageSubmit").submit();
                }
            }
        });
        //带回页面的select参数进行动态赋值
        var ordTpCd = "${params.ordTpCd!''}";
        var ordStsCd = "${params.ordStsCd !''}";
        var provinceId = "${params.provinceId!''}";
        var cityId = "${params.cityId !''}";
        if(provinceId != '') {
            $("#provinceId").find("option[value = "+provinceId+"]").attr("selected","selected");
        }
        $("#cityId").find("option[value = \"${(params.cityId)!''}\"]").attr("selected","selected");
      /*
              $("#cityId").find("option[value = "+cityId+"]").attr("selected","selected");
      */
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
        // 重新渲染页面
        form.render();
        //  弹出窗  修改订单信息
        $(".updateOrder").on("click", function(){
            var id = $(this).attr("var");
            layer.open({
                title: '商品明细 - 惠买ivalue管理系统'
                ,area: ['550px', '500px']
                ,type: 2
                ,content: '/order/updateOrderInfo.do?id='+id
            });
        });
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


    });
</script>
</body>
</html>