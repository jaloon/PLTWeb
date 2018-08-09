var dispatch;
var deleteAppver;

$(function () {
    dispatch = function (mode, id) {
        var title = "添加APP版本信息";
        var h = "271px";
        if ("edit" == mode) {
            title = "修改APP版本信息";
            h = "271px";
        }
        if ("view" == mode) {
            title = "查看APP版本信息";
            h = "216px";
        }
        layer.open({
            type: 2,
            title: ['APP版本信息管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: '../../manage/appver/dispatch.do?mode=' + mode + '&id=' + id, //iframe的url
            end: function () {
                if (mode != "view") {
                    refreshPage();
                }
            }
        });
    }

    deleteAppver = function (id) {
        layer.confirm('删除后不可撤销，是否确认删除？', {
            icon: 0,
            title: ['删除', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function () {
            $.post("../../manage/appver/delete.do", "id=" + id,
                function (data) {
                    if ("success" == data.msg) {
                        layer.msg('删除成功！', {icon: 1, time: 500}, function () {
                            refreshPage();
                        });
                    } else {
                        layer.msg('删除失败！', {icon: 2, time: 500});
                    }
                },
                "json"
            );
        });
    }

    function showList(center, pageId) {
        var rows = $("#page_size").val();
        var startRow = (pageId - 1) * rows;

        $.post(
            "../../manage/appver/ajaxFindForPage.do",
            "centerName=" + center + "&pageId=" + pageId + "&startRow=" + startRow + "&rows=" + rows,
            function (data) {
                var gridPage = eval(data);

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
                var appdevs = gridPage.dataList;
                var tableData = "<table width='100%'>";
                for (var i = 0; i < gridPage.currentRows; i++) {
                    var appdev = appdevs[i];
                    tableData += "<tr>" +
                        "<td class=\"appver-center\">" + appdev.centerName + "</td>" +
                        "<td class=\"appver-system\">" + appdev.system + "</td>" +
                        "<td class=\"appver-assign\">" + appdev.assignVer + "</td>" +
                        "<td class=\"appver-min\">" + appdev.minVer + "</td>" +
                        "<td class=\"appver-action\">" +
                        "<img src=\"../../resources/images/operate/view.png\" alt=\"查看\" title=\"查看\" onclick=\"dispatch('view'," + appdev.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/edit.png\" alt=\"编辑\" title=\"编辑\" onclick=\"dispatch('edit'," + appdev.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/delete.png\" alt=\"删除\" title=\"删除\" onclick=\"deleteAppver(" + appdev.id + ")\">" +
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