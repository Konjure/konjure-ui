/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

$(document).ready(function() {

	$("*").hover(function() {

		if($(this).is("[transition]")) {
			
			$(this).css("transition", $(this).attr("transition") + "s ease");
			
		}
		
		if($(this).is("[hover]")) {
			
			if(!$(this).hasClass("disabled")) {
		
				$(this).addClass($(this).attr("hover"));
			
			}
		
		}
		
	});

});