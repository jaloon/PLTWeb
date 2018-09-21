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
    <script src="../../resources/js/appdev/appdevEdit.js"></script>

</head>

<body>
<div class="container">
    <input type="hidden" id="mode" value="${mode}">

    <c:if test="${mode=='view'}">
        <table>
            <tr>
                <td>手机UUID:</td>
                <td><input type="text" class="editInfo" value="${appdev.uuid}" readonly></td>
            </tr>
            <tr>
                <td>应用标识:</td>
                <td>
                    <input type="text" class="editInfo" value="${appdev.appid}" readonly>
                </td>
            </tr>
            <tr>
                <td>手机系统:</td>
                <td>
                    <input type="text" class="editInfo" value="${appdev.system}" readonly>
                </td>
            </tr>
            <tr>
                <td>手机型号:</td>
                <td><input type="text" class="editInfo" value="${appdev.model}" readonly></td>
            </tr>
            <tr>
                <td>当前版本:</td>
                <td><input type="text" class="editInfo" value="${appdev.currentVer}" readonly></td>
            </tr>
            <tr>
                <td>机主姓名:</td>
                <td><input type="text" class="editInfo" value="${appdev.owner}" readonly></td>
            </tr>
            <tr>
                <td>联系电话:</td>
                <td><input type="text" class="editInfo" value="${appdev.phone}" readonly></td>
            </tr>
            <tr>
                <td>职务:</td>
                <td><input type="text" class="editInfo" value="${appdev.duty}" readonly></td>
            </tr>
            <tr>
                <td>机构:</td>
                <td><input type="text" class="editInfo" value="${appdev.institution}" readonly></td>
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
                <td>应用标识:</td>
                <td>
                    <input type="text" class="editInfo" id="appid" placeholder="pltone_e_seal、pltone_e_seal_gasstation...">
                </td>
            </tr>
            <tr>
                <td>手机系统:</td>
                <td>
                    <select class="editInfo" id="system" style="width: 398px;height: 28px;">
                        <option value="Android">Android</option>
                        <option value="iOS">iOS</option>
                        <option value="其它">其它</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>手机型号:</td>
                <td>
                    <input type="text" class="editInfo" id="model">
                </td>
            </tr>
            <tr>
                <td>当前版本:</td>
                <td>
                    <input type="text" class="editInfo" id="current" placeholder="版本号格式：1.1.1">
                </td>
            </tr>
            <tr>
                <td>机主姓名:</td>
                <td><input type="text" class="editInfo" id="owner"></td>
            </tr>
            <tr>
                <td>联系电话:</td>
                <td><input type="text" class="editInfo" id="phone"></td>
            </tr>
            <tr>
                <td>职务:</td>
                <td><input type="text" class="editInfo" id="duty"></td>
            </tr>
            <tr>
                <td>机构:</td>
                <td><input type="text" class="editInfo" id="org"></td>
            </tr>
        </table>
        <div class="oper-zone">
            <input type="button" id="cancel" value="取消">
            <input type="button" id="confirm" value="确认">
        </div>
    </c:if>
    <c:if test="${mode=='edit'}">
        <input type="hidden" id="id" value="${appdev.id}">
        <table>
            <tr>
                <td>手机UUID:</td>
                <td>
                    <input type="text" class="editInfo" id="uuid" value="${appdev.uuid}" readonly>
                </td>
            </tr>
            <tr>
                <td>应用标识:</td>
                <td>
                    <input type="text" class="editInfo" value="${appdev.appid}" readonly>
                </td>
            </tr>
            <tr>
                <td>手机系统:</td>
                <td>
                    <select class="editInfo" id="system" style="width: 398px;height: 28px;">
                        <c:choose>
                            <c:when test="${appdev.system == 'Android'}">
                                <option value="Android" selected>Android</option>
                                <option value="iOS">iOS</option>
                                <option value="其它">其它</option>
                            </c:when>
                            <c:when test="${appdev.system == 'iOS'}">
                                <option value="Android" selected>Android</option>
                                <option value="iOS" selected>iOS</option>
                                <option value="其它">其它</option>
                            </c:when>
                            <c:otherwise>
                                <option value="Android">Android</option>
                                <option value="iOS">iOS</option>
                                <option value="其它" selected>其它</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>
            </tr>
            <tr>
                <td>手机型号:</td>
                <td>
                    <input type="text" class="editInfo" id="model" value="${appdev.model}">
                </td>
            </tr>
            <tr>
                <td>当前版本:</td>
                <td>
                    <input type="text" class="editInfo" id="current" value="${appdev.currentVer}"
                           placeholder="版本号格式：1.1.1">
                </td>
            </tr>
            <tr>
                <td>机主姓名:</td>
                <td><input type="text" class="editInfo" id="owner" value="${appdev.owner}"></td>
            </tr>
            <tr>
                <td>联系电话:</td>
                <td><input type="text" class="editInfo" id="phone" value="${appdev.phone}"></td>
            </tr>
            <tr>
                <td>职务:</td>
                <td><input type="text" class="editInfo" id="duty" value="${appdev.duty}"></td>
            </tr>
            <tr>
                <td>机构:</td>
                <td><input type="text" class="editInfo" id="org" value="${appdev.institution}"></td>
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