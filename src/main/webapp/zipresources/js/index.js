var showList;$(function(){showList=function(b){window.showContent.location.href="normal/"+b+"/"+b+"List.html"};$(".profile-zone").mouseover(function(){$(".info-zone").css({display:"block"})});$(".profile-zone").mouseout(function(){$(".info-zone").css({display:"none"})});function a(d,b){var e=$("#id").val();var c="修改个人信息";if("pwd"==d){c="修改密码"}layer.open({type:2,title:[c,"font-size:14px;color:#ffffff;background:#478de4;"],shade:0.8,area:["540px",b],content:"manage/user/dispatch.do?mode="+d+"&id="+e})}$("#info-edit").click(function(){a("info","379px")});$("#pwd-edit").click(function(){a("pwd","235px")});$("#logout").click(function(){layer.confirm("您确定要退出系统吗？",{icon:3,title:["退出","font-size:14px;color:#ffffff;background:#478de4;"]},function(){$.post("manage/session/logout.do",function(b){if(b&&b.e){layer.alert(b.msg,{icon:5})}else{document.location.href="login.jsp"}}).fail(function(c,e,d){if(c.readyState==4){var b=c.status;if(b==0||b>600){location.reload(true)}else{if(b==200){if(e=="parsererror"){layer.alert("应答数据格式解析错误！")}else{layer.alert("http response error: "+e)}}else{layer.alert("http connection error: status["+b+"], "+c.statusText)}}}})})});$(".left-nav dt").append("<img src='resources/images/navbar/nav_right_12.png' />");$(".left-nav dd").hide();$(".left-nav dt").click(function(){$(".left-nav dt").css({"background-color":"#445065"});$(this).css({"background-color":"#478de4"});$(this).parent().find("dd").removeClass("menu_chioce");$(".left-nav dt img").attr("src","resources/images/navbar/nav_right_12.png");$(this).parent().find("img").attr("src","resources/images/navbar/nav_down_12.png");$(".menu_chioce").slideUp();$(this).parent().find("dd").slideDown();$(this).parent().find("dd").addClass("menu_chioce")});$(".left-nav dt").mouseover(function(){if($(this).parent().find("img").attr("src")=="resources/images/navbar/nav_down_12.png"){$(this).css({"background-color":"#478de4"})}else{$(this).css({"background-color":"#3e81d5"})}});$(".left-nav dt").mouseout(function(){if($(this).parent().find("img").attr("src")=="resources/images/navbar/nav_down_12.png"){$(this).css({"background-color":"#478de4"})}else{$(this).css({"background-color":"#445065"})}});$(window).resize(function(){$("iframe").attr("height",$(window).height()-66+"px");$("iframe").attr("width",$(window).width()-200+"px")}).resize()});