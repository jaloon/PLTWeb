$(function(){var y=parent.layer.getFrameIndex(window.name);var D;var E="修改成功！";var B="修改失败！";var z=$("#mode").val();var C="";var q="ftp",w="",F="",r="",s="",u="",t="";if("edit"==z){v();x()}function v(){q=$.trim($("#ftp_protocol").val());w=$.trim($("#ftp_host").val());F=$.trim($("#ftp_port").val())==""?"":parseInt($.trim($("#ftp_port").val()),10);r=$.trim($("#ftp_directory").val());s=$.trim($("#ftp_account").val());u=$.trim($("#ftp_password").val());t=$.trim($("#ftp_remark").val())}function x(){var a={protocol:q,host:w,port:F,directory:r,account:s,password:u,remark:t};C=encodeURIComponent(JSON.stringify(a));return a}$("#ftp").click(function(){var a="添加ftp配置";var b="361px";if("edit"==z){a="修改ftp配置"}else{if("view"==z){v();a="查看ftp配置";b="322px"}}D=layer.open({type:1,title:["用户中心管理（"+a+"）","font-size:14px;color:#ffffff;background:#478de4;"],area:["500px",b],content:$(".ftp"),end:A()})});function A(){$("#ftp_protocol").val(q);$("#ftp_host").val(w);$("#ftp_port").val(F);$("#ftp_directory").val(r);$("#ftp_account").val(s);$("#ftp_password").val(u);$("#ftp_remark").val(t)}$("#ftp_cancel").click(function(){A();layer.close(D)});$("#ftp_confirm").click(function(){var g=$.trim($("#ftp_protocol").val());var e=$.trim($("#ftp_host").val());var c=$.trim($("#ftp_port").val())==""?"":parseInt($.trim($("#ftp_port").val()),10);var f=$.trim($("#ftp_directory").val());var h=$.trim($("#ftp_account").val());var d=$.trim($("#ftp_password").val());var a=$.trim($("#ftp_remark").val());if(!isIP(e)){layer.alert("主机IP地址不正确！",{icon:2},function(i){layer.close(i);$("#ftp_host").select()});return}if(!isPort(c)){layer.alert("端口号不正确！",{icon:2},function(i){layer.close(i);$("#ftp_port").select()});return}if(!isFtpDir(f)){layer.alert("文件目录格式不正确！",{icon:2},function(i){layer.close(i);$("#ftp_directory").select()});return}if(isNull(h)){layer.alert("ftp账号不能为空！",{icon:2},function(i){layer.close(i);$("#ftp_account").select()});return}if(isNull(d)){layer.alert("ftp密码不能为空！",{icon:2},function(i){layer.close(i);$("#ftp_password").select()});return}v();var b=x();$("#ftp").html(ftpConfigConverter(b));layer.close(D)});$("#cancel").click(function(){parent.layer.close(y)});$("#confirm").click(function(){var e=$("#id").val();var d=$.trim($("#name").val());var k=$.trim($("#ip").val());var m=$.trim($("#web").val());var h=$.trim($("#tcp").val());var g=$.trim($("#barrier").val());var c=$.trim($("#director").val());var j=$.trim($("#phone").val());var i=$.trim($("#address").val());var l=$.trim($("#remark").val());var f="../../manage/center/update.do";var b="id="+e+"&name="+d+"&ip="+k+"&webPort="+m+"&tcpPort="+h+"&barrierPort="+g+"&director="+c+"&phone="+j+"&address="+i+"&remark="+l+"&ftp="+C;if("add"==z){f="../../manage/center/add.do";b="name="+d+"&ip="+k+"&webPort="+m+"&tcpPort="+port+"&barrierPort="+g+"&director="+c+"&phone="+j+"&address="+i+"&remark="+l+"&ftp="+C;E="添加成功！";B="添加失败！";if(isNull(d)){layer.alert("用户中心名称不能为空！",{icon:0},function(n){layer.close(n);$("#name").select()});return}else{var a=true;$.ajax({type:"post",async:false,url:"../../manage/center/isExist.do",data:"centerName="+d,dataType:"json",success:function(n){if(n==true||n=="true"){a=false;layer.alert("用户中心名称已存在！",{icon:5},function(o){layer.close(o);$("#name").select()})}},error:function(p,n,o){a=false;if(p.readyState==4){var H=p.status;if(H==0||H>600){location.reload(true)}else{if(H==200){if(n=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+n)}}else{layer.alert("http connection error: status["+H+"], "+p.statusText)}}}}});if(!a){return}}}if(!isIP(k)){layer.alert("IP地址不正确！",{icon:2},function(n){layer.close(n);$("#ip").select()});return}if(!isPort(m)){layer.alert("Web端口不正确！",{icon:2},function(n){layer.close(n);$("#web").select()});return}if(!isPort(h)){layer.alert("监管端口不正确！",{icon:2},function(n){layer.close(n);$("#tcp").select()});return}if(!isPort(g)){layer.alert("道闸端口不正确！",{icon:2},function(n){layer.close(n);$("#barrier").select()});return}if(isNull(C)){layer.alert("ftp配置不能为空！",{icon:2},function(n){layer.close(n);$("#ftp").click()});return}$.post(f,b,function(n){if("error"==n.msg){layer.msg(B,{icon:2,time:500})}else{layer.msg(E,{icon:1,time:500},function(){parent.layer.close(y)})}},"json").fail(function(p,n,o){if(p.readyState==4){var H=p.status;if(H==0||H>600){location.reload(true)}else{if(H==200){if(n=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+n)}}else{layer.alert("http connection error: status["+H+"], "+p.statusText)}}}})})});