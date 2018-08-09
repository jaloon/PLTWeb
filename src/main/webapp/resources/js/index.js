var showList;

$(function() {
    showList = function(o) {
        window.showContent.location.href = "normal/" + o + "/" + o + "List.html"; //showContent当前页的iframe名字,js控制iframe中页面跳转
    }

    $(".profile-zone").mouseover(function() {
        $(".info-zone").css({
            "display": "block"
        })
    });
    $(".profile-zone").mouseout(function() {
        $(".info-zone").css({
            "display": "none"
        })
    });

    function setUser(mode, h) {
        var id = $("#id").val();
        var title = "修改个人信息";
        if ("pwd" == mode) {
            title = "修改密码";
        }

        layer.open({
            type: 2,
            title: [title, 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: 'manage/user/dispatch.do?mode=' + mode + '&id=' + id
        });
    }

    $("#info-edit").click(function() {
        setUser("info", "379px");
    });

    $("#pwd-edit").click(function() {
        setUser("pwd", "235px");
    });

    $("#logout").click(function() {
        layer.confirm('您确定要退出系统吗？', {
            icon: 3,
            title: ['退出', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function() {
            $.post(
                "manage/session/logout.do",
                function(data) {
                    if (data && data.e) {
                        layer.alert(data.msg, { icon: 5 });
                    } else {
                        document.location.href = "login.jsp";
                    }
                }
            ).fail(function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.readyState == 4) {
                    var http_status = XMLHttpRequest.status;
                    if (http_status == 0 || http_status > 600) {
                        location.reload(true);
                    } else if (http_status == 200) {
                        if (textStatus == "parsererror") {
                            layer.alert("应答数据格式解析错误！")
                        } else {
                            layer.alert("http response error: " + textStatus)
                        }
                    } else {
                        layer.alert("http connection error: status[" + http_status + "], " + XMLHttpRequest.statusText)
                    }
                }
            });

        });
    });

    $(".left-nav dt").append("<img src='resources/images/navbar/nav_right_12.png' />");
    $(".left-nav dd").hide();

    $(".left-nav dt").click(function() {
        $(".left-nav dt").css({
            "background-color": "#445065"
        });
        $(this).css({
            "background-color": "#478de4"
        });
        $(this).parent().find('dd').removeClass("menu_chioce");
        $(".left-nav dt img").attr("src", "resources/images/navbar/nav_right_12.png");
        $(this).parent().find('img').attr("src", "resources/images/navbar/nav_down_12.png");
        $(".menu_chioce").slideUp();
        $(this).parent().find('dd').slideDown();
        $(this).parent().find('dd').addClass("menu_chioce");
    });

    $(".left-nav dt").mouseover(function() {
        if ($(this).parent().find('img').attr("src") == "resources/images/navbar/nav_down_12.png") {
            $(this).css({
                "background-color": "#478de4"
            });
        } else {
            $(this).css({
                "background-color": "#3e81d5"
            });
        }
    });

    $(".left-nav dt").mouseout(function() {
        if ($(this).parent().find('img').attr("src") == "resources/images/navbar/nav_down_12.png") {
            $(this).css({
                "background-color": "#478de4"
            });
        } else {
            $(this).css({
                "background-color": "#445065"
            });
        }
    });

    $(window).resize(function() {
        $("iframe").attr("height", $(window).height() - 66 + "px");
        $("iframe").attr("width", $(window).width() - 200 + "px");
    }).resize();
});