/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

$(document).ready(function() {

	$(".inner-bar").each(function(i) {
		
		var $hideBar = $("<div>", {"class": "hide-bar"});
		var $animateBar = $("<div>", {"class": "animate-bar"});
		var $innerBarLabel = $("<div>", {"class": "inner-bar-label"});
		var transitionTicks = $(this).attr("transition") * 1000;

		if($(this).is("[progress]")) {
			
			if($(this).is("[transition]")) {
				
				$(this).append($hideBar);
				var progress = $(this).attr("progress");
				$(this).css("transition", $(this).attr("transition") + "s ease");
				
				$(this).children(".hide-bar").animate({"width":100 - progress + "%"}, transitionTicks, function() {
					
					if($(this).parent().hasClass("error")) {
						
						$(this).parent().parent().css("background-color", "var(--bar-error-color)");
						$(this).parent().animate({"opacity":"0.0"}, transitionTicks / 4);
						
					} else if($(this).parent().hasClass("warning")) {
						
						$(this).parent().parent().css("background-color", "var(--bar-warning-color)");
						$(this).parent().animate({"opacity":"0.0"}, transitionTicks / 4);
						
					} else {
						
						if(progress === "100" || $(this).parent().hasClass("success")) {

							$(this).parent().parent().css("background-color", "var(--bar-completed-color)");
							$(this).parent().animate({"opacity":"0.0"}, transitionTicks / 4);
							
						}
						
					}
					
				});
				
			} else {
				
				$(this).append($hideBar);
				var progress = $(this).attr("progress");
				$(this).children(".hide-bar").css("width", 100 - progress + "%");
				
				if($(this).hasClass("error")) {
					
					$(this).parent().css("background-color", "var(--bar-error-color)");
					$(this).css("opacity", "0.0");
					
				} else if($(this).hasClass("warning")) {
					
					$(this).parent().css("background-color", "var(--bar-warning-color)");
					$(this).css("opacity", "0.0");
					
				} else {
					
					if(progress === "100" || $(this).hasClass("success")) {
						
						$(this).parent().css("background-color", "var(--bar-completed-color)");
						$(this).css("opacity", "0.0");
						
					}
					
				}
				
			}
			
			if($(this).is("[animate]")) {

				$(this).append($animateBar);
			
			}
			
			/*
			
			if($(this).is("[label='percentage']")) {

				$(this).children(".hide-bar").append($innerBarLabel);
				$(this).find(".inner-bar-label").text(progress + "%");
				$(this).find(".inner-bar-label").css("opacity", "1.0");
			
			} else if($(this).is("[label='status']")) {
				
				// Status alternative
				
			}
			
			*/
		
		}

	});

});