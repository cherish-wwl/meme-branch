/**
 * 判断是否为正数
 * @param value
 * @returns {Boolean}
 */
function isPositiveNumber(value){
	var str = new String(value);
	if(isNaN(str)){return false;}
	 if(str.substr(0,1) == "-"){return false;}
	 return true;
}

/**
 * 判断是否为整数
 * @param value
 * @returns {Boolean}
 */
function isInteger(value){
	var numchars = "1234567890-";
	var str=new String(value);
	var i;
	for (i=0;i<str.length;i++){
		if (numchars.indexOf(str.charAt(i))==-1) return false;
	}
	return true;
}

/**
 * 是否只为汉字
 * @param value
 * @returns {Boolean}
 */
function isCnCharacter(value){
	var str=new String(value);
    if (/^[\u4e00-\u9fa5]+$/.test(str)){
      return true;
    }
    return false;
}
/**
 * 小数中整数位数
 * @param value
 * @returns
 */
function integerNumInDecimal(value){
	var str=new String(value);
	if(str.indexOf(".")!=-1){
		var temp = str.substring(0,str.indexOf("."));
		return temp.length;
	}
	return 0;
}
/**
 * 小数中小数点后的位数
 * @param value
 * @returns {Boolean}
 */
function decimalNumInDecimal(value){
	var str=new String(value);
	if(str.indexOf(".")!=-1){
		var temp = str.substring(str.indexOf(".")+1,str.length);
		return temp.length;
	}
	return 0;
}