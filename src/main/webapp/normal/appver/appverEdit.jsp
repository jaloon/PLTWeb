<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
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
    <script src="../../resources/js/appver/appverEdit.js"></script>
</head>

<body>
    <div class="container">
        <input type="hidden" id="mode" value="${mode}">

        <c:if test="${mode=='view'}">
            <table>
                <tr>
                    <td>用户中心:</td>
                    <td><input type="text" class="editInfo" value="${appVer.centerName}" readonly></td>
                </tr>
                <tr>
                    <td>应用标识:</td>
                    <td>
                        <input type="text" class="editInfo" value="${appVer.appid}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>手机系统:</td>
                    <td>
                        <input type="text" class="editInfo" value="${appVer.system}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>指定版本:</td>
                    <td><input type="text" class="editInfo" value="${appVer.assignVer}" readonly></td>
                </tr>
                <tr>
                    <td>最低版本:</td>
                    <td><input type="text" class="editInfo"  value="${appVer.minVer}" readonly></td>
                </tr>
            </table>
        </c:if>
        <c:if test="${mode=='defaut'}">
            <input type="hidden" id="center" value="0">
            <table>
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
                    <td>指定版本:</td>
                    <td>
                        <input type="text" class="editInfo" id="assign" placeholder="版本号格式：1.1.1">
                    </td>
                </tr>
                <tr>
                    <td>最低版本:</td>
                    <td>
                        <input type="text" class="editInfo" id="minver" placeholder="版本号格式：1.1.1">
                    </td>
                </tr>
            </table>
            <div class="oper-zone">
                <input type="button" id="cancel" value="取消">
                <input type="button" id="confirm" value="确认">
            </div>
        </c:if>
        <c:if test="${mode=='add'}">
            <table>
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
                    <td>指定版本:</td>
                    <td>
                        <input type="text" class="editInfo" id="assign" placeholder="版本号格式：1.1.1">
                    </td>
                </tr>
                <tr>
                    <td>最低版本:</td>
                    <td>
                        <input type="text" class="editInfo" id="minver" placeholder="版本号格式：1.1.1">
                    </td>
                </tr>
            </table>
            <div class="oper-zone">
                <input type="button" id="cancel" value="取消">
                <input type="button" id="confirm" value="确认">
            </div>
        </c:if>
        <c:if test="${mode=='edit'}">
            <input type="hidden" id="id" value="${appVer.id}">
            <table>
                <tr>
                    <td>用户中心:</td>
                    <td><input type="text" class="editInfo" value="${appVer.centerName}" readonly></td>
                </tr>
                <tr>
                    <td>应用标识:</td>
                    <td>
                        <input type="text" class="editInfo" value="${appVer.appid}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>手机系统:</td>
                    <td>
                        <input type="text" class="editInfo" value="${appVer.system}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>指定版本:</td>
                    <td>
                        <input type="text" class="editInfo" id="assign" value="${appVer.assignVer}"  placeholder="版本号格式：1.1.1">
                    </td>
                </tr>
                <tr>
                    <td>最低版本:</td>
                    <td>
                        <input type="text" class="editInfo" id="minver" value="${appVer.minVer}"  placeholder="版本号格式：1.1.1">
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