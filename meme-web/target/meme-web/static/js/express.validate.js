//扩展验证规则
var additionalRules = {};
/**
 * 表单验证函数
 * @param formObj
 * @returns
 */
function validate(formObj) {
	// 合并验证规则
	if(typeof rulesSettings != 'undefined') {
		$.extend(true, rulesSettings, additionalRules);
		return formObj.validate({
			rules : rulesSettings,
			messages : {}
		});
	}else{
		return formObj.validate({
			rules : additionalRules,
			messages : {}
		});
	}
}