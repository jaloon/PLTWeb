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
    <script src="../../resources/plugins/verify.js"></script>
    <script src="../../resources/js/base.js"></script>
    <script src="../../resources/js/centerdev/centerdevEdit.js"></script>

</head>

<body>
<div class="container">
    <input type="hidden" id="mode" value="${mode}">

    <c:if test="${mode=='view'}">
        <table>
            <tr>
                <td>手机UUID:</td>
                <td><input type="text" class="editInfo" value="${centerDev.uuid}" readonly></td>
            </tr>
            <tr>
                <td>用户中心:</td>
                <td>
                    <input type="text" class="editInfo" value="${centerDev.centerName}" readonly>
                </td>
            </tr>
        </table>
    </c:if>
    <c:if test="${mode=='add'}">
        <table>
            <tr>
                <td>手机UUID:</td>
                <td>
                    <input type="text" class="editInfo" id="uuid">
                </td>
            </tr>
            <tr>
                <td>用户中心:</td>
                <td>
                    <select class="editInfo" id="center" style="width: 398px;height: 28px;">
                        <c:forEach items="${centers}" var="center">
                            <option value="${center.id}">${center.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <div class="oper-zone">
            <input type="button" id="cancel" value="取消">
            <input type="button" id="confirm" value="确认">
        </div>
    </c:if>
    <c:if test="${mode=='edit'}">
        <input type="hidden" id="id" value="${centerDev.id}">
        <table>
            <tr>
                <td>手机UUID:</td>
                <td>
                    <input type="text" class="editInfo" id="uuid" value="${centerDev.uuid}" readonly>
                </td>
            </tr>
            <tr>
                <td>用户中心:</td>
                <td>
                    <select class="editInfo" id="center" style="width: 398px;height: 28px;">
                        <c:forEach items="${centers}" var="center">
                            <c:if test="${centerDev.centerId == center.id}">
                                <option value="${center.id}" selected>${center.name}</option>
                            </c:if>
                            <c:if test="${centerDev.centerId != center.id}">
                                <option value="${center.id}">${center.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
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