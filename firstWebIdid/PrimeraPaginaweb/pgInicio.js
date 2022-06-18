var mod_video;
var video;
var social_network;
var confirmIt;

window.onload = function(){
	initVars();
	gestionEventos();
}
function initVars(){
	mod_video = document.getElementsByTagName("button");
	video = document.getElementById('video');
	social_network = document.querySelectorAll('div#datosInicio > ul > li > a');
	confirmIt = function (e) {
		alert("Sigueme!! Go ahead!!");
        if (!confirm('Are you sure?')) e.preventDefault();
	}
}
function gestionEventos(){
	mod_video[0].addEventListener("click",function(){
		video.pause();
	});
	mod_video[1].addEventListener("click",function(){
		video.play();
	});
	for (var i =0;i < 3;i++){
		social_network[i].addEventListener("click",confirmIt,false);
	}
	
}

