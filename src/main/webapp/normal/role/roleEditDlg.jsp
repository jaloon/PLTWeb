<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="pop" uri="/pop-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <jsp:include page="/common/baseInclude.jsp"></jsp:include> --%>
<style>
#permissionTree,#deptPermissionTree{
	width: 100%;
	height: 249px;
	background-color: #fff;
	border: 1px solid #D1D1D1;
	overflow: auto;
}
</style>
<div class="container_24">
	<form id="roleForm">
		<input type="hidden" name="id" value="${role.id }">
		<input type="hidden" name="isSuper" value="${role.isSuper }">
		<input type="hidden" name="permissionIds" value="">
		<div class="grid_4  lable-right">
			<label class="form-lbl"><em>* </em>角色名称：</label>
		</div>
		<div class="grid_20">
			<input name="name" class="form-txt required isRoleExist" value="${role.name }" maxlength="30">
		</div>
		<div class="clearLine"></div>
		
		<div class="grid_4  lable-right">
			<label class="form-lbl">备注：</label>
		</div>
		<div class="grid_20">
			<textarea name="remark" rows="2" class="form-txt" maxlength="200">${role.remark }</textarea>
		</div>
		<div class="clearLine"></div>
		
		<div class="grid_1">&nbsp;</div>
		<div class="grid_23">
	    	<div id="permissionTree"></div>
		</div>
		<div class="clearLine"></div>
	</form>
</div>
<script type="text/javascript">
$(function() {
	var mode = "${mode}";
	var roleId = "${role.id }";
	if(!roleId){
		roleId = 0;
	}
	if (mode == 'add') {
		$("#roleForm").attr("action","${path}/manage/role/add.do");
	} else if(mode == 'edit'){
		$("#roleForm").attr("action", "${path}/manage/role/update.do")
	} else{
		$("#roleForm").find("input,textarea,select").attr("disabled","disabled").addClass("viewMode")
	}
	$("#permissionTree").zTreeBox({
		async : {
			autoParam : ["ename"],
			otherParam : {"mode":mode,"roleId":roleId},
			url: "${path}/manage/role/findPermissions.do"
		},
		check: { enable: true,chkboxType: { "Y" : "ps", "N" : "ps" }},
		data: {	key: { name: "cname" } },
		callback: {
			onClick : function(event, treeId, treeNode){
			},
			onAsyncSuccess:function(event, treeId, treeNode, msg){
			}
		}
	});
	$("#roleForm").validate({
		submitHandler: function(form) {
			$("input[name='permissionIds']").val(getSystemPermissionIds());
			$(form).formSubmit({
				type:'post',
                success:function(data){
               		$.messageBox("角色编辑成功！");
               		$("#roleListTable").reloadJqGrid()
					$("#roleDialog").closeDialog();
                }
			})
		}
	});
	
	jQuery.validator.addMethod("isRoleExist", function(value, element) {
		var canAdd = true;
		var name = $("#roleForm input[name='name']").val();
		var id = $("#roleForm input[name='id']").val();
		$.ajax({
			async:false,
			url:"${path}/manage/role/isExist.do",
			data:{
				id:id,
				name:name
			},
			success:function(data){
				canAdd = !data;
			}
		})
		return canAdd;
	}, "角色名称重复！ ");
	
	function getSystemPermissionIds(){
		var ids="";
		var nodes = $("#permissionTree").getZTCheckedNodes();
		for(var i=0;i<nodes.length;i++){
			ids += nodes[i].id+",";
		}
		return ids;
	}
})
</script>