/*

	* Copyright (c) 2018 Konjure
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

// Image popup

window.popped = false;
window.effect = "none";
window.imgHeight = 0;
window.imgPosition = "";

// Close popup

closePopup = function() {
	
	$(".popup").empty().remove();
	$(".pop").removeClass("original");
	$("body").css("top", -(document.documentElement.scrollTop) + "px").removeClass("dim");
	$(window).scrollTop(window.scroll);
	window.popped = false;
	
};

// Add arrows to popup

addArrows = function() {
	
	$(".popup").append($arrowLeft, $arrowRight);
	window.imgHeight = ($(".open").height() / 2) - ($(".arrow-left").height() / 2);
	$(".arrow-left, .arrow-right").css("top", window.imgHeight);
	$(".arrow-left").show();
	$(".arrow-right").show();
	
};

// Position arrows and caption based on image height

positionAddons = function() {
	
	window.imgHeight = ($(".open").height() / 2) - ($(".arrow-left").height() / 2);
	var captionMargin = -Math.abs($(".caption").outerHeight());
	
	$(".arrow-left, .arrow-right").css("top", window.imgHeight);
	$(".caption").css("marginTop", captionMargin);
	
};

// Change what the original clicked image is

setImgPosition = function() {
	
	if(!$(".original").parentsUntil().hasClass("wrap")) {
	
		if($(".original").parent().is(":first-child")) {
			
			window.imgPosition = "first";
			
		} else if($(".original").parent().is(":last-child")) {
			
			window.imgPosition = "last";
			
		} else if(!($(".original").parent().is(":first-child") || $(".original").parent().is(":last-child"))) {
			
			window.imgPosition = "middle";
			
		} else if($(".original").parent().is(":first-child") && $(".original").parent().is(":last-child")) {
			
			window.imgPosition = "only";
			
		}
	
	}
	
};

changePrevOriginal = function() {
	
	$(".original").addClass("old-original");
	$(".original").parent().prev().find("img").addClass("original");
	$(".old-original").removeClass("original old-original");
	setImgPosition();
	
};

changeNextOriginal = function() {
	
	$(".original").addClass("old-original");
	$(".original").parent().next().find("img").addClass("original");
	$(".old-original").removeClass("original old-original");
	setImgPosition();
	
};

updateArrows = function() {
	
	if(!$(".original").parentsUntil().hasClass("wrap")) {

		if(window.imgPosition === "first") {
			
			$(".arrow-left").hide();
			
		} else if(window.imgPosition === "last") {
			
			$(".arrow-right").hide();
			
		} else if(window.imgPosition === "middle") {
			
			$(".arrow-left").show();
			$(".arrow-right").show();
			
		}
	
	}

};

// Create divs for popup

var $popup = $("<div>", {"class": "popup"});
var $exit = $("<i>", {"class": "fa fa-times x-out"});
var $arrowLeft = $("<i>", {"class": "fa fa-chevron-left arrow-left addon"});
var $arrowRight = $("<i>", {"class": "fa fa-chevron-right arrow-right addon"});
var $caption = $("<div>", {"class": "caption addon"});

$(".pop").click(function() {
	
	// Ensure popup isn't already open
	
	if(window.popped === false) {
		
		if(!$(this).hasClass("open")) {
			
			window.popped = true;
			
			// Clone img and create popup
			
			$("body").append($popup);
			$(this).clone(true).addClass("clone").appendTo(".popup");
			$(this).addClass("original");
			$(".clone").addClass("open");
			$(".popup").append($exit);
			
			// Keep browser still
			
			window.scroll = $(window).scrollTop();
			$("body").css("top", -(document.documentElement.scrollTop) + "px").addClass("dim");
			
			// If there's a caption, add it in CSS
			
			if($(this).is("[caption]")) {
				
				$(".popup").append($caption);
				$(".caption").text($(this).attr("caption"));
				var captionMargin = -Math.abs($(".caption").outerHeight());
				$(".caption").css("marginTop", captionMargin);
				
			}
			
			// Add arrows, hide accordingly for first and last pictures unless requested otherwise with the wrap class
			
			if($(".original").parentsUntil().hasClass("wrap")) {
				
				addArrows();
				
			} else {
				
				if($(".original").parent().is(":first-child")) {
					
					window.imgPosition = "first";
					addArrows();
					$(".arrow-left").hide();
					
				} else if($(".original").parent().is(":last-child")) {
					
					window.imgPosition = "last";
					addArrows();
					$(".arrow-right").hide();
					$(".original").parent().addClass("hi");
					
				} else if(!($(".original").parent().is(":first-child") || $(".original").parent().is(":last-child"))) {
					
					window.imgPosition = "middle";
					addArrows();
					
				} else if($(".original").parent().is(":first-child") && $(".original").parent().is(":last-child")) {
					
					window.imgPosition = "only";
					$(".arrow-left").hide();
					$(".arrow-right").hide();
					
				}
				
			}
			
			// Opening effect
			
			if($(this).is("[effect='fade']")) {
				
				window.effect = "fade";
				$(".popup").hide().fadeIn("fast");
				
			} else if($(this).is("[effect='slide']")) {
				
				window.effect = "fade";
				$(".caption, .arrow-left, .arrow-right").css("opacity", "0.0");
				
				$.when($(".popup").hide().slideDown()).done(function() {
					
					positionAddons();
			
					$(".caption, .arrow-left, .arrow-right").each(function() {
						
						$(this).animate({opacity: 1.0}, 200);
						
					});
					
				});
				
			}
			
		}
		
	} else {
		
		if(!$(this).hasClass("open")) {
			
			closePopup();
			
		}
		
	}
	
});

// Close popup when clicking outside of the image (or X button by default), but not when clicking addons

$(window).click(function() {
	
	var target = $(event.target);
	
	if(window.popped === true && !target.is(".addon, .open")) {
		
		closePopup();
		
	}
	
});

$(".pop").click(function(event) {
	
	if(!($(this).parent().is(".popup"))) {
	
		event.stopPropagation();
	
	}
	
});

// Hover effects for left and right side of popup image

$(".kj").on("click", "img", function(e) {
	
	if($(this).hasClass("open")) {
		
		alert("clicked popup image");
	
		if((e.pageX - this.offsetLeft) < $(this).width() / 2) {
			
			alert("left");
			
		} else {
			
			alert("right");
			
		}
	
	}
	
});

// Switch popup image with arrows

$(document).on("click", ".addon", function() {
	
	if($(this).is(".arrow-left")) {
		
		$(".open").attr("src", $(".original").parent().prev().find("img").prop("src"));
		changePrevOriginal();
		
		$(".caption").text($(".original").attr("caption"));
		positionAddons();
		
		if($(".original").parentsUntil().hasClass("wrap")) {
		
			// NEED WRAP SUPPORT
		
		} else {
			
			updateArrows();
			
		}
	
	} else if($(this).is(".arrow-right")) {
		
		$(".open").attr("src", $(".original").parent().next().find("img").prop("src"));
		changeNextOriginal();

		$(".caption").text($(".original").attr("caption"));
		positionAddons();
		
		if($(".original").parentsUntil().hasClass("wrap")) {
		
			// NEED WRAP SUPPORT
		
		} else {
			
			updateArrows();
			
		}
	
	}
	
});

$(window).resize(function() {
	
	positionAddons();
	
});