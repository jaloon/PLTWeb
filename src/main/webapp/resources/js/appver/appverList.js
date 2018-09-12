var dispatch;
var deleteAppver;

function getDefaultVer() {
    var ajaxFlag = true; //ajax执行结果标记，用于判断是否中断整个程序（return若放在ajax回调函数中，则无法跳出ajax）
    $.ajax({
        type: "get",
        async: false, //不异步，先执行完ajax，再干别的
        url: "../../manage/appver/getDefaultVer.do",
        dataType: "text",
        success: function (response) {
            $(".default-ver").remove();
            if (response == null || response == undefined || response.length == 0 || response == "[]") {
                $("#add_default_ver").before("");
            } else {
                var appvers = JSON.parse(response);
                var verHtml = "";
                appvers.forEach(function (appver) {
                    verHtml += "<tr class='default-ver'>" +
                        "<td>" + appver.appid + "</td>" +
                        "<td>" + appver.system + "</td>" +
                        "<td>" + appver.assignVer + "</td>" +
                        "<td>" + appver.minVer + "</td>" +
                        "<td>" +
                        "<img src='../../resources/images/operate/edit.png' alt=编辑 title='编辑' onclick=\"dispatch(false,'edit'," + appver.id + ")\">&emsp;" +
                        "<img src='../../resources/images/operate/delete.png' alt='删除' title='删除' onclick='deleteAppver(false," + appver.id + ")'>" +
                        "</td>" +
                        "</tr>";
                });
                $("#add_default_ver").before(verHtml);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            ajaxFlag = false;
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
    return ajaxFlag;
}

function showDefaultVer() {
    if (getDefaultVer()) {
        layer.open({
            type: 1,
            title: ['APP缺省版本管理（针对所有中心）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: '540px',
            content: $("#default_ver_box")
        });
    }
}

$(function () {

    $("#add_default_ver td span").click(function () {
        dispatch(false, "defaut", 0);
    });

    dispatch = function (refresh, mode, id) {
        var title = "添加APP版本信息";
        var h = "307px";
        if ("defaut" == mode) {
            h = "271px";
        }
        if ("edit" == mode) {
            title = "修改APP版本信息";
            // h = "271px";
        }
        if ("view" == mode) {
            title = "查看APP版本信息";
            h = "250px";
        }
        layer.open({
            type: 2,
            title: ['APP版本信息管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: '../../manage/appver/dispatch.do?mode=' + mode + '&id=' + id, //iframe的url
            end: function () {
                if (refresh) {
                    refreshPage();
                } else {
                    getDefaultVer();
                }
            }
        });
    }

    deleteAppver = function (refresh, id) {
        layer.confirm('删除后不可撤销，是否确认删除？', {
            icon: 0,
            title: ['删除', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function () {
            $.post("../../manage/appver/delete.do", "id=" + id,
                function (data) {
                    if ("success" == data.msg) {
                        layer.msg('删除成功！', {icon: 1, time: 500}, function () {
                           if (refresh) {
                               refreshPage();
                           } else {
                               getDefaultVer();
                           }
                        });
                    } else {
                        layer.msg('删除失败！', {icon: 2, time: 500});
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
    }

    function showList(center, pageId) {
        var rows = $("#page_size").val();
        var startRow = (pageId - 1) * rows;

        $.post(
            "../../manage/appver/ajaxFindForPage.do",
            "centerName=" + center + "&pageId=" + pageId + "&startRow=" + startRow + "&rows=" + rows,
            function (gridPage) {
                var maxIndex = $("#page_id option:last").index(); //获取Select最大的索引值
                var len = maxIndex + 1 - gridPage.total;
                if (len > 0) {
                    for (var i = gridPage.total > 0 ? gridPage.total : 1; i < maxIndex + 1; i++) {
                        $("#page_id option:last").remove(); //删除Select中索引值最大Option(最后一个)
                    }
                } else if (len < 0) {
                    for (var i = maxIndex + 2; i <= gridPage.total; i++) {
                        $("#page_id").append("<option value=" + i + ">" + i + "</option>"); //为Select追加一个Option下拉项
                    }
                }
                $("#page_id").val(gridPage.page);

                $("#page_info").html("页(" + gridPage.currentRows + "条数据)/共" + gridPage.total + "页(共" + gridPage.records + "条数据)");
                $("#avcenter").val(gridPage.t.centerName);
                $(".table-body").html("");
                var appvers = gridPage.dataList;
                var tableData = "<table width='100%'>";
                for (var i = 0; i < gridPage.currentRows; i++) {
                    var appver = appvers[i];
                    tableData += "<tr>" +
                        "<td class=\"appver-center\">" + appver.centerName + "</td>" +
                        "<td class=\"appver-appid\">" + appver.appid + "</td>" +
                        "<td class=\"appver-system\">" + appver.system + "</td>" +
                        "<td class=\"appver-assign\">" + appver.assignVer + "</td>" +
                        "<td class=\"appver-min\">" + appver.minVer + "</td>" +
                        "<td class=\"appver-action\">" +
                        "<img src=\"../../resources/images/operate/view.png\" alt=\"查看\" title=\"查看\" onclick=\"dispatch(false,'view'," + appver.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/edit.png\" alt=\"编辑\" title=\"编辑\" onclick=\"dispatch(true,'edit'," + appver.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/delete.png\" alt=\"删除\" title=\"删除\" onclick=\"deleteAppver(true," + appver.id + ")\">" +
                        "</td>" +
                        "</tr>";
                }
                tableData += "</table>";
                tableData = tableData.replace(/>null</g, "><");
                $(".table-body").html(tableData);
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
    }

    showList("", 1);

    $("#search_btn").click(function () {
        var center = $("#search_text").val();
        showList(center, 1);
    });

    function refreshPage() {
        var pageId = $("#page_id").val();
        if (isNull(pageId)) {
            pageId = 1;
        }
        var center = $("#avcenter").val();
        if (isNull(center)) {
            center = "";
        }
        showList(center, pageId);
    }

    $("#page_id").change(function () {
        refreshPage();
    });

    $("#page_size").change(function () {
        var center = $("#avcenter").val();
        if (isNull(center)) {
            center = "";
        }
        showList(center, 1);
    });
});