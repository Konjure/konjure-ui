/*

	* Konjure UI JS Library vv1.0.0
	* https://konjure.org/ui

	* Copyright (c) 2018 Konjure and other contributors
	* Released under the MIT license
	* https://opensource.org/licenses/MIT

*/

$(document).ready(function(){$(".advertisement").each(function(b){if($(this).is("[size]")){var c=$(this).attr("size").split("x")[0];
var a=$(this).attr("size").split("x")[1];$(this).css("width",c+"px");$(this).css("height",a+"px")}})});
$(document).ready(function(){var b,c,a,e;$(".button.material").click(function(d){if($(this).find(".materialInk").length==0){$(this).prepend("<span class='materialInk'></span>")
}b=$(this).find(".materialInk");b.removeClass("animate");if(!b.height()&&!b.width()){c=Math.max($(this).outerWidth(),$(this).outerHeight());
b.css({height:c,width:c})}a=d.pageX-$(this).offset().left-b.width()/2;e=d.pageY-$(this).offset().top-b.height()/2;
b.css({top:e+"px",left:a+"px"}).addClass("animate")})});$(document).ready(function(){$("img").on("dragstart",function(a){a.preventDefault()
})});$(document).ready(function(){window.isChanged=false;$(".expander").click(function(){$(this).toggleClass("expanded");
if($(this).is("[change-icon]")){window.newIcon=$(this).attr("change-icon");if(window.isChanged===false){window.currentIcon=$(this).find("i").attr("class").split(" ")[1];
$(this).find("i").removeClass(window.currentIcon);$(this).find("i").addClass(window.newIcon);window.isChanged=true
}else{$(this).find("i").removeClass(window.newIcon);$(this).find("i").addClass(window.currentIcon);window.isChanged=false
}}})});$(document).ready(function(){window.popped=false;window.effect="none";window.imgHeight=0;window.imgPosition="";
closePopup=function(){$(".popup").empty().remove();$(".pop").removeClass("original");$("body").css("top",-(document.documentElement.scrollTop)+"px").removeClass("dim");
$(window).scrollTop(window.scroll);window.popped=false};addArrows=function(){$(".popup").append(b,a);
window.imgHeight=($(".open").height()/2)-($(".arrow-left").height()/2);$(".arrow-left, .arrow-right").css("top",window.imgHeight);
$(".arrow-left").show();$(".arrow-right").show()};positionAddons=function(){window.imgHeight=($(".open").height()/2)-($(".arrow-left").height()/2);
var f=-Math.abs($(".caption").outerHeight());$(".arrow-left, .arrow-right").css("top",window.imgHeight);
$(".caption").css("marginTop",f)};setImgPosition=function(){if(!$(".original").parentsUntil().hasClass("wrap")){if($(".original").parent().is(":first-child")){window.imgPosition="first"
}else{if($(".original").parent().is(":last-child")){window.imgPosition="last"}else{if(!($(".original").parent().is(":first-child")||$(".original").parent().is(":last-child"))){window.imgPosition="middle"
}else{if($(".original").parent().is(":first-child")&&$(".original").parent().is(":last-child")){window.imgPosition="only"
}}}}}};changePrevOriginal=function(){$(".original").addClass("old-original");$(".original").parent().prev().find("img").addClass("original");
$(".old-original").removeClass("original old-original");setImgPosition()};changeNextOriginal=function(){$(".original").addClass("old-original");
$(".original").parent().next().find("img").addClass("original");$(".old-original").removeClass("original old-original");
setImgPosition()};updateArrows=function(){if(!$(".original").parentsUntil().hasClass("wrap")){if(window.imgPosition==="first"){$(".arrow-left").hide()
}else{if(window.imgPosition==="last"){$(".arrow-right").hide()}else{if(window.imgPosition==="middle"){$(".arrow-left").show();
$(".arrow-right").show()}}}}};var c=$("<div>",{"class":"popup"});var e=$("<i>",{"class":"fa fa-times x-out"});
var b=$("<i>",{"class":"fa fa-chevron-left arrow-left addon"});var a=$("<i>",{"class":"fa fa-chevron-right arrow-right addon"});
var d=$("<div>",{"class":"caption addon"});$(".pop").click(function(){if(window.popped===false){if(!$(this).hasClass("open")){window.popped=true;
$("body").append(c);$(this).clone(true).addClass("clone").appendTo(".popup");$(this).addClass("original");
$(".clone").addClass("open");$(".popup").append(e);window.scroll=$(window).scrollTop();$("body").css("top",-(document.documentElement.scrollTop)+"px").addClass("dim");
if($(this).is("[caption]")){$(".popup").append(d);$(".caption").text($(this).attr("caption"));var f=-Math.abs($(".caption").outerHeight());
$(".caption").css("marginTop",f)}if($(".original").parentsUntil().hasClass("wrap")){addArrows()}else{if($(".original").parent().is(":first-child")){window.imgPosition="first";
addArrows();$(".arrow-left").hide()}else{if($(".original").parent().is(":last-child")){window.imgPosition="last";
addArrows();$(".arrow-right").hide();$(".original").parent().addClass("hi")}else{if(!($(".original").parent().is(":first-child")||$(".original").parent().is(":last-child"))){window.imgPosition="middle";
addArrows()}else{if($(".original").parent().is(":first-child")&&$(".original").parent().is(":last-child")){window.imgPosition="only";
$(".arrow-left").hide();$(".arrow-right").hide()}}}}}if($(this).is("[effect='fade']")){window.effect="fade";
$(".popup").hide().fadeIn("fast")}else{if($(this).is("[effect='slide']")){window.effect="fade";$(".caption, .arrow-left, .arrow-right").css("opacity","0.0");
$.when($(".popup").hide().slideDown()).done(function(){positionAddons();$(".caption, .arrow-left, .arrow-right").each(function(){$(this).animate({opacity:1},200)
})})}}}}else{if(!$(this).hasClass("open")){closePopup()}}});$(window).click(function(){var f=$(event.target);
if(window.popped===true&&!f.is(".addon, .open")){closePopup()}});$(".pop").click(function(f){if(!($(this).parent().is(".popup"))){f.stopPropagation()
}});$(".kj").on("click","img",function(f){if($(this).hasClass("open")){alert("clicked popup image");if((f.pageX-this.offsetLeft)<$(this).width()/2){alert("left")
}else{alert("right")}}});$(document).on("click",".addon",function(){if($(this).is(".arrow-left")){$(".open").attr("src",$(".original").parent().prev().find("img").prop("src"));
changePrevOriginal();$(".caption").text($(".original").attr("caption"));positionAddons();if($(".original").parentsUntil().hasClass("wrap")){}else{updateArrows()
}}else{if($(this).is(".arrow-right")){$(".open").attr("src",$(".original").parent().next().find("img").prop("src"));
changeNextOriginal();$(".caption").text($(".original").attr("caption"));positionAddons();if($(".original").parentsUntil().hasClass("wrap")){}else{updateArrows()
}}}});$(window).resize(function(){positionAddons()})});$(document).ready(function(){$("*").hover(function(){if($(this).is("[transition]")){$(this).css("transition",$(this).attr("transition")+"s ease")
}if($(this).is("[hover]")){if(!$(this).hasClass("disabled")){$(this).addClass($(this).attr("hover"))}}})
});$(document).ready(function(){$(".inner-bar").each(function(e){var c=$("<div>",{"class":"hide-bar"});
var a=$("<div>",{"class":"animate-bar"});var f=$("<div>",{"class":"inner-bar-label"});var d=$(this).attr("transition")*1000;
if($(this).is("[progress]")){if($(this).is("[transition]")){$(this).append(c);var b=$(this).attr("progress");
$(this).css("transition",$(this).attr("transition")+"s ease");$(this).children(".hide-bar").animate({width:100-b+"%"},d,function(){if($(this).parent().hasClass("error")){$(this).parent().parent().css("background-color","var(--bar-error-color)");
$(this).parent().animate({opacity:"0.0"},d/4)}else{if($(this).parent().hasClass("warning")){$(this).parent().parent().css("background-color","var(--bar-warning-color)");
$(this).parent().animate({opacity:"0.0"},d/4)}else{if(b==="100"||$(this).parent().hasClass("success")){$(this).parent().parent().css("background-color","var(--bar-completed-color)");
$(this).parent().animate({opacity:"0.0"},d/4)}}}})}else{$(this).append(c);var b=$(this).attr("progress");
$(this).children(".hide-bar").css("width",100-b+"%");if($(this).hasClass("error")){$(this).parent().css("background-color","var(--bar-error-color)");
$(this).css("opacity","0.0")}else{if($(this).hasClass("warning")){$(this).parent().css("background-color","var(--bar-warning-color)");
$(this).css("opacity","0.0")}else{if(b==="100"||$(this).hasClass("success")){$(this).parent().css("background-color","var(--bar-completed-color)");
$(this).css("opacity","0.0")}}}}if($(this).is("[animate]")){$(this).append(a)}}})});