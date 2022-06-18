var texto;
var change;
window.onload = function(){
	initiVars();
	gestionEventos();
}
function initiVars(){
	texto = document.getElementById('more_info');
	change = document.getElementById('change_status');
}
function gestionEventos(){
	change.addEventListener("click",function(){
		if(texto.style.display == "none"){
			texto.style.display = "block";
			this.innerHTML = "Reducir...";
		}else{
			texto.style.display = "none";
			this.innerHTML = "Ampliar...";
		}
	});
}
