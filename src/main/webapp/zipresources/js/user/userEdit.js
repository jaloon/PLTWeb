$(function(){var a=parent.layer.getFrameIndex(window.name);var b="修改成功！";var c="修改失败！";var d=$("#mode").val();$("#confirm").click(function(){if("pwd"==d){var q=$("#id").val();var e=$.trim($("#oldPwd").val());var p=$.trim($("#pwd").val());var j=$.trim($("#repwd").val());var o="../../manage/user/updatePwd.do";var f="id="+q+"&password="+p+"&oldPwd="+e;if(isNull(e)||isNull(p)||isNull(j)){layer.alert("密码不能为空！",{icon:5});return}if(p!=j){layer.alert("两次输入新密码不一致！",{icon:5});return}$.post(o,f,function(r){if("error"==r.msg){if("oldPwdError"==r.e){layer.alert("原密码不正确！",{icon:2},function(s){layer.close(s);$("#oldPwd").select()})}else{layer.msg(c,{icon:2,time:500})}}else{layer.msg(b,{icon:1,time:500},function(){parent.layer.close(a)})}},"json").fail(function(s,u,t){if(s.readyState==4){var r=s.status;if(r==0||r>600){location.reload(true)}else{if(r==200){if(u=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+u)}}else{layer.alert("http connection error: status["+r+"], "+s.statusText)}}}})}else{var q=$("#id").html();var m=$.trim($("#account").html());var l=$("#roleId").val();var k=$.trim($("#name").val());var n=$.trim($("#phone").val());var i=$.trim($("#identityCard").val());var h=$.trim($("#remark").val());var o="../../manage/user/update.do";var f="id="+q+"&account="+m+"&roleId="+l+"&name="+k+"&phone="+n+"&identityCard="+i+"&remark="+h;if("add"==d){m=$.trim($("#account").val());o="../../manage/user/add.do";f="account="+m+"&roleId="+l+"&name="+k+"&phone="+n+"&identityCard="+i+"&remark="+h;b="添加成功！";c="添加失败！";if(!isAccount(m)){layer.alert("账号不符规则！",{icon:5},function(r){layer.close(r);$("#account").select()});return}else{var g=true;$.ajax({type:"post",async:false,url:"../../manage/user/isExist.do",data:"account="+m,dataType:"json",success:function(r){if(r==true||r=="true"){g=false;layer.alert("账号已存在！",{icon:5},function(s){layer.close(s);$("#account").select()})}},error:function(s,u,t){g=false;if(s.readyState==4){var r=s.status;if(r==0||r>600){location.reload(true)}else{if(r==200){if(u=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+u)}}else{layer.alert("http connection error: status["+r+"], "+s.statusText)}}}}});if(!g){return}}}if(isNull(k)){layer.alert("姓名不能为空！",{icon:0},function(r){layer.close(r);$("#name").select()});return}$.post(o,f,function(r){if("error"==r.msg){layer.msg(c,{icon:2,time:500})}else{layer.msg(b,{icon:1,time:500},function(){parent.layer.close(a)})}},"json").fail(function(s,u,t){if(s.readyState==4){var r=s.status;if(r==0||r>600){location.reload(true)}else{if(r==200){if(u=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+u)}}else{layer.alert("http connection error: status["+r+"], "+s.statusText)}}}})}});$("#cancel").click(function(){parent.layer.close(a)})});