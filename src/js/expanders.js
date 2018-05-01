/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

window.isChanged = false;

$(".expander").click(function() {
	
	$(this).toggleClass("expanded");
	
	// Icon changing

	if($(this).is("[change-icon]")) {
		
		window.newIcon = $(this).attr("change-icon");
		
		if(window.isChanged === false) {
			
			window.currentIcon = $(this).find("i").attr("class").split(" ")[1];
		
			$(this).find("i").removeClass(window.currentIcon);
			$(this).find("i").addClass(window.newIcon);
			window.isChanged = true;
			
		} else {
			
			$(this).find("i").removeClass(window.newIcon);
			$(this).find("i").addClass(window.currentIcon);
			window.isChanged = false;
			
		}
		
	}
	
});