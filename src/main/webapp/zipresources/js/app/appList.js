var dispatch;var deleteApp;var centerHtml="";$(function(){dispatch=function(mode,id){var title="添加APP设备信息";var h="379px";if("edit"==mode){title="修改APP设备信息";h="379px"}if("view"==mode){title="查看APP设备信息";h="322px"}layer.open({type:2,title:["APP设备信息管理（"+title+"）","font-size:14px;color:#ffffff;background:#478de4;"],shadeClose:true,shade:0.8,area:["540px",h],content:"../../manage/app/dispatch.do?mode="+mode+"&id="+id,end:function(){if(mode!="view"){refreshPage()}}})};deleteApp=function(id){layer.confirm("删除后不可撤销，是否确认删除？",{icon:0,title:["删除","font-size:14px;color:#ffffff;background:#478de4;"]},function(){$.post("../../manage/app/delete.do","id="+id,function(data){if("success"==data.msg){layer.msg("删除成功！",{icon:1,time:500},function(){refreshPage()})}else{layer.msg("删除失败！",{icon:2,time:500})}},"json")})};function showList(uuid,centerId,pageId){var rows=$("#page_size").val();var startRow=(pageId-1)*rows;$.post("../../manage/app/ajaxFindForPage.do","uuid="+uuid+"&center.id="+centerId+"&pageId="+pageId+"&startRow="+startRow+"&rows="+rows,function(data){var gridPage=eval(data);var maxIndex=$("#page_id option:last").index();var len=maxIndex+1-gridPage.total;if(len>0){for(var i=gridPage.total>0?gridPage.total:1;i<maxIndex+1;i++){$("#page_id option:last").remove()}}else{if(len<0){for(var i=maxIndex+2;i<=gridPage.total;i++){$("#page_id").append("<option value="+i+">"+i+"</option>")}}}$("#page_id").val(gridPage.page);$("#page_info").html("页("+gridPage.currentRows+"条数据)/共"+gridPage.total+"页(共"+gridPage.records+"条数据)");$("#auuid").val(gridPage.t.uuid);$("#acenter").val(gridPage.t.center.id);$(".table-body").html("");var apps=gridPage.dataList;var tableData="<table width='100%'>";for(var i=0;i<gridPage.currentRows;i++){var app=apps[i];tableData+='<tr><td class="app-uuid">'+app.uuid+'</td><td class="app-system">'+app.system+'</td><td class="app-model">'+app.model+'</td><td class="app-center">'+app.center.name+'</td><td class="app-current">'+app.currentVer+'</td><td class="app-assign">'+app.assignVer+'</td><td class="app-min">'+app.minVer+'</td><td class="app-action"><img src="../../resources/images/operate/view.png" alt="查看" title="查看" onclick="dispatch(\'view\','+app.id+')">&emsp;<img src="../../resources/images/operate/edit.png" alt="编辑" title="编辑" onclick="dispatch(\'edit\','+app.id+')">&emsp;<img src="../../resources/images/operate/delete.png" alt="删除" title="删除" onclick="deleteAppdev('+app.id+')"></td></tr>'}tableData+="</table>";tableData=tableData.replace(/>null</g,"><");$(".table-body").html(tableData)},"json").fail(function(XMLHttpRequest,textStatus,errorThrown){if(XMLHttpRequest.readyState==4){var http_status=XMLHttpRequest.status;if(http_status==0||http_status>600){location.reload(true)}else{if(http_status==200){if(textStatus=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+textStatus)}}else{layer.alert("http connection error: status["+http_status+"], "+XMLHttpRequest.statusText)}}}})}showList("","",1);$("#search_type").change(function(){$("#search_text").empty();var type=$("#search_type").val();if(type<2){$("#search_text").replaceWith('<input type="text" id="search_text">')}else{if(type==2){if(centerHtml.length==0){$.getJSON("../../manage/device/getCenterList.do",function(centers){var len=centers.length;if(len==0){layer.alert("无用户中心数据！");return}var opts='<select id="search_text">';for(var i=0;i<len;i++){var center=centers[i];opts+="<option value="+center.id+">"+center.name+"</option>"}opts+="</select>";centerHtml=opts;$("#search_text").replaceWith(opts)}).fail(function(XMLHttpRequest,textStatus,errorThrown){if(XMLHttpRequest.readyState==4){var http_status=XMLHttpRequest.status;if(http_status==0||http_status>600){location.reload(true)}else{if(http_status==200){if(textStatus=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+textStatus)}}else{layer.alert("http connection error: status["+http_status+"], "+XMLHttpRequest.statusText)}}}})}else{$("#search_text").replaceWith(centerHtml)}}}});$("#search_btn").click(function(){var type=$("#search_type").val();if(type==0){layer.alert("请选择查询类型！",{icon:6})}else{var uuid="";var centerId=-2;if(type==1){uuid=$("#search_text").val();if(isNull(uuid)){layer.alert("请输入要查询的手机UUID！",{icon:6},function(index2){layer.close(index2);$("#search_text").select()});return}}else{centerId=$("#search_text").val()}showList(uuid,centerId,1)}});function refreshPage(){var pageId=$("#page_id").val();if(isNull(pageId)){pageId=1}var uuid=$("#auuid").val();if(isNull(uuid)){uuid=""}var centerId=$("#acenter").val();if(isNull(centerId)){centerId=-2}showList(uuid,centerId,pageId)}$("#page_id").change(function(){refreshPage()});$("#page_size").change(function(){var uuid=$("#auuid").val();if(isNull(uuid)){uuid=""}var centerId=$("#acenter").val();if(isNull(centerId)){centerId=-2}showList(uuid,centerId,1)})});