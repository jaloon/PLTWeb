<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib prefix="pop" uri="/pop-tags" %>
<%-- <jsp:include page="/common/baseInclude.jsp"></jsp:include> --%>
<div class="main-content">
	<div class="container-fluid">
	    <div class="m-b-10 btntoolbar">
	    	<pop:Permission ename="editRole">
				<button class="btn btn-more btn-default btn-lt" id="addRole"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增角色</button>
				<button class="btn btn-more btn-lt btn-default" id="updateRole"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改角色</button>
				<button class="btn btn-more btn-lt btn-default" id="deleteRole"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除角色</button>
			</pop:Permission>
			<div class="form-group search-group">
                <div class="input-group">
                    <input id="searchVal" type="text" class="input form-control" placeholder="请输入角色名称">
                    <span class="input-group-btn">
                        <button class="btn btn btn-gray fastSearchBtn" type="button"> <i class="fa fa-search"></i> 搜索</button>
                    </span>
                </div>
            </div>
	    </div>
	    <div class="jqGrid_wrapper">
      	    <table id="roleListTable"></table>  
 	   		<div id="roleListTablePager"></div>
     	</div>
     	<div class="autoWidth"></div>
 	</div>
</div>  
<div id="roleDialog" title="角色管理"></div>

<script>
$(document).ready(function(){
	$(".btntoolbar").initBtnToolbar();
	var dialogW = 600;
	var dialogH = 480;
	$(".autoWidth").width(TRScreen.contentTableConW);
	$("#roleListTable").jqGridTable({    
		url:'${path}/manage/role/findForPage.do',
		height:TRScreen.contentTableConH-$(".m-b-10").height(),
        colNames:['ID','角色名称','备注'],
        colModel:[ 
              {name:'id',hidden:true},
              {name:'name',index:'name',formatter:nameFormatter,width:$(".autoWidth").width()*0.3},
              {name:'remark',sortable:false,width:$(".autoWidth").width()*0.7}
        ],
        onSelectRow:function(rowid,status){ enableButton(); },
	    onSelectAll:function(aRowids,status){ enableButton(); },
		loadEnd:function(){ enableButton(); }
    });
    $("#roleListTable").reloadJqGrid();
	function nameFormatter(el,options,rowData){
		return "<a href='javascript:void(0)' class='view' onclick='viewRole("+rowData.id+")'>"+rowData.name+"</a>"
	}
    function enableButton(){
		$("#updateRole,#deleteRole").addClass("disable");
		var selectIds = $("#roleListTable").getJqSelectIds();
		if(selectIds.length==0){
		}else if(selectIds.length==1){
			$("#updateRole,#deleteRole").removeClass("disable");
		}else{
			$("#deleteRole").removeClass("disable");
		}
	}
	$("#addRole").on("click",function(){
		if($(this).isTRDisable()){return;}
		$("#roleDialog").createDialog({
			title: "新增角色",
			width: dialogW,
			height: dialogH,
			url: "${path}/manage/role/dispatch.do?mode=add",
			buttons: {
   	        	"保存": function() {
   	        		$("#roleForm").submit();
    	        }
    	    }
			
		})
	})
	$("#updateRole").on("click",function(){
		if($(this).isTRDisable()){return;}
		$("#roleDialog").createDialog({
			title: "修改角色详情",
			width: dialogW,
			height: dialogH,
			url: "${path}/manage/role/dispatch.do?mode=edit&id="+$("#roleListTable").getJqSelectId(),
			buttons: {
   	        	"保存": function() {
   	        		$("#roleForm").submit();
    	        }
    	    }
			
		})
	})
	$("#deleteRole").on("click",function(){
		if($(this).isTRDisable()){return;}
		var selectIds = $("#roleListTable").getJqSelectIds();
		var confirmMsg = "数据一经删除将不能恢复，是否确认删除！"
		if($.inArray(TR.curUser.roleId+"", selectIds)>=0){
			confirmMsg = "不能删除当前正在使用的角色，是否继续删除其他角色！";
			if(selectIds.length==1){
				confirmMsg = "不能删除当前正在使用的角色！";
				selectIds = [];
			}
		}
		$.confirmBox({
			 msg:confirmMsg,
    		 height:200,
    		 width: 400,
    		 addDefaultCloseBtn:function(){return selectIds.length>0;}(),
    		 buttons:{
		        "确认": function() {
		        	if(selectIds.length>0){
			        	$.ajax({
			        		url:"${path}/manage/role/delete.do?ids="+$("#roleListTable").getJqSelectIds(),
			        		success:function(data){
			        			if(data && data.e){
									$.confirmBox({type:'warn',msg:data.e,defaultCloseBtnName:'确认'})
			                   	}else{
			                   		$("#roleListTable").reloadJqGrid();
			                   		$.confirmBox("close");
			                   	}
			        		}
			        	})
		        	}else{
		        		$.confirmBox("close");
		        	}
		        }
		    }
		})
	})
	$(".fastSearchBtn").on("click",function(){
		var name = $("#searchVal").val();
		$("#roleListTable").reloadJqGrid({
			name:name
		})
	})
	window.viewRole = function(id){
		<pop:Permission ename="viewRole">
			$("#roleDialog").createDialog({
				title: "角色详情",
				width: dialogW,
				height: dialogH,
				url: "${path}/manage/role/dispatch.do?mode=view&id="+id
			})
		</pop:Permission>
	}
});
</script>
	