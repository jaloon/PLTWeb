var dispatch;var deleteDevice;var centerHtml="";$(function(){dispatch=function(b,a){var g="添加设备";var h="379px";if("edit"==b){var h="415px";g="修改设备信息"}if("view"==b){g="查看设备信息";h="358px"}layer.open({type:2,title:["设备管理（"+g+"）","font-size:14px;color:#ffffff;background:#478de4;"],shade:0.8,area:["540px",h],content:"../../manage/device/dispatch.do?mode="+b+"&id="+a,end:function(){if(b!="view"){c()}}})};deleteDevice=function(a){layer.confirm("删除后不可撤销，是否确认删除？",{icon:0,title:["删除","font-size:14px;color:#ffffff;background:#478de4;"]},function(){$.post("../../manage/device/delete.do","id="+a,function(b){if("success"==b.msg){layer.msg("删除成功！",{icon:1,time:500},function(){c()})}else{layer.msg("删除失败！",{icon:2,time:500})}},"json").fail(function(b,i,j){if(b.readyState==4){var h=b.status;if(h==0||h>600){location.reload(true)}else{if(h==200){if(i=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+i)}}else{layer.alert("http connection error: status["+h+"], "+b.statusText)}}}})})};function d(h,a,i){var b=$("#page_size").val();var j=(i-1)*b;$.post("../../manage/device/ajaxFindForPage.do","type="+h+"&centerId="+a+"&pageId="+i+"&startRow="+j+"&rows="+b,function(p){var g=$("#page_id option:last").index();var q=g+1-p.total;if(q>0){for(var r=p.total>0?p.total:1;r<g+1;r++){$("#page_id option:last").remove()}}else{if(q<0){for(var r=g+2;r<=p.total;r++){$("#page_id").append("<option value="+r+">"+r+"</option>")}}}$("#page_id").val(p.page);$("#page_info").html("页("+p.currentRows+"条数据)/共"+p.total+"页(共"+p.records+"条数据)");$("#dtype").val(p.t.type);$("#dcenter").val(p.t.centerId);$(".table-body").html("");var e=p.dataList;var f="<table width='100%'>";for(var r=0;r<p.currentRows;r++){var o=e[r];f+='<tr><td class="device-id">'+o.deviceId+'</td><td class="device-type">'+o.typeName+'</td><td class="device-ver">'+stringifyVer(o.ver)+'</td><td class="device-model">'+o.model+'</td><td class="device-center">'+o.centerName+'</td><td class="device-produce">'+o.produce+'</td><td class="device-delivery">'+o.delivery+'</td><td class="device-remark">'+o.remark+'</td><td class="device-action"><img src="../../resources/images/operate/view.png" alt="查看" title="查看" onclick="dispatch(\'view\','+o.id+')">&emsp;<img src="../../resources/images/operate/edit.png" alt="编辑" title="编辑" onclick="dispatch(\'edit\','+o.id+')">&emsp;<img src="../../resources/images/operate/delete.png" alt="删除" title="删除" onclick="deleteDevice('+o.id+')"></td></tr>'}f+="</table>";f=f.replace(/>null</g,"><");$(".table-body").html(f)},"json").fail(function(g,e,f){if(g.readyState==4){var l=g.status;if(l==0||l>600){location.reload(true)}else{if(l==200){if(e=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+e)}}else{layer.alert("http connection error: status["+l+"], "+g.statusText)}}}})}d("","",1);$("#search_type").change(function(){$("#search_text").empty();var a=$("#search_type").val();if(a==1){$("#search_text").append("<option value=1>车载终端</option><option value=2>锁</option><option value=3>出入库读卡器</option><option value=4>手持机</option>")}else{if(a==2){if(centerHtml.length==0){$.getJSON("../../manage/device/getCenterList.do",function(b){var i=b.length;if(i==0){layer.alert("无用户中心数据！");return}var k='<select id="search_text">';for(var l=0;l<i;l++){var j=b[l];k+="<option value="+j.id+">"+j.name+"</option>"}k+="</select>";centerHtml=k;$("#search_text").replaceWith(k)}).fail(function(b,i,j){if(b.readyState==4){var h=b.status;if(h==0||h>600){location.reload(true)}else{if(h==200){if(i=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+i)}}else{layer.alert("http connection error: status["+h+"], "+b.statusText)}}}})}else{$("#search_text").replaceWith(centerHtml)}}}});$("#search_btn").click(function(){var a=$("#search_type").val();if(a==0){layer.alert("请选择查询类型！",{icon:6})}else{var f=-2;var b=-2;if(a==1){f=$("#search_text").val()}else{b=$("#search_text").val()}d(f,b,1)}});function c(){var a=$("#page_id").val();if(isNull(a)){a=1}var f=$("#dtype").val();if(isNull(f)){f=-2}var b=$("#dcenter").val();if(isNull(b)){b=-2}d(f,b,a)}$("#page_id").change(function(){c()});$("#page_size").change(function(){var a=$("#dtype").val();if(isNull(a)){a=-2}var b=$("#dcenter").val();if(isNull(b)){b=-2}d(a,b,1)})});