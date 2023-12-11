function boolean comprueba (number corte)
{   
	let number nota;
    alert ("Aprobado?");
    input(nota);
    return ((nota > corte));
}

function boolean sinRecu(boolean resultado)
{
	
	if(comprueba(4)){
		alert("No hay que ir a julio");
		resultado = true;
	}
	else{
		alert("Hay que ir a julio");
		resultado = false;
	}
	
	return resultado;
    
}
function vacaciones ()
{
	let boolean tengoCoche;
	tengoCoche = true;
	if(sinRecu(true) && tengoCoche) alert("Me voy de vacaciones");
  
}

vacaciones();



