$(function() {

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var ftpLayer;

    var success_zh_text = "修改成功！";
    var error_zh_text = "修改失败！";
    var mode = $("#mode").val();

    var ftp = "";
    var ftp_protocol = "ftp",
        ftp_host = "",
        ftp_port = "",
        ftp_directory = "",
        ftp_account = "",
        ftp_password = "",
        ftp_remark = "";

    if ("edit" == mode) {
        getFtpConfig();
        parseFtpConfig();
    }

    function getFtpConfig() {
        ftp_protocol = $.trim($("#ftp_protocol").val());
        ftp_host = $.trim($("#ftp_host").val());
        ftp_port = $.trim($("#ftp_port").val()) == "" ? "" : parseInt($.trim($("#ftp_port").val()), 10);
        ftp_directory = $.trim($("#ftp_directory").val());
        ftp_account = $.trim($("#ftp_account").val());
        ftp_password = $.trim($("#ftp_password").val());
        ftp_remark = $.trim($("#ftp_remark").val());
    }

    function parseFtpConfig() {
        var ftpConfig = {
            protocol: ftp_protocol,
            host: ftp_host,
            port: ftp_port,
            directory: ftp_directory,
            account: ftp_account,
            password: ftp_password,
            remark: ftp_remark
        };
        ftp = encodeURIComponent(JSON.stringify(ftpConfig));
        return ftpConfig;
    }

    $("#ftp").click(function() {
        var title = "添加ftp配置";
        var h = "361px";
        if ("edit" == mode) {
            title = "修改ftp配置";
        } else if ("view" == mode) {
        	getFtpConfig();
            title = "查看ftp配置";
            h = "322px";
        }
        ftpLayer = layer.open({
            type: 1,
            title: ['用户中心管理（' + title + '）', 'font-size:14px;color:#ffffff;background:#478de4;'],
            area: ['500px', h],
            // moveOut: true,
            content: $('.ftp'),
            end: resetFtpConfig()
        });
    });

    function resetFtpConfig() {
        $("#ftp_protocol").val(ftp_protocol);
        $("#ftp_host").val(ftp_host);
        $("#ftp_port").val(ftp_port);
        $("#ftp_directory").val(ftp_directory);
        $("#ftp_account").val(ftp_account);
        $("#ftp_password").val(ftp_password);
        $("#ftp_remark").val(ftp_remark);
    }

    $("#ftp_cancel").click(function() {
        resetFtpConfig();
        layer.close(ftpLayer);
    });

    $("#ftp_confirm").click(function() {
    	var _ftp_protocol = $.trim($("#ftp_protocol").val());
        var _ftp_host = $.trim($("#ftp_host").val());
        var _ftp_port = $.trim($("#ftp_port").val()) == "" ? "" : parseInt($.trim($("#ftp_port").val()), 10);
        var _ftp_directory = $.trim($("#ftp_directory").val());
        var _ftp_account = $.trim($("#ftp_account").val());
        var _ftp_password = $.trim($("#ftp_password").val());
        var _ftp_remark = $.trim($("#ftp_remark").val());

        if (!isIP(_ftp_host)) {
            layer.alert('主机IP地址不正确！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ftp_host").select();
            });
            return;
        }

        if (!isPort(_ftp_port)) {
            layer.alert('端口号不正确！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ftp_port").select();
            });
            return;
        }

        if (!isFtpDir(_ftp_directory)) {
            layer.alert('文件目录格式不正确！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ftp_directory").select();
            });
            return;
        }

        if (isNull(_ftp_account)) {
            layer.alert('ftp账号不能为空！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ftp_account").select();
            });
            return;
        }

        if (isNull(_ftp_password)) {
            layer.alert('ftp密码不能为空！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ftp_password").select();
            });
            return;
        }
        getFtpConfig();
        var ftpConfig = parseFtpConfig();
        $("#ftp").html(ftpConfigConverter(ftpConfig));
        layer.close(ftpLayer);
    });

    $("#cancel").click(function() {
        parent.layer.close(index);
    });

    $("#confirm").click(function() {
        var id = $("#id").val();
        var name = $.trim($("#name").val());
        var ip = $.trim($("#ip").val());
        var web = $.trim($("#web").val());
        var tcp = $.trim($("#tcp").val());
        var barrier = $.trim($("#barrier").val());
        var director = $.trim($("#director").val());
        var phone = $.trim($("#phone").val());
        var address = $.trim($("#address").val());
        var remark = $.trim($("#remark").val());
        var url = "../../manage/center/update.do";
        var param = "id=" + id + "&name=" + name + "&ip=" + ip + "&webPort=" + web + "&tcpPort=" + tcp + "&barrierPort=" + barrier + "&director=" + director +
            "&phone=" + phone + "&address=" + address + "&remark=" + remark + "&ftp=" + ftp;

        if ("add" == mode) {
            url = "../../manage/center/add.do";
            param = "name=" + name + "&ip=" + ip + "&webPort=" + web + "&tcpPort=" + port + "&barrierPort=" + barrier + "&director=" + director +
                "&phone=" + phone + "&address=" + address + "&remark=" + remark + "&ftp=" + ftp;
            success_zh_text = "添加成功！"
            error_zh_text = "添加失败！"
            if (isNull(name)) {
                layer.alert('用户中心名称不能为空！', { icon: 0 }, function(index2) {
                    layer.close(index2);
                    $("#name").select();
                });
                return;
            } else {
                var ajaxFlag = true; //ajax执行结果标记，用于判断是否中断整个程序（return若放在ajax回调函数中，则无法跳出ajax）
                $.ajax({
                    type: "post",
                    async: false, //不异步，先执行完ajax，再干别的
                    url: "../../manage/center/isExist.do",
                    data: "centerName=" + name,
                    dataType: "json",
                    success: function(response) {
                        if (response == true || response == "true") {
                            ajaxFlag = false;
                            layer.alert('用户中心名称已存在！', { icon: 5 }, function(index2) {
                                layer.close(index2);
                                $("#name").select();
                            });
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
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
                if (!ajaxFlag) {
                    return;
                }
            }
        }

        if (!isIP(ip)) {
            layer.alert('IP地址不正确！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ip").select();
            });
            return;
        }

        if (!isPort(web)) {
            layer.alert('Web端口不正确！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#web").select();
            });
            return;
        }
        
        if (!isPort(tcp)) {
        	layer.alert('监管端口不正确！', { icon: 2 }, function(index2) {
        		layer.close(index2);
        		$("#tcp").select();
        	});
        	return;
        }
        
        if (!isPort(barrier)) {
        	layer.alert('道闸端口不正确！', { icon: 2 }, function(index2) {
        		layer.close(index2);
        		$("#barrier").select();
        	});
        	return;
        }

        if (isNull(ftp)) {
            layer.alert('ftp配置不能为空！', { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ftp").click();
            });
            return;
        }

        // if (isNull(director)) {
        //     layer.alert('负责人不能为空！', { icon: 0 }, function(index2) {
        //         layer.close(index2);
        //         $("#director").select();
        //     });
        //     return;
        // }
        //
        // if (!isPhone(phone)) {
        //     layer.alert('电话号码不正确！', { icon: 5 }, function(index2) {
        //         layer.close(index2);
        //         $("#phone").select();
        //     });
        //     return;
        // }
        //
        // if (isNull(address)) {
        //     layer.alert('地址不能为空！', { icon: 0 }, function(index2) {
        //         layer.close(index2);
        //         $("#address").select();
        //     });
        //     return;
        // }

        $.post(url, param,
            function(data) {
                if ("error" == data.msg) {
                    layer.msg(error_zh_text, { icon: 2, time: 500 });
                } else {
                    layer.msg(success_zh_text, { icon: 1, time: 500 }, function() {
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