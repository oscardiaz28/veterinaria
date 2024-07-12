import { agregarNuevoForm } from './helpers/functions.js';
import { formulario, timeContainer, timeBtns, menu, navLinks, add_pet } from './selectors/selectors.js';
import { Cita } from './class/cita.js';
import { serializarFormulario, serializarFormularioFormData, serializarFormularioJson } from './helpers/functions.js';

export class Events{

        init(){
            let citaClass = new Cita();

            if(add_pet){
                add_pet.addEventListener('click', agregarNuevoForm )
            }

            if( formulario ){
                formulario.addEventListener('submit', e => {
                    e.preventDefault();
                    const form = e.target;
                    let cita = serializarFormularioJson(form);
                    Cita.realizarCita(cita);
                })
            }

            if( timeContainer ){
                timeBtns.forEach( btn => {
                    btn.addEventListener('click', addClass);
                } )
                function addClass(event){
                    timeBtns.forEach( b => b.classList.remove('active') )
                    event.currentTarget.classList.add('active');
                }
            }


            if( menu != null || menu != undefined ){
                menu.addEventListener('click', () => {
                    navLinks.style.display = (navLinks.style.display == "none" || navLinks.style.display == "" ) ? "flex" : "none";
                })
            }


        }

}