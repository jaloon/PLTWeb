$(function(){var a=parent.layer.getFrameIndex(window.name);var b="修改成功！";var c="修改失败！";var d=$("#mode").val();$("#cancel").click(function(){parent.layer.close(a)});$("#confirm").click(function(){var h=$("#id").val();var m=$("#assign").val();var i=$("#minver").val();var g="../../manage/appver/update.do";var j="id="+h+"&assignVer="+m+"&minVer="+i;if("add"==d||"defaut"==d){var e=$("#center").val();var l=$("#appid").val();var f=$("#system").val();g="../../manage/appver/add.do";j="centerId="+e+"&appid="+l+"&system="+f+"&assignVer="+m+"&minVer="+i;b="添加成功！";c="添加失败！";if(l===""){layer.alert("应用标识不能为空！",{icon:2},function(n){layer.close(n);$("#appid").select()});return}if(f===""){layer.alert("手机系统不能为空！",{icon:2},function(n){layer.close(n);$("#system").select()});return}var k=true;$.ajax({type:"post",async:false,url:"../../manage/appver/isExist.do",data:"centerId="+e+"&appid="+l+"&system="+f,dataType:"json",success:function(n){if(n==true||n=="true"){k=false;layer.alert(("add"==d?"用户中心[":"[")+f+"]APP版本配置信息已存在！",{icon:5},function(o){layer.close(o);$("#center").select()})}},error:function(o,q,p){k=false;if(o.readyState==4){var n=o.status;if(n==0||n>600){location.reload(true)}else{if(n==200){if(q=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+q)}}else{layer.alert("http connection error: status["+n+"], "+o.statusText)}}}}});if(!k){return}}if(!isVer(m)){layer.alert("指定版本不符合版本号格式！",{icon:2},function(n){layer.close(n);$("#assign").select()});return}if(!isVer(i)){layer.alert("最低版本不符合版本号格式！",{icon:2},function(n){layer.close(n);$("#minver").select()});return}$.post(g,j,function(n){if("error"==n.msg){layer.msg(c,{icon:2,time:500})}else{layer.msg(b,{icon:1,time:500},function(){parent.layer.close(a)})}},"json").fail(function(o,q,p){if(o.readyState==4){var n=o.status;if(n==0||n>600){location.reload(true)}else{if(n==200){if(q=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+q)}}else{layer.alert("http connection error: status["+n+"], "+o.statusText)}}}})})});