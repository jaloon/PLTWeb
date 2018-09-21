var dispatch;
var deleteCenterdev;
var centerHtml = "";

$(function () {
    dispatch = function (mode, id) {
        var title = "添加APP归属信息";
        var h = "199px";
        if ("edit" == mode) {
            title = "修改APP归属信息";
        }
        if ("view" == mode) {
            title = "查看APP归属信息";
            h = "145px";
        }
        layer.open({
            type: 2,
            title: ['APP归属信息管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: '../../manage/centerdev/dispatch.do?mode=' + mode + '&id=' + id, //iframe的url
            end: function () {
                if (mode != "view") {
                    refreshPage();
                }
            }
        });
    }

    deleteCenterdev = function (id) {
        layer.confirm('删除后不可撤销，是否确认删除？', {
            icon: 0,
            title: ['删除', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function () {
            $.post("../../manage/centerdev/delete.do", "id=" + id,
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
    };

    function showList(uuid, center, pageId) {
        var rows = $("#page_size").val();
        var startRow = (pageId - 1) * rows;

        $.post(
            "../../manage/centerdev/ajaxFindForPage.do",
            "uuid=" + uuid + "&centerId=" + center + "&pageId=" + pageId + "&startRow=" + startRow + "&rows=" + rows,
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
                $("#cduuid").val(gridPage.t.uuid);
                $("#cdcenter").val(gridPage.t.centerId);
                $(".table-body").html("");
                var centerdevs = gridPage.dataList;
                var tableData = "<table width='100%'>";
                for (var i = 0; i < gridPage.currentRows; i++) {
                    var centerdev = centerdevs[i];
                    tableData += "<tr>" +
                        "<td class=\"centerdev-id\">" + centerdev.id + "</td>" +
                        "<td class=\"centerdev-uuid\">" + centerdev.uuid + "</td>" +
                        "<td class=\"centerdev-center\">" + centerdev.centerName + "</td>" +
                        "<td class=\"centerdev-action\">" +
                        "<img src=\"../../resources/images/operate/view.png\" alt=\"查看\" title=\"查看\" onclick=\"dispatch('view'," + centerdev.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/edit.png\" alt=\"编辑\" title=\"编辑\" onclick=\"dispatch('edit'," + centerdev.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/delete.png\" alt=\"删除\" title=\"删除\" onclick=\"deleteCenterdev(" + centerdev.id + ")\">" +
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

    showList("", "", 1);

    $("#search_type").change(function () {
        $("#search_text").val("");
        var type = $("#search_type").val();
        if (type == 0) {
            $("#search_text").replaceWith("<input type='text' id='search_text' value='所有' readonly>");
        } else if (type == 1) {
            $("#search_text").replaceWith("<input type='text' id='search_text'>");
        } else if (type == 2) {
            if (centerHtml.length == 0) {
                $.getJSON("../../manage/device/getCenterList.do",
                    function(centers) {
                        var len = centers.length;
                        if (len == 0) {
                            layer.alert("无用户中心数据！");
                            return;
                        }
                        var opts = "<select id=\"search_text\">";
                        for (var i = 0; i < len; i++) {
                            var center = centers[i];
                            opts += "<option value=" + center.id + ">" + center.name + "</option>";
                        }
                        opts += "</select>";
                        centerHtml = opts;
                        $("#search_text").replaceWith(opts);
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
            } else {
                $("#search_text").replaceWith(centerHtml);
            }
        }
    });

    $("#search_btn").click(function () {
        var type = $("#search_type").val();
        var uuid = "";
        var center = "";
        if (type == 1) {
            uuid = $("#search_text").val();
            if (isNull(uuid)) {
                layer.alert('请输入要查询的手机UUID！', {icon: 6}, function (index2) {
                    layer.close(index2);
                    $("#search_text").select();
                });
                return;
            }
        } else if (type == 2) {
            center = $("#search_text").val();
        }
        showList(uuid, center, 1);
    });

    function refreshPage() {
        var pageId = $("#page_id").val();
        if (isNull(pageId)) {
            pageId = 1;
        }
        var uuid = $("#cduuid").val();
        if (isNull(uuid)) {
            uuid = "";
        }
        var center = $("#cdcenter").val();
        if (isNull(center)) {
            center = "";
        }
        showList(uuid, center, pageId);
    }

    $("#page_id").change(function () {
        refreshPage();
    });

    $("#page_size").change(function () {
        var uuid = $("#cduuid").val();
        if (isNull(uuid)) {
            uuid = "";
        }
        var center = $("#cdcenter").val();
        if (isNull(center)) {
            center = -2;
        }
        showList(uuid, center, 1);
    });
});