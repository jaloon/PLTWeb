function isNull(a){if(a==undefined||a==null||a==""){return true}return false}function toHexId(d){var c=d.toString(16).toUpperCase();if(c.length<8){for(var b=0,a=8-c.length;b<a;b++){c="0"+c}}return c}function ftpConfigConverter(b){var a="";if(!isNull(b)){a+=b.protocol+"://";if(!isNull(b.account)){a+=b.account+":******@"}a+=b.host+":"+b.port+b.directory}return a}function stringifyVer(a){if(a==0){return""}return((a>>24)&255)+"."+((a>>16)&255)+"."+(a&65535)}function parseVerToInt(a){if(isNull(a)){return 0}var b=a.split(".");if(b.length!=3){throw"版本号字符串格式不正确！"}var e=parseInt(b[0],10);var d=parseInt(b[1],10);var c=parseInt(b[2],10);return((255&e)<<24)|((255&d)<<16)|(65535&c)};