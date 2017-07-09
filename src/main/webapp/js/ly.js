// JavaScript Document
$(function(){
	$(".nav li").hover(
		function(){//鼠标移入
			$(this).find(".menu").show(".menu");
		},
		function(){//鼠标移除
			$(this).find(".menu").hide();
		}
	);
	

	$(".sign").click(function(){
		$(this).parents(".lyitem").find(".lyitem-bd").toggle();
	})

});


