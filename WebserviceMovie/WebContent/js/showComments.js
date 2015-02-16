/**
 * 
 */

$(function() {
	
	$(".grid1_of_2").bind("click", function(e) {
		if ($(e.target).is("input") || $(e.target).is("textarea")) {
			return;
		}
		$(this).find(".comment_field").slideToggle("quick");
		$(this).find(".comment_text").slideToggle("quick");
	});
	
});