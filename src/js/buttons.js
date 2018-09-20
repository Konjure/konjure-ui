/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

$(document).ready(function() {

	var materialEffect, d, x, y;

	$(".material").click(function(e){
		
		if($(this).find(".materialEffect").length == 0)
			
			$(this).prepend("<span class='materialEffect'></span>");
			materialEffect = $(this).find(".materialEffect");
			materialEffect.removeClass("animate");

		if(!materialEffect.height() && !materialEffect.width()) {
			
			d = Math.max($(this).outerWidth(), $(this).outerHeight());
			materialEffect.css({height: d, width: d});
			
		}

		x = e.pageX - $(this).offset().left - materialEffect.width() / 2;
		y = e.pageY - $(this).offset().top - materialEffect.height() / 2;
		
		materialEffect.css({top: y+'px', left: x+'px'}).addClass("animate");
		
	});

});