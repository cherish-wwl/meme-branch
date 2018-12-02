$.validator.addMethod("byteRangeLength", function(value, element, params) {
	var length = value.length;
    for(var i = 0; i < value.length; i++){
        if(value.charCodeAt(i) > 127){
            length++;
        }
    }
	return this.optional(element) || ( length >= params[0] && length <= params[1] );
}, $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串(一个中文字算2个字符串)"));

$.validator.addMethod("positiveInteger", function(value, element) {
	if(isPositiveNumber(value)&&isInteger(value)) return true;
	else return false;
}, "请输入正整数或者0");

$.validator.addMethod("cnCharacter", function(value, element) {
	return isCnCharacter(value);
}, "只允许输入汉字");

/**
 * 日期验证，参照冒泡排序，param为需要验证的日期控件的ID数组（按日期先后构建）
 */
$.validator.addMethod("dateCompare",function(value,element,param){
	var flag=false;
	for(var i=0;i<param.length;++i){
		if($('input[id="'+param[i]+'"]').val()!=''){
			for(var j=i+1;j<param.length-i;++j){
				if($('input[id="'+param[j]+'"]').val()!=''){
					var dateSmall=$('input[id="'+param[i]+'"]').val().replace(/-/g,"");
					var dateBig=$('input[id="'+param[j]+'"]').val().replace(/-/g,"");
					if(dateSmall>dateBig){
						flag=false;
						break;
					}else{
						flag=true;
					}
				}else{
					flag=true;
				}
			}
			if(flag==false){
				break;
			}
		}else{
			flag=true;
		}
	}
	return flag;
}, $.validator.format("请检查日期的先后顺序"));

$.validator.addMethod("float", function(value, element, params) {
	if(typeof params=='string'){
		params=eval(params);
	}
	var decNum=decimalNumInDecimal(value);
	var intNum=integerNumInDecimal(value);
	return this.optional(element) || ( intNum <= params[0] && decNum <= params[1] );
}, $.validator.format("整数位不超过{0}位,小数位不超过{1}位"));

$.validator.addMethod("numberRange", function(value, element, param) {
	var str=new String(value);
	return this.optional(element) || (str.length<=param);
}, $.validator.format("请输入位数不超过{0}位的数字"));

$.validator.addMethod("positive", function(value, element) {
	var str=new String(value);
	var reg = /^\d+(?=\.{0,1}\d+$|$)/;
	if(reg.test(str)) return true;
	return false;
}, $.validator.format("请输入正数"));

$.validator.addMethod("datetime", function(value, element) {
	return this.optional(element) || /^\d{4}-(?:0\d|1[0-2])-(?:[0-2]\d|3[01])( (?:[01]\d|2[0-3])\:[0-5]\d\:[0-5]\d)?$/.test(value);
}, $.validator.format("请输入格式正确的日期(yyyy-MM-dd HH:mm:ss)"));

$.validator.addMethod("folder", function(value, element) {
	var str=['\\','/',':','*','?','"','<','>','|'];
	for(var i=0;i<str.length;i++){
		if(value.indexOf(str[i])>=0) return false;
	}
	return true;
//	var reg = new RegExp('^[\\^/:*?"<>|]+$');
//	if(reg.test(value)) return false;
//	else return true;
}, '文件夹名称不能包含\\/:*?"<>| 这些特殊字符');