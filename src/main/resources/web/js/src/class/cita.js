import { loader, formulario, timeBtns } from '../selectors/selectors.js';
import { showSwal } from '../helpers/functions.js' ;

export class Cita{

    static realizarCita( payload ){
        console.log( payload  );

        if( loader ){
            loader.classList.remove('hidden');
        }

        setTimeout( async () => {
            const response = await fetch("http://localhost:8085/api/crear_cita", {
                method: "POST",
                body: JSON.stringify(payload)
                //body: payload
            })
            const data = await response.json();
            Cita.mostrarAlerta(data);
        }, 1000)
    }

    static mostrarAlerta( data ){
        loader.classList.add('hidden');
        const {message} = data;
        console.log(data);
        if( formulario && message == "Cita registrada correctamente"){
            timeBtns.forEach( b => b.classList.remove('active') )
            formulario.fecha.value = ""
            formulario.mensaje.value = "";
            //formulario.reset();
            showSwal("success", message, "Excelente")
        }else{
            showSwal("info", message, "Error");
        }
    }


}