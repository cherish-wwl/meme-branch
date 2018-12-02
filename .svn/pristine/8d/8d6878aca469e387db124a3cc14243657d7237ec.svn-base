function showTime(id){
	var D = new Date();
	var T = { y: D.getFullYear(), m: D.getMonth() + 1, d: D.getDate(), H: D.getHours(), M: D.getMinutes(), S: D.getSeconds(),D:D.getDay() };
	var X = document.getElementById(id);
	var str= T.y+'年'+T.m+'月'+T.d+'日 '+'星期'
	+(T.D==0?'日':T.D==1?'一':T.D==2?'二':T.D==3?'三':T.D==4?'四':T.D==5?'五':'六')+'&nbsp;'
	+(T.H>9?T.H:('0'+T.H))+':'+(T.M>9?T.M:('0'+T.M))+':'+(T.S>9?T.S:('0'+T.S));
	X.innerHTML = str,window.setTimeout(function(){showTime(id);},1000);
}
