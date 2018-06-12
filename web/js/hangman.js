var  abecedario = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ','o','p','q','r','s','t','u','v','w','x','y','z'];
var palabras = [];
var palabra = "";
var longitud = 0;
var contador = 0;
var error = 0;
var buenas = 0;
var malas = 0;

function init(palabrasLista){
	palabras = palabrasLista;
	console.log(palabras);
	palabra =palabras[Math.floor(Math.random()*(palabras.length))];
	console.log(palabra);
	error = 0;
	contador = 0;
	longitud = palabra.length;
	crearTablero();	
	limpiarCanvas();
	console.log(buenas);
	console.log(malas);
}

function crearTablero(){	
	var espacio = document.getElementById("espacios");
	for(var i=0; i<longitud; i++) {
		var adivina = document.createElement("div");
		adivina.innerHTML="&nbsp";
		adivina.setAttribute("id",i);
		espacio.appendChild(adivina);
	}

	for (var i = 0; i<=26; i++) {
		var letras = document.getElementById("letras");
		var botones =document.createElement("button");
		botones.innerHTML=abecedario[i];
		botones.setAttribute("class","letras btn btn-primary ");
		botones.setAttribute("onclick","buscar('"+abecedario[i]+"')");
		botones.setAttribute("type","button");
		botones.setAttribute("id",abecedario[i])
		letras.appendChild(botones);
	}

}

function comparar(){
	if(contador==longitud){
		swal("Buen trabajo!", "Da click al botón para avanzar a la siguiente", "success").then((value) => {
				buenas ++;
				revisarFinal();
			});	
	}
}


function buscar(letra) {
	var bandera= false;
	var button = document.getElementById(letra);
	button.setAttribute("disabled","true")
	for (var i=0; i<longitud; i++){
		if (letra==palabra[i]) {
			document.getElementById(i).innerHTML=palabra[i];
			contador++;
			bandera=true;
			};
	}
	comparar();
	if (bandera!=true) {
		error++;
		ahorcado();
	};
	if(error==7){
		swal("Perdiste!", "Da click en el boton para continuar con la siguiente palabra", "error").then((value) => {
			malas++;
			revisarFinal();
		});
	}
	document.getElementById(letra).style.background="#1D1799";
}

function revisarFinal(){
	if(buenas+malas==5){
		swal("Terminaste la ronda!","buenas: "+buenas+" malas: "+malas).then((value) => {
			buenas ++;
			var espacio = document.getElementById("espacios");
			for(var i = palabras.length-1; i>=0; i--){
				if(palabras[i]==palabra){
					palabras.splice(i,1);
				}
			}
			for(var i=palabra.length-1; i>=0 ; i--){
				$("#"+i).remove();			
			}
			for (var i = 0; i<=26; i++) {
				$("#"+abecedario[i]).remove();
			}
			//llamar al servlet
			});
	}else{
		limpiar();
	}
}


function limpiar(){
	var espacio = document.getElementById("espacios");
	for(var i = palabras.length-1; i>=0; i--){
		if(palabras[i]==palabra){
			palabras.splice(i,1);
		}
	}
	for(var i=palabra.length-1; i>=0 ; i--){
		$("#"+i).remove();			
	}
	for (var i = 0; i<=26; i++) {
		$("#"+abecedario[i]).remove();
	}
	init(palabras);
}

function limpiarCanvas(){
	var bizquierdo = document.getElementById("bizquierdo");
	var cabeza = document.getElementById("cabeza");
	var torso = document.getElementById("torso");
	var cadera = document.getElementById("cadera");
	var bderecho = document.getElementById("bderecho");
	var pizquierdo = document.getElementById("pizquierdo");
	var pderecho = document.getElementById("pderecho");
	bizquierdo.width = bizquierdo.width; 
	cabeza.width = cabeza.width;
	torso.width = torso.width;
	cadera.width = cadera.width;
	bderecho.width = bderecho.width;
	pizquierdo.width = pizquierdo.width;
	pderecho.width = pderecho.width;
}



function ahorcado (){
	switch (error){
		case 1:
		var cabeza = document.getElementById("cabeza");
		var circulo = cabeza.getContext("2d");
		circulo.beginPath();
		circulo.arc(150,100,40,0,2*Math.PI);
		circulo.lineWidth=4;
		circulo.stroke();
		break;

		case 2:
		var torso = document.getElementById("torso");
		var torsoarriba = torso.getContext("2d");
		torsoarriba.moveTo(150,0);
		torsoarriba.lineTo(150,600);
		torsoarriba.lineWidth=4;
		torsoarriba.stroke();
		break;

		case 3:
		var cadera = document.getElementById("cadera");
		var caderaabajo = cadera.getContext("2d");
		caderaabajo.moveTo(150,0);
		caderaabajo.lineTo(150,600);
		caderaabajo.lineWidth=4;
		caderaabajo.stroke();
		break;

		case 4:
		var bizquierdo = document.getElementById("bizquierdo");
		var bizq = bizquierdo.getContext("2d");
		bizq.moveTo(300,0);
		bizq.lineTo(-100,400);
		bizq.lineWidth=4;
		bizq.stroke();
		break

		case 5:
		var bderecho = document.getElementById("bderecho");
		var bder = bderecho.getContext("2d");
		bder.moveTo(0,0);
		bder.lineTo(400,400);
		bder.lineWidth=4;
		bder.stroke();
		break;

		case 6:
		var pizquierdo = document.getElementById("pizquierdo");
		var pizq = pizquierdo.getContext("2d");
		pizq.moveTo(0,0);
		pizq.lineTo(400,400);
		pizq.lineWidth=4;
		pizq.stroke();
		break;

		case 7:
		var pderecho = document.getElementById("pderecho");
		var pder = pderecho.getContext("2d");
		pder.moveTo(300,0);
		pder.lineTo(-100,400);
		pder.lineWidth=4;
		pder.stroke();
		break;

	}
}















