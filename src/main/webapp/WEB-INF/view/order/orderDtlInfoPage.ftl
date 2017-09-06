<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>订单明细系统 惠买ivalue后台管理</title>
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

    <div class="layui-inline">
        <button class="layui-btn" lay-submit lay-filter="">搜索</button>
        <button class="layui-btn layui-btn-primary" type="reset">重置</button>
    </div>
</form>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>订单明细列表</legend>
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
            <th>订单明细编号</th>
            <th>商品编号</th>
            <th>商品明细编号</th>
            <th>商品数量</th>
            <th>会员编号</th>
            <th>可退货与否</th>
            <th>成本价格</th>
            <th>销售价格</th>
            <th>企划编号</th>
            <th>余额支付</th>
            <th>积分支付</th>
            <th>是否可用</th>
            <th>商品类型</th>
            <th>商品名称</th>
            <th>商品细别名称</th>

            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list list as item>
        <tr>
            <td>${item.id! ''}</td>
            <td>${item.ordNo! ''}</td>
            <td>${item.ordDtlNo! ''}</td>

            <td>${item.prdNo! ''}</td>
            <td>${item.prdDtlNo! ''}</td>
            <td>${item.prdCount! ''}</td>
            <td>${item.vipNo! ''}</td>
            <td>
                <#if item.isReturn=='y'>可退换
                <#elseif item.isReturn=='n'>不可退换
                </#if>
            </td>
            <td>${item.ordSum! ''}</td>
            <td>${item.saleSum! ''}</td>
            <td>${item.spNo! ''}</td>
            <td>${item.cashAmt! ''}</td>
            <td>${item.igAmt! ''}</td>
            <td>
                <#if item.isUsed=='y'>可用
                <#elseif item.isUsed=='n'>不可用
                </#if>
            </td>
            <td>${item.tpName! ''}</td>
            <td>${item.prdName! ''}</td>
            <td>${item.prdDtlName! ''}</td>
            <td>
                <a href="javascript:;" class="layui-btn layui-btn-radius updateOrderDtlInfo"  var="${item.id!''}">修改</a>
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

        //  弹出窗  修改订单明细信息
        $(".updateOrderDtlInfo").on("click", function(){
            var id = $(this).attr("var");
            layer.open({
                title: '商品明细 - 惠买ivalue管理系统'
                ,area: ['550px', '500px']
                ,type: 2
                ,content: '/order/updateOrderDtlInfo.do?id='+id
            });
        });
    });
</script>
</body>
</html>