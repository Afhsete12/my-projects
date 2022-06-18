var fg_table;
var bg_body;
var color_bg;
var color_fg;
var btn_bg;
var btn_fg;

window.onload = function(){
	initVars();
	gestionEventos();
}
function initVars(){
	fg_table = document.querySelectorAll("table a");
	bg_body = document.querySelector("body");
	color_bg = document.getElementById("colorFondo");
	color_fg = document.getElementById("colorTexto");
	btn_bg = document.getElementById("cambiarFondo");
	btn_fg = document.getElementById("cambiarTexto");
}
function gestionEventos(){
	btn_bg.addEventListener("click",function(){
		bg_body.style.backgroundColor = color_bg.value;
	})
	btn_fg.addEventListener("click",function(){
		for(var i=0;i < fg_table.length;i++){
			fg_table[i].style.color = color_fg.value;
		}
	});
	
}