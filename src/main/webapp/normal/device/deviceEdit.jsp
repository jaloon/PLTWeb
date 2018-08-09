<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib prefix="pop" uri="/pop-tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="../../resources/css/base.css ">
    <link rel="stylesheet" href="../../resources/css/edit.css ">
    <script src="../../resources/plugins/jquery-3.2.1.min.js"></script>
    <script src="../../resources/plugins/layer/layer.js"></script>
    <script src="../../resources/plugins/laydate/laydate.js"></script>
    <script src="../../resources/plugins/verify.js"></script>
    <script src="../../resources/js/base.js"></script>
    <script src="../../resources/js/device/deviceEdit.js"></script>
    <script type="text/javascript">
        $(function () {
            <c:if test="${mode!='add'}">
            $("#ver").val(stringifyVer(${device.ver}));
            </c:if>
            <c:if test="${mode!='view'}">
            var ptime = new Date();
            var dtime = new Date();
            <c:if test="${mode=='edit'}">
            ptime = "${device.produce}";
            dtime = "${device.delivery}";
            </c:if>
            laydate.render({
                elem: '#produce',
                type: 'datetime',
                value: ptime
            });
            laydate.render({
                elem: '#delivery',
                type: 'datetime',
                value: dtime
            });
            </c:if>
        });
    </script>
</head>

<body>
<div class="container">
    <input type="hidden" id="mode" value="${mode}">

    <c:if test="${mode=='view'}">
        <table>
            <tr>
                <td>设备ID:</td>
                <td><input type="text" class="editInfo" id="did" value="${device.deviceId}" readonly></td>
            </tr>
            <tr>
                <td>设备类型:</td>
                <td><input type="text" class="editInfo" value="${device.typeName}" readonly></td>
            </tr>
            <tr>
                <td>设备版本:</td>
                <td>
                    <input type="text" class="editInfo" id="ver" readonly>
                </td>
            </tr>
            <tr>
                <td>设备型号:</td>
                <td><input type="text" class="editInfo" id="model" value="${device.model}" readonly></td>
            </tr>
            <tr>
                <td>用户中心:</td>
                <td><input type="text" class="editInfo" value="${device.centerName}" readonly></td>
            </tr>
            <tr>
                <td>出厂时间:</td>
                <td><input type="text" class="editInfo" id="produce" value="${device.produce}" readonly></td>
            </tr>
            <tr>
                <td>交付时间:</td>
                <td><input type="text" class="editInfo" id="delivery" value="${device.delivery}" readonly></td>
            </tr>
            <tr>
                <td>备注:</td>
                <td><input type="text" class="editInfo" id="remark" value="${device.remark}" readonly></td>
            </tr>
        </table>
    </c:if>
    <c:if test="${mode=='add'}">
        <table>
            <!--  <tr>
                 <td>设备ID:</td>
                 <td>
                     <input type="text" class="editInfo" id="did" readonly>
                 </td>
             </tr> -->
            <tr>
                <td>设备类型:</td>
                <td>
                    <select class="editInfo" id="type" style="width: 398px;height: 28px;">
                        <option value=1>车载终端</option>
                        <option value=2>锁</option>
                        <option value=3>出入库读卡器</option>
                        <option value=4>手持机</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>设备版本:</td>
                <td>
                    <input type="text" class="editInfo" id="ver" placeholder="版本号格式：1.1.1">
                </td>
            </tr>
            <tr>
                <td>设备型号:</td>
                <td>
                    <input type="text" class="editInfo" id="model">
                </td>
            </tr>
            <tr>
                <td>用户中心:</td>
                <td>
                    <c:if test="${centers == null || fn:length(centers) == 0}">
                        <input type="text" class="editInfo" value="无用户中心数据" style="color: red" readonly>
                        <input type="hidden" id="center" value="0">
                    </c:if>
                    <c:if test="${centers != null && fn:length(centers) > 0}">
                        <select class="editInfo" id="center" style="width: 398px;height: 28px;">
                            <c:forEach var="center" items="${centers}">
                                <option value="${center.id}">${center.name}</option>
                            </c:forEach>
                        </select>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>出厂时间:</td>
                <td>
                    <input class="editInfo" type="text" id="produce">
                </td>
            </tr>
            <tr>
                <td>交付时间:</td>
                <td>
                    <input class="editInfo" type="text" id="delivery">
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td>
                    <input class="editInfo" type="text" id="remark">
                </td>
            </tr>
            <!-- <tr>
                <td style="height:28px"></td>
            </tr> -->
        </table>
        <div class="oper-zone">
            <input type="button" id="cancel" value="取消">
            <input type="button" id="confirm" value="确认">
        </div>
    </c:if>
    <c:if test="${mode=='edit'}">
        <input type="hidden" id="id" value="${device.id}">
        <table>
            <tr>
                <td>设备ID:</td>
                <td>
                    <input type="text" class="editInfo" id="did" value="${device.deviceId}" readonly>
                </td>
            </tr>
            <tr>
                <td>设备类型:</td>
                <td>
                    <input type="text" class="editInfo" id="typeName" value="${device.typeName}" readonly>
                    <input type="hidden" id="type" value="${device.type}" readonly>
                </td>
            </tr>
            <tr>
                <td>设备版本:</td>
                <td>
                    <input type="text" class="editInfo" id="ver" placeholder="版本号格式：1.1.1">
                </td>
            </tr>
            <tr>
                <td>设备型号:</td>
                <td>
                    <input type="text" class="editInfo" id="model" value="${device.model}">
                </td>
            </tr>
            <tr>
                <td>用户中心:</td>
                <td>
                    <c:if test="${centers == null || fn:length(centers) == 0}">
                        <input type="text" class="editInfo" value="无用户中心数据" style="color: red" readonly>
                        <input type="hidden" id="center" value="0">
                    </c:if>
                    <c:if test="${centers != null && fn:length(centers) > 0}">
                        <select class="editInfo" id="center" style="width: 398px;height: 28px;">
                            <c:forEach var="center" items="${centers}">
                                <c:if test="${device.centerId == center.id}">
                                    <option value="${center.id}" selected>${center.name}</option>
                                </c:if>
                                <c:if test="${device.centerId != center.id}">
                                    <option value="${center.id}">${center.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>出厂时间:</td>
                <td>
                    <input class="editInfo" type="text" id="produce">
                </td>
            </tr>
            <tr>
                <td>交付时间:</td>
                <td>
                    <input class="editInfo" type="tel" id="delivery">
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td>
                    <input class="editInfo" type="text" id="remark" value="${device.remark}">
                </td>
            </tr>
        </table>
        <div class="oper-zone">
            <input type="button" id="cancel" value="取消">
            <input type="button" id="confirm" value="确认">
        </div>
    </c:if>
</div>
</body>

</html>