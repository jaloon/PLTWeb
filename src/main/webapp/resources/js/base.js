/**
 * 空对象判断
 * @param {*} o
 */
function isNull(o) {
    if (o == undefined || o == null || o == "") {
        return true;
    }
    return false;
}

/**
 * 十进制ID转为十六进制
 * @param {number} id
 */
function toHexId(id) {
    var hexId = id.toString(16).toUpperCase();
    if (hexId.length < 8) {
        for (var i = 0, len = 8 - hexId.length; i < len; i++) {
            hexId = "0" + hexId;
        }
    }
    return hexId;
}

/**
 * Ftp配置转路径
 * @param {*} ftpConfig
 * @returns {string} ftp路径
 */
function ftpConfigConverter(ftpConfig){
	var ftpUrl = "";
	if (!isNull(ftpConfig)) {
		ftpUrl += ftpConfig.protocol + "://";
		if (!isNull(ftpConfig.account)) {
			ftpUrl += ftpConfig.account + ":******@";
		}
		ftpUrl += ftpConfig.host + ":" + ftpConfig.port + ftpConfig.directory;
	}
	return ftpUrl;
}

/**
 * 将整形数版本号转为版本号字符串
 * @param ver {number} 整形数版本号
 * @returns {string} 版本号字符串（格式：1.2.3456）
 */
function stringifyVer(ver) {
    if (ver == 0) {
        return "";
    }
    return ((ver >> 24) & 0xff) + '.'
        + ((ver >> 16) & 0xff) + '.'
        + (ver & 0xffff);
}

/**
 * 将版本号字符串解析为整形数版本号
 * @param verStr {string} 版本号字符串（格式：1.2.3456）
 * @returns {number} 整形数版本号
 */
function parseVerToInt(verStr) {
	if (isNull(verStr)) {
		return 0;
	}
	var verArr = verStr.split("\.");
	if (verArr.length != 3) {
		throw "版本号字符串格式不正确！";
	}
	var v1 = parseInt(verArr[0], 10);
	var v2 = parseInt(verArr[1], 10);
	var v3 = parseInt(verArr[2], 10);
	return ((0xff & v1) << 24) | ((0xff & v2) << 16) | (0xffff & v3);
}