var dispatch;
var deleteDevice;
var centerHtml = "";

$(function() {
    dispatch = function(mode, id) {
        var title = "添加设备";
        var h = "379px";
        if ("edit" == mode) {
        	var h = "415px";
            title = "修改设备信息";
        }
        if ("view" == mode) {
            title = "查看设备信息";
            h = "358px";
        }
        layer.open({
            type: 2,
            title: ['设备管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: '../../manage/device/dispatch.do?mode=' + mode + '&id=' + id, //iframe的url
            end: function() {
                if (mode != "view") {
                    refreshPage();
                }
            }
        });
    }

    deleteDevice = function(id) {
        layer.confirm('删除后不可撤销，是否确认删除？', {
            icon: 0,
            title: ['删除', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function() {
            $.post("../../manage/device/delete.do", "id=" + id,
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

    function showList(deviceType, centerId, pageId) {
        var rows = $("#page_size").val();
        var startRow = (pageId - 1) * rows;

        $.post(
            "../../manage/device/ajaxFindForPage.do",
            "type=" + deviceType + "&centerId=" + centerId + "&pageId=" + pageId + "&startRow=" + startRow + "&rows=" + rows,
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
                $("#dtype").val(gridPage.t.type);
                $("#dcenter").val(gridPage.t.centerId);
                $(".table-body").html("");
                var devices = gridPage.dataList;
                var tableData = "<table width='100%'>";
                for (var i = 0; i < gridPage.currentRows; i++) {
                    var device = devices[i];
                    tableData += "<tr>" +
                        "<td class=\"device-id\">" + device.deviceId + "</td>" +
                        "<td class=\"device-type\">" + device.typeName + "</td>" +
                        "<td class=\"device-ver\">" + stringifyVer(device.ver) + "</td>" +
                        "<td class=\"device-model\">" + device.model + "</td>" +
                        "<td class=\"device-center\">" + device.centerName + "</td>" +
                        "<td class=\"device-produce\">" + device.produce + "</td>" +
                        "<td class=\"device-delivery\">" + device.delivery + "</td>" +
                        "<td class=\"device-remark\">" + device.remark + "</td>" +
                        "<td class=\"device-action\">" +
                        "<img src=\"../../resources/images/operate/view.png\" alt=\"查看\" title=\"查看\" onclick=\"dispatch('view'," + device.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/edit.png\" alt=\"编辑\" title=\"编辑\" onclick=\"dispatch('edit'," + device.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/delete.png\" alt=\"删除\" title=\"删除\" onclick=\"deleteDevice(" + device.id + ")\">" +
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

    $("#search_type").change(function() {
        $("#search_text").empty();
        var type = $("#search_type").val();
        if (type == 1) {
            $("#search_text").append(
                "<option value=1>车载终端</option>" +
                "<option value=2>锁</option>" +
                "<option value=3>出入库读卡器</option>" +
                "<option value=4>手持机</option>"
            );
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

    $("#search_btn").click(function() {
        var type = $("#search_type").val();
        if (type == 0) {
            layer.alert('请选择查询类型！', { icon: 6 });
        } else {
            var deviceType = -2;
            var centerId = -2;
            if (type == 1) {
                deviceType = $("#search_text").val();
            } else {
                centerId = $("#search_text").val();
            }
            showList(deviceType, centerId, 1);
        }
    });

    function refreshPage() {
        var pageId = $("#page_id").val();
        if (isNull(pageId)) {
            pageId = 1;
        }
        var deviceType = $("#dtype").val();
        if (isNull(deviceType)) {
            deviceType = -2;
        }
        var centerId = $("#dcenter").val();
        if (isNull(centerId)) {
            centerId = -2;
        }
        showList(deviceType, centerId, pageId);
    }

    $("#page_id").change(function() {
        refreshPage();
    });

    $("#page_size").change(function() {
        var deviceType = $("#dtype").val();
        if (isNull(deviceType)) {
            deviceType = -2;
        }
        var centerId = $("#dcenter").val();
        if (isNull(centerId)) {
            centerId = -2;
        }
        showList(deviceType, centerId, 1);
    });
});