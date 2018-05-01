/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

$(".advertisement").each(function(i){
	
	if($(this).is("[size]")) {
		
		var width = $(this).attr("size").split("x")[0];
		var height = $(this).attr("size").split("x")[1];
		$(this).css("width", width + "px");
		$(this).css("height", height + "px");
	
	}
	
});