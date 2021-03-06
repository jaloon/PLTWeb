var dispatch;
var deleteUser;
var resetPwd;

$(function() {
    dispatch = function(mode, id) {
        var title = "添加操作员";
        var h = "343px";
        if ("edit" == mode) {
            title = "修改操作员信息";
            h = "379px";
        }
        if ("view" == mode) {
            title = "查看操作员信息";
            h = "321px";
        }
        layer.open({
            type: 2,
            title: ['操作员管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            // shadeClose: true,
            shade: 0.8,
            area: ['540px', h],
            content: '../../manage/user/dispatch.do?mode=' + mode + '&id=' + id, //iframe的url
            end: function() {
                if (mode != "view") {
                    refreshPage();
                }
            }
        });
    }

    deleteUser = function(id) {
        layer.confirm('删除后不可撤销，是否确认删除？', {
            icon: 0,
            title: ['删除', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function() {
            $.post("../../manage/user/delete.do", "id=" + id,
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

    resetPwd = function(id) {
        layer.confirm('确认重置密码？', {
            icon: 4,
            title: ['密码重置', 'font-size:14px;color:#ffffff;background:#478de4;']
        }, function() {
            $.post("../../manage/user/reset.do", "id=" + id,
                function(data) {
                    if ("success" == data.msg) {
                        layer.msg('重置成功！', { icon: 1, time: 500 });
                    } else {
                        layer.msg('重置失败！', { icon: 2, time: 500 });
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

    function showList(account, name, pageId) {
        var rows = $("#page_size").val();
        var startRow = (pageId - 1) * rows;

        $.post(
            "../../manage/user/ajaxFindForPage.do",
            "account=" + account + "&name=" + name + "&pageId=" + pageId + "&startRow=" + startRow + "&rows=" + rows,
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
                $("#uaccount").val(gridPage.t.account);
                $("#uname").val(gridPage.t.name);
                $(".table-body").html("");
                var users = gridPage.dataList;
                var tableData = "<table width='100%'>";
                for (var i = 0; i < gridPage.currentRows; i++) {
                    var user = users[i];
                    tableData += "<tr>" +
                        "<td class=\"user-id\">" + user.id + "</td>" +
                        "<td class=\"user-account\">" + user.account + "</td>" +
                        "<td class=\"user-role\">" + user.role.name + "</td>" +
                        "<td class=\"user-name\">" + user.name + "</td>" +
                        "<td class=\"user-phone\">" + user.phone + "</td>" +
                        "<td class=\"user-card\">" + user.identityCard + "</td>" +
                        "<td class=\"user-remark\">" + user.remark + "</td>" +
                        "<td class=\"user-action\">" +
                        "<img src=\"../../resources/images/operate/view.png\" alt=\"查看\" title=\"查看\" onclick=\"dispatch('view'," + user.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/edit.png\" alt=\"编辑\" title=\"编辑\" onclick=\"dispatch('edit'," + user.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/delete.png\" alt=\"删除\" title=\"删除\" onclick=\"deleteUser(" + user.id + ")\">&emsp;" +
                        "<img src=\"../../resources/images/operate/repwd.png\" alt=\"重置密码\" title=\"重置密码\" onclick=\"resetPwd(" + user.id + ")\">" +
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

    $("#search_btn").click(function() {
        var type = $("#search_type").val();
        if (type == 0) {
            layer.alert('请选择查询类型！', { icon: 6 });
        } else {
            var account = "";
            var name = "";
            if (type == 1) {
                account = $.trim($("#search_text").val());
                if (isNull(account)) {
                    layer.alert('请输入要查询的账号！', { icon: 6 }, function(index2) {
                        layer.close(index2);
                        $("#search_text").select();
                    });
                }
            } else {
                name = $.trim($("#search_text").val());
                if (isNull(name)) {
                    layer.alert('请输入要查询的姓名！', { icon: 6 }, function(index2) {
                        layer.close(index2);
                        $("#search_text").select();
                    });
                }
            }
            showList(account, name, 1);
        }
    });

    function refreshPage() {
        var pageId = $("#page_id").val();
        if (isNull(pageId)) {
            pageId = 1;
        }
        var account = $("#uaccount").val();
        if (isNull(account)) {
            account = "";
        }
        var name = $("#uname").val();
        if (isNull(name)) {
            name = "";
        }
        showList(account, name, pageId);
    }

    $("#page_id").change(function() {
        refreshPage();
    })

    $("#page_size").change(function() {
        var account = $("#uaccount").val();
        if (isNull(account)) {
            account = "";
        }
        var name = $("#uname").val();
        if (isNull(name)) {
            name = "";
        }
        showList(account, name, 1);
    });
});