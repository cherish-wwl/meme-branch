function Map(){
	this.container=new Object();
}
Map.prototype.put=function(key,value){
	this.container[key]=value;
}
Map.prototype.get=function(key){
	return this.container[key];
}
Map.prototype.containsKey=function(key){
	if(this.container[key]) return true;
	return false;
}
Map.prototype.keySet=function(){
	var keys=new Array();
	var count=0;
	for(var key in this.container){
		if(key =='extend') continue;
		keys[count]=key;
		count++;
	}
	return keys;
}
Map.prototype.size=function(){
	var count=0;
	for(var key in this.container){
		if(key =='extend') continue;
		count++;
	}
	return count;
}
Map.prototype.remove=function(key){
	if(this.container[key]) delete this.container[key];
}
Map.prototype.clear=function(){
	for(var key in this.container){
		if(key =='extend') continue;
		delete this.container[key];
	}
}

/*function Map() {
	var struct = function (key, value) {
		this.key = key;
		this.value = value;
	}

	var put = function (key, value) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	}

	var get = function (key) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	}

	var remove = function (key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	}

	var size = function () {
		return this.arr.length;
	}

	var isEmpty = function () {
		return this.arr.length <= 0;
	}
	
	this.arr = new Array();
	this.get = get;
	this.put = put;
	this.remove = remove;
	this.size = size;
	this.isEmpty = isEmpty;
}*/