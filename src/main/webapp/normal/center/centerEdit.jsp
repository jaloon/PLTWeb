<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib prefix="pop" uri="/pop-tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=center-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="../../resources/css/base.css ">
    <link rel="stylesheet" href="../../resources/css/edit.css ">
    <style type="text/css">
    	.ftp {
    		width: 500px;
    	}
    	
    	.ftp>table {
    		left: 10px;
    		top: 5px;
    	}
    
    	.ftp>.oper-zone {
    		top: 12px;
    	}
    	
    	#ftp_cancel {
		    width: 100px;
		    height: 30px;
		    border: 1px solid #478de4;
		    border-radius: 3px;
		    background: #ffffff;
		    color: #40454b;
		    position: absolute;
		    top: 50%;
		    margin-top: -15px;
		    right: 124px;
            cursor: pointer;
		}

        #ftp_cancel:hover{
            color: #5ca1f7;
            border-color: #5ca1f7;
        }
		
		#ftp_confirm {
		    width: 100px;
		    height: 30px;
		    background: #478de4;
		    color: #ffffff;
		    border: 0;
		    border-radius: 3px;
		    position: absolute;
		    top: 50%;
		    margin-top: -15px;
		    right: 14px;
            cursor: pointer;
		}

        #ftp_confirm:hover{
            background: #5ca1f7;
        }
    </style>
    <script src="../../resources/plugins/jquery-3.2.1.min.js"></script>
    <script src="../../resources/plugins/layer/layer.js"></script>
    <script src="../../resources/plugins/verify.js"></script>
    <script src="../../resources/js/base.js"></script>
    <script src="../../resources/js/center/centerEdit.js"></script>
</head>

<body>
    <div class="container">
        <input type="hidden" id="mode" value="${mode}">
        <c:if test="${mode=='view'}">
            <table>
               <tr>
                    <td>中心ID:</td>
                    <td>
                        <input type="text" class="editInfo" id="id" value="${center.id}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>中心名称:</td>
                    <td>
                        <input type="text" class="editInfo" id="name" value="${center.name}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>IP地址:</td>
                    <td>
                        <input type="text" class="editInfo" id="ip" value="${center.ip}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>Web端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="web" value="${center.webPort}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>监管端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="tcp" value="${center.tcpPort}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>道闸端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="barrier" value="${center.barrierPort}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>ftp配置:</td>
                    <td>
                        <a href="#">
                            <div class="editInfo" id="ftp" style="background: #e6e7e9;">${center.ftpConfig}</div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>负责人:</td>
                    <td>
                        <input type="text" class="editInfo" id="director" value="${center.director}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input type="tel" class="editInfo" id="phone" value="${center.phone}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>联系地址:</td>
                    <td>
                        <input type="text" class="editInfo" id="address" value="${center.address}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td>
                        <input type="text" class="editInfo" id="remark" value="${center.remark}" readonly>
                    </td>
                </tr>
            </table>
        </c:if>
        <c:if test="${mode=='add'}">
            <table>
                <tr>
                    <td>中心名称:</td>
                    <td>
                        <input type="text" class="editInfo" id="name">
                    </td>
                </tr>
                <tr>
                    <td>IP地址:</td>
                    <td>
                        <input type="text" class="editInfo" id="ip">
                    </td>
                </tr>
                <tr>
                    <td>Web端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="web">
                    </td>
                </tr>
                <tr>
                    <td>监管端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="tcp">
                    </td>
                </tr>
                <tr>
                    <td>道闸端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="barrier">
                    </td>
                </tr>
                <tr>
                    <td>ftp配置:</td>
                    <td>
                        <a href="#">
                            <div class="editInfo" id="ftp"></div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>负责人:</td>
                    <td>
                        <input type="text" class="editInfo" id="director">
                    </td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input type="text" class="editInfo" id="phone">
                    </td>
                </tr>
                <tr>
                    <td>联系地址:</td>
                    <td>
                        <input type="text" class="editInfo" id="address">
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td>
                        <input type="text" class="editInfo" id="remark">
                    </td>
                </tr>
            </table>
            <div class="oper-zone">
	            <input type="button" id="cancel" value="取消">
	            <input type="button" id="confirm" value="确认">
	        </div>
        </c:if>
        <c:if test="${mode=='edit'}">
            <table>
                <tr>
                    <td>中心ID:</td>
                    <td>
                        <input type="text" class="editInfo" id="id" value="${center.id}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>中心名称:</td>
                    <td>
                        <input type="text" class="editInfo" id="name" value="${center.name}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>IP地址:</td>
                    <td>
                        <input type="text" class="editInfo" id="ip" value="${center.ip}">
                    </td>
                </tr>
                <tr>
                    <td>Web端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="web" value="${center.webPort}">
                    </td>
                </tr>
                <tr>
                    <td>监管端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="tcp" value="${center.tcpPort}" >
                    </td>
                </tr>
                <tr>
                    <td>道闸端口:</td>
                    <td>
                        <input type="text" class="editInfo" id="barrier" value="${center.barrierPort}" >
                    </td>
                </tr>
                <tr>
                    <td>ftp配置:</td>
                    <td>
                        <a href="#">
                            <div class="editInfo" id="ftp">${center.ftpConfig}</div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>负责人:</td>
                    <td>
                        <input type="text" class="editInfo" id="director" value="${center.director}">
                    </td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input type="tel" class="editInfo" id="phone" value="${center.phone}">
                    </td>
                </tr>
                <tr>
                    <td>联系地址:</td>
                    <td>
                        <input type="text" class="editInfo" id="address" value="${center.address}">
                    </td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td>
                        <input type="text" class="editInfo" id="remark" value="${center.remark}">
                    </td>
                </tr>
            </table>
            <div class="oper-zone">
	            <input type="button" id="cancel" value="取消">
	            <input type="button" id="confirm" value="确认">
	        </div>
        </c:if>
    </div>
    <div class="container ftp" style="display:none;">
    	<c:if test="${mode=='view'}">
	    	<table>
				<tr>
					<td>ftp协议:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_protocol" value="${center.ftpConfig.protocol}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>主机IP地址:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_host" value="${center.ftpConfig.host}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>端口号:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_port" value="${center.ftpConfig.port}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>文件目录:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_directory" value="${center.ftpConfig.directory}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>账号:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_account" value="${center.ftpConfig.account}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>密码:</td>
		            <td>
		                <input type="password" class="editInfo" id="ftp_password" value="${center.ftpConfig.password}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>备注:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_remark" value="${center.ftpConfig.remark}" readonly>
		            </td>
				</tr>    
	    	</table>
       	</c:if>
    	<c:if test="${mode=='edit'}">
	    	<table>
				<tr>
					<td>ftp协议:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_protocol" value="${center.ftpConfig.protocol}" readonly>
		            </td>
				</tr>    
				<tr>
					<td>主机IP地址:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_host" value="${center.ftpConfig.host}">
		            </td>
				</tr>    
				<tr>
					<td>端口号:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_port" value="${center.ftpConfig.port}" placeholder="范围：0 ~ 65535，例如：端口为 21。">
		            </td>
				</tr>    
				<tr>
					<td>文件目录:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_directory" value="${center.ftpConfig.directory}" placeholder="若为用户根目录，不填；若为子目录，以“/”开头，不以“/”结尾，并且不含“//”，例如：/user。">
		            </td>
				</tr>    
				<tr>
					<td>账号:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_account" value="${center.ftpConfig.account}">
		            </td>
				</tr>    
				<tr>
					<td>密码:</td>
		            <td>
		                <input type="password" class="editInfo" id="ftp_password" value="${center.ftpConfig.password}">
		            </td>
				</tr>    
				<tr>
					<td>备注:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_remark" value="${center.ftpConfig.remark}">
		            </td>
				</tr>    
	    	</table>
	    	<div class="oper-zone">
	           	<input type="button" id="ftp_cancel" value="取消">
	           	<input type="button" id="ftp_confirm" value="确认">
	       	</div>
       	</c:if>
    	<c:if test="${mode=='add'}">
	    	<table>
				<tr>
					<td>ftp协议:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_protocol" value="ftp" readonly>
		            </td>
				</tr>    
				<tr>
					<td>主机IP地址:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_host">
		            </td>
				</tr>    
				<tr>
					<td>端口号:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_port" placeholder="范围：0 ~ 65535，例如：端口为 21。">
		            </td>
				</tr>    
				<tr>
					<td>文件目录:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_directory" placeholder="若为用户根目录，不填；若为子目录，以“/”开头，不以“/”结尾，并且不含“//”，例如：/user。">
		            </td>
				</tr>    
				<tr>
					<td>账号:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_account" >
		            </td>
				</tr>    
				<tr>
					<td>密码:</td>
		            <td>
		                <input type="password" class="editInfo" id="ftp_password" >
		            </td>
				</tr>    
				<tr>
					<td>备注:</td>
		            <td>
		                <input type="text" class="editInfo" id="ftp_remark" >
		            </td>
				</tr>    
	    	</table>
	    	<div class="oper-zone">
	           	<input type="button" id="ftp_cancel" value="取消">
	           	<input type="button" id="ftp_confirm" value="确认">
	       	</div>
       	</c:if>
    </div>
</body>
</html>