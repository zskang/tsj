/**** 对超长的字符串进行截取 ****/
function intercepteLongWors(val,length){
	if(val&&val.length<length){
		return val;
	}else if(val&&val.length>=length){
		val = val.substring(0,length);
		return val+"...";
	}else{
		return val;
	}
}