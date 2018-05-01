/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

var materialInk, d, x, y;

$(".button.material").click(function(e){
	
	if($(this).find(".materialInk").length == 0)
		
		$(this).prepend("<span class='materialInk'></span>");
		materialInk = $(this).find(".materialInk");
		materialInk.removeClass("animate");

	if(!materialInk.height() && !materialInk.width()) {
		
		d = Math.max($(this).outerWidth(), $(this).outerHeight());
		materialInk.css({height: d, width: d});
		
	}

	x = e.pageX - $(this).offset().left - materialInk.width() / 2;
	y = e.pageY - $(this).offset().top - materialInk.height() / 2;
	
	materialInk.css({top: y+'px', left: x+'px'}).addClass("animate");
	
});