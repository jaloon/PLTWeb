$(function() {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

    var success_zh_text = "修改成功！"
    var error_zh_text = "修改失败！"
    var mode = $("#mode").val();

    $("#cancel").click(function() {
        parent.layer.close(index);
    });

    $("#confirm").click(function() {
        var id = $("#id").val();
        var type = $("#type").val();
        var ver;
        try{
        	ver = parseVerToInt($("#ver").val());
        }catch (e) {
        	layer.alert(e, { icon: 2 }, function(index2) {
                layer.close(index2);
                $("#ver").select();
            });
            return;
		}
        var model = $.trim($("#model").val());
        var centerId = $("#center").val();
        var produce = $.trim($("#produce").val());
        var delivery = $.trim($("#delivery").val());
        var remark = $.trim($("#remark").val());
        var url = "../../manage/device/update.do";
        var param = "id=" + id + "&type=" + type + "&ver=" + ver + "&model=" + model + 
        	"&centerId=" + centerId + "&produce=" + produce + "&delivery=" + delivery + "&remark=" + remark;

        if ("add" == mode) {
        	typeName = $("#type").find("option:selected").text();
            url = "../../manage/device/add.do";
            param = "type=" + type + "&ver=" + ver + "&model=" + model + "&centerId=" + centerId + 
            	"&produce=" + produce + "&delivery=" + delivery + "&remark=" + remark;
            success_zh_text = "添加成功！";
            error_zh_text = "添加失败！";
        }
        
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