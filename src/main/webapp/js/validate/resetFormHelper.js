function resetValidatorClass(){
	$("input").removeClass("valid").removeClass("error");
	$("select").removeClass("valid").removeClass("error");
	$("b.error").remove();
}