import getCookie from './src/helpers/functions.js';
import { Auth } from './src/class/auth.js';
import { Events } from './src/events.js';
import { selectMascotas, formulario } from './src/selectors/selectors.js';
import { Mascota } from './src/entity/mascota.js';

document.addEventListener('DOMContentLoaded', () => {

    const dni = getCookie('dni_cliente');
    Auth.editLink(dni);

    const events = new Events();
    events.init();

    const url = "http://localhost:8085/getMascotas?cliente_dni="+dni;
    fetch(url)
    .then( response => response.json() )
    .then( data => {
        showOptions(data);
    } )

    function showOptions(data){
        if( data.length > 0 ){
            if( selectMascotas ){
                 selectMascotas.innerHTML = "";
                            data.forEach( mascota => {
                                const option = document.createElement('option');
                                option.value = mascota.codigo;
                                option.textContent = mascota.nombre;
                                selectMascotas.appendChild(option);
                            })
            }

        }
    }

    if( formulario  ){
        const url = "http://localhost:8085/clientes?cliente_dni="+dni;
        fetch(url)
        .then( response => response.json() )
        .then( data => completeForm(data) )
    }

    function completeForm(data){
        const {apellidos, celular, codigo_mascota, direccion, nombre, usuario_id} = data[0];
        console.log( apellidos, celular, direccion, nombre, usuario_id );
        formulario.nombre.value = nombre;
        formulario.nombre.disabled = true;
        formulario.apellido.value = apellidos;
        formulario.apellido.disabled = true;
        formulario.telefono.value = celular;
        formulario.telefono.disabled = true;

    }

})


