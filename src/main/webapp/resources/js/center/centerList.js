var dispatch;
var deleteCenter;

$(function() {
    dispatch = function(mode, id) {
        var title = "添加用户中心";
        var h = "487px";
        if ("edit" == mode) {
            title = "修改用户中心信息";
            h = "523px";
        }
        if ("view" == mode) {
            title = "查看用户中心信息";
            h = "462px";
        }
        layer.open({
            type: 2,
            title: ['用户中心管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: '../../manage/center/dispatch.do?mode=' + mode + '&id=' + id, //iframe的url
            end: function() {
                if (mode != "view") {
                    refreshPage();
                }
            }
        });
    }

    deleteCenter = function(id) {
        layer.confirm('删除用户中心将连同该中心所属设备一起删除，且删除后不可撤销，是否确认删除？', {
            icon: 0,
            title: ['删除', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function() {
            $.post("../../manage/center/delete.do", "id=" + id,
                function(data) {
                    if ("success" == data.msg) {
                        layer.msg('删除成功！', { icon: 1, time: 500 }, function() {
                            refreshPage();
                        });
                    } else {
                        layer.msg('删除失败！', { icon: 2, time: 500 });
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

    function showList(centerName, pageId) {
        var rows = $("#page_size").val();
        var startRow = (pageId - 1) * rows;

        $.post(
            "../../manage/center/ajaxFindForPage.do",
            "name=" + centerName + "&pageId=" + pageId + "&startRow=" + startRow + "&rows=" + rows,
            function(gridPage) {
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
                $("#cname").val(gridPage.t.centerName);
                $(".table-body").html("");
                var centers = gridPage.dataList;
                var tableData = "<table width='100%'>";
                for (var i = 0; i < gridPage.currentRows; i++) {
                    var center = centers[i];
                    tableData += "<tr>" +
                        "<td class=\"center-id\">" + center.id + "</td>" +
                        "<td class=\"center-name\">" + center.name + "</td>" +
                        "<td class=\"center-ip\">" + center.ip + "</td>" +
                        "<td class=\"center-web\">" + center.webPort + "</td>" +
                        "<td class=\"center-tcp\">" + center.tcpPort + "</td>" +
                        "<td class=\"center-barrier\">" + center.barrierPort + "</td>" +
                        "<td class=\"center-ftp\">" + ftpConfigConverter(center.ftpConfig) + "</td>" +
                        "<td class=\"center-director\">" + center.director + "</td>" +
                        "<td class=\"center-phone\">" + center.phone + "</td>" +
                        "<td class=\"center-address\">" + center.address + "</td>" +
                        "<td class=\"center-remark\">" + center.remark + "</td>" +
                        "<td class=\"center-action\">" +
                        "<img src=\"../../resources/images/operate/view.png\" alt=\"查看\" title=\"查看\" onclick=\"dispatch('view'," + center.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/edit.png\" alt=\"编辑\" title=\"编辑\" onclick=\"dispatch('edit'," + center.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/delete.png\" alt=\"删除\" title=\"删除\" onclick=\"deleteAppdev(" + center.id + ")\">" +
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

    $("#search_btn").click(function() {
        var centerName = $("#search_text").val();
        if (isNull(centerName)) {
            layer.alert('请输入要查询的中心名称！', { icon: 6 }, function(index2) {
                layer.close(index2);
                $("#search_text").select();
            });
            return;
        }
        showList(centerName, 1);
    });

    function refreshPage() {
        var pageId = $("#page_id").val();
        if (isNull(pageId)) {
            pageId = 1;
        }
        var centerName = $("#cname").val();
        if (isNull(centerName)) {
            centerName = "";
        }
        showList(centerName, pageId);
    }

    $("#page_id").change(function() {
        refreshPage();
    })

    $("#page_size").change(function() {
        var centerName = $("#cname").val();
        if (isNull(centerName)) {
            centerName = "";
        }
        showList(centerName, 1);
    });
});