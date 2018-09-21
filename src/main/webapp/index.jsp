<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pop" uri="/pop-tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>普利通电子签封安全监管系统</title>
    <link rel="stylesheet" type="text/css" href="resources/css/base.css">
    <link rel="stylesheet" type="text/css" href="resources/css/index.css" />
    <script src="resources/plugins/jquery-3.2.1.min.js"></script>
    <script src="resources/plugins/layer/layer.js"></script>
    <script src="resources/js/base.js"></script>
    <script src="resources/js/index.js"></script>
    <style type="text/css">
        .centerManage dt {
            background-image: url(resources/images/navbar/center.png);
        }
        
        .deviceManage dt {
            background-image: url(resources/images/navbar/device.png);
        }
        
        .appdevManage dt {
            background-image: url(resources/images/navbar/cell.png);
        }

        .centerdevManage dt {
            background-image: url(resources/images/navbar/ascription.png);
        }

        .appverManage dt {
            background-image: url(resources/images/navbar/app.png);
        }

        .userManage dt {
            background-image: url(resources/images/navbar/permission.png);
        }
    </style>
    <c:set var="user" value="<%=com.tipray.core.ThreadVariable.getUser()%>"></c:set>
</head>

<body>
    <div class="container">
        <div class="top">
            <div class="profile-zone">
            	<input type="hidden" id="id" value="${user.id}">
                <span class="profile-name">${user.name }<img src="resources/images/profile/profile_down.png" /></span>
                <img class="profile-pic" src="resources/images/default_headpic.png" />
            	<div class="info-zone">
                    <div class="user-info" id="info-edit">修改个人信息</div>
                    <br>
                    <div class="user-info" id="pwd-edit">修改密码</div>
                    <hr />
                    <div class="user-info" id="logout">退出</div>
                </div>
            </div>
            <div class="logo-zone">
                <img src="resources/images/commen-logo.png" />
                <span class="logo-text">普利通电子签封安全监管系统</span>
            </div>
        </div>
        <div class="left-nav">
          	<pop:Permission ename="infoManage">
          		<pop:Permission ename="centerManage">
		            <dl class="centerManage">
		                <dt onclick="showList('center')">用户中心管理</dt>
		                <!-- <dt><a href="normal/center/centerList.html" target="showContent">用户中心管理</a></dt> -->
		                <!-- <dd class="first_dd"><a href="normal/center/centerList.html" target="showContent">查看用户中心</a></dd> -->
		            </dl>
          		</pop:Permission>
          		<pop:Permission ename="deviceManage">
		            <dl class="deviceManage">
		                <dt onclick="showList('device')">设备信息管理</dt>
		                <!-- <dt><a href="normal/device/deviceList.html" target="showContent">设备信息管理</a></dt> -->
		                <!-- <dd class="first_dd"><a href="normal/device/deviceList.html" target="showContent">查看设备</a></dd> -->
		            </dl>
          		</pop:Permission>
          		<pop:Permission ename="appdevManage">
		            <dl class="appdevManage">
		                <dt onclick="showList('appdev')">APP设备管理</dt>
		            </dl>
          		</pop:Permission>
          		<pop:Permission ename="centerdevManage">
		            <dl class="centerdevManage">
		                <dt onclick="showList('centerdev')">APP归属管理</dt>
		            </dl>
          		</pop:Permission>
          		<pop:Permission ename="appverManage">
		            <dl class="appverManage">
		                <dt onclick="showList('appver')">APP版本管理</dt>
		            </dl>
          		</pop:Permission>
          	</pop:Permission>
          	<pop:Permission ename="systemManage">
	        	<pop:Permission ename="userManage">
		            <dl class="userManage">
		                <dt onclick="showList('user')">操作员权限管理</dt>
		                <!-- <dt><a href="normal/user/userList.html" target="showContent">操作员权限管理</a></dt> -->
		                <!-- <dd class="first_dd"><a href="normal/user/userList.html" target="showContent">操作员管理</a></dd> -->
		            </dl>
	            </pop:Permission>
          	</pop:Permission>
        </div>
        <div class="main">
            <iframe src="normal/device/deviceList.html" frameborder="0" name="showContent" width="100%" height="100%"></iframe>
        </div>
    </div>

</body>
</html>
