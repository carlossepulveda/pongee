$(document).ready(function(){

	$("#btnBook").click(function(e){
		window.location.href = "/book/"+$("#idBook").val();
	});

	$("#btnUser").click(function(e){
		window.location.href = "/user/"+$("#idUser").val();
	});

});
