var dispatch;var deleteAppdev;$(function(){dispatch=function(c,d){var f="添加APP设备信息";var e="451px";if("edit"==c){f="修改APP设备信息"}if("view"==c){f="查看APP设备信息";e="400px"}layer.open({type:2,title:["APP设备信息管理（"+f+"）","font-size:14px;color:#ffffff;background:#478de4;"],shade:0.8,area:["540px",e],content:"../../manage/appdev/dispatch.do?mode="+c+"&id="+d,end:function(){if(c!="view"){b()}}})};deleteAppdev=function(c){layer.confirm("删除后不可撤销，是否确认删除？",{icon:0,title:["删除","font-size:14px;color:#ffffff;background:#478de4;"]},function(){$.post("../../manage/appdev/delete.do","id="+c,function(d){if("success"==d.msg){layer.msg("删除成功！",{icon:1,time:500},function(){b()})}else{layer.msg("删除失败！",{icon:2,time:500})}},"json").fail(function(f,d,g){if(f.readyState==4){var e=f.status;if(e==0||e>600){location.reload(true)}else{if(e==200){if(d=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+d)}}else{layer.alert("http connection error: status["+e+"], "+f.statusText)}}}})})};function a(c,e,g){var d=$("#page_size").val();var f=(g-1)*d;$.post("../../manage/appdev/ajaxFindForPage.do","uuid="+c+"&owner="+e+"&pageId="+g+"&startRow="+f+"&rows="+d,function(n){var l=$("#page_id option:last").index();var k=l+1-n.total;if(k>0){for(var j=n.total>0?n.total:1;j<l+1;j++){$("#page_id option:last").remove()}}else{if(k<0){for(var j=l+2;j<=n.total;j++){$("#page_id").append("<option value="+j+">"+j+"</option>")}}}$("#page_id").val(n.page);$("#page_info").html("页("+n.currentRows+"条数据)/共"+n.total+"页(共"+n.records+"条数据)");$("#aduuid").val(n.t.uuid);$("#adowner").val(n.t.owner);$(".table-body").html("");var i=n.dataList;var h="<table width='100%'>";for(var j=0;j<n.currentRows;j++){var m=i[j];h+='<tr><td class="appdev-uuid">'+m.uuid+'</td><td class="appdev-appid">'+m.appid+'</td><td class="appdev-system">'+m.system+'</td><td class="appdev-model">'+m.model+'</td><td class="appdev-ver">'+m.currentVer+'</td><td class="appdev-owner">'+m.owner+'</td><td class="appdev-phone">'+m.phone+'</td><td class="appdev-duty">'+m.duty+'</td><td class="appdev-org">'+m.institution+'</td><td class="appdev-action"><img src="../../resources/images/operate/view.png" alt="查看" title="查看" onclick="dispatch(\'view\','+m.id+')">&emsp;<img src="../../resources/images/operate/edit.png" alt="编辑" title="编辑" onclick="dispatch(\'edit\','+m.id+')">&emsp;<img src="../../resources/images/operate/delete.png" alt="删除" title="删除" onclick="deleteAppdev('+m.id+')"></td></tr>'}h+="</table>";h=h.replace(/>null</g,"><");$(".table-body").html(h)},"json").fail(function(i,k,j){if(i.readyState==4){var h=i.status;if(h==0||h>600){location.reload(true)}else{if(h==200){if(k=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+k)}}else{layer.alert("http connection error: status["+h+"], "+i.statusText)}}}})}a("","",1);$("#search_type").change(function(){$("#search_text").val("");var c=$("#search_type").val();if(c==0){$("#search_text").attr("readonly",true)}else{$("#search_text").attr("readonly",false)}});$("#search_btn").click(function(){var d=$("#search_type").val();var c="";var e="";if(d==1){c=$("#search_text").val();if(isNull(c)){layer.alert("请输入要查询的手机UUID！",{icon:6},function(f){layer.close(f);$("#search_text").select()});return}}else{if(d==2){e=$("#search_text").val();if(isNull(e)){layer.alert("请输入要查询的机主姓名！",{icon:6},function(f){layer.close(f);$("#search_text").select()});return}}}a(c,e,1)});function b(){var c=$("#page_id").val();if(isNull(c)){c=1}var d=$("#aduuid").val();if(isNull(d)){d=""}var e=$("#adowner").val();if(isNull(e)){e=""}a(d,e,c)}$("#page_id").change(function(){b()});$("#page_size").change(function(){var d=$("#aduuid").val();if(isNull(d)){d=""}var c=$("#adowner").val();if(isNull(c)){c=-2}a(d,c,1)})});