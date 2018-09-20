/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

$(document).ready(function() {

	$(".expander > h1, .expander > h2, .expander > h3, .expander > h4, .expander > h5, .expander > h6, .expander > p, .expander > i").click(function() {
		
		$(this).parent().toggleClass("expanded");
		
		// Icon changing

		if($(this).parent().is("[change-icon]")) {
			
			window.newIcon = $(this).parent().attr("change-icon");
			
			if($(this).parent().hasClass("expanded")) {
				
				window.currentIcon = $(this).parent().find("i").attr("class").split(" ")[1];
			
				$(this).parent().find("i").removeClass(window.currentIcon);
				$(this).parent().find("i").addClass(window.newIcon);
				window.isChanged = true;
				
			} else {
				
				$(this).parent().find("i").removeClass(window.newIcon);
				$(this).parent().find("i").addClass(window.currentIcon);
				window.isChanged = false;
				
			}
			
		}
		
	});

});