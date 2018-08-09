$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var success_zh_text = "修改成功！"
    var error_zh_text = "修改失败！"
    var mode = $("#mode").val();

    $("#cancel").click(function () {
        parent.layer.close(index);
    });

    $("#confirm").click(function () {
        var id = $("#id").val();
        var assign = $("#assign").val();
        var minver = $("#minver").val();

        var url = "../../manage/appver/update.do";
        var param = "id=" + id + "&assignVer=" + assign + "&minVer=" + minver;

        if ("add" == mode) {
            var center = $("#center").val();
            var system = $("#system").val();
            url = "../../manage/appver/add.do";
            param = "centerId=" + center + "&system=" + system + "&assignVer=" + assign + "&minVer=" + minver;
            success_zh_text = "添加成功！";
            error_zh_text = "添加失败！";

            var ajaxFlag = true; //ajax执行结果标记，用于判断是否中断整个程序（return若放在ajax回调函数中，则无法跳出ajax）
            $.ajax({
                type: "post",
                async: false, //不异步，先执行完ajax，再干别的
                url: "../../manage/appver/isExist.do",
                data: "centerId=" + center + "&system=" + system,
                dataType: "json",
                success: function (response) {
                    if (response == true || response == "true") {
                        ajaxFlag = false;
                        layer.alert('用户中心' + system + 'APP版本配置信息已存在！', {icon: 5}, function (index2) {
                            layer.close(index2);
                            $("#center").select();
                        });
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
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
                }
            });
            if (!ajaxFlag) {
                return;
            }
        }

        if (!isVer(assign)) {
            layer.alert('指定版本不符合版本号格式！', {icon: 2}, function (index2) {
                layer.close(index2);
                $("#assign").select();
            });
            return;
        }
        if (!isVer(minver)) {
            layer.alert('最低版本不符合版本号格式！', {icon: 2}, function (index2) {
                layer.close(index2);
                $("#minver").select();
            });
            return;
        }

        $.post(url, param,
            function (data) {
                if ("error" == data.msg) {
                    layer.msg(error_zh_text, {icon: 2, time: 500});
                } else {
                    layer.msg(success_zh_text, {icon: 1, time: 500}, function () {
                        parent.layer.close(index);
                    });
                }
            },
            "json"
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