import { loader, formulario, timeBtns, pet_container } from '../selectors/selectors.js';

let formCount = 1;
export function agregarNuevoForm(){
    const newForm = document.createElement('div');
    newForm.classList.add('grid', 'grid-cols-1', 'gap-5', 'md:grid-cols-3', 'mt-5');

    newForm.innerHTML = `
        <input name="nombre_mascota[${formCount}]" type="text" placeholder="Nombre" class="w-full border outline-none h-[55px] px-3 text-md ">
        <input name="especie[${formCount}]" type="text" placeholder="Especie" class="w-full border outline-none h-[55px] px-3 text-md ">
        <input name="raza[${formCount}]" type="text" placeholder="Raza, Ejm. pitbull" class="w-full border outline-none h-[55px] px-3 text-md ">
        <input name="edad[${formCount}]" type="text" placeholder="Edad, Ejm. 2 meses" class="w-full border outline-none h-[55px] px-3 text-md ">
        <select name="genero[${formCount}]" class="border outline-none px-3">
          <option value="macho">macho</option>
          <option value="hembra">hembra</option>
        </select>
        <input name="imagen[${formCount}]" type="file" class="w-full border outline-none h-[55px] px-3 text-md ">
        <div>
            <button onclick="eliminar" class="bg-red-900 p-1 px-3 rounded-md text-white" >Eliminar formulario</button>
        </div>
    `;
    if( pet_container ){
        pet_container.appendChild(newForm);
    }
}

const dni = getCookie('dni_cliente');

export const serializarFormularioJson = (form) => {
    const cita = {};
    const formData = new FormData(form);
    for( let [key, value] of formData){
        if (!/\[.*?\]/.test(name)) {
             if(name === "servicios"){
                continue;
             }
             cita[key] = value;
        }
    }
    const selectElement = form.querySelector("select[name='servicios']");
    const selectValues = Array.from( selectElement.selectedOptions ).map( option => option.value);
    cita["servicios"] = selectValues;
    cita["id_cliente"] = dni;
    return cita;
}

export const serializarFormularioFormData = (form) => {
    const formData = new FormData(form);
    const cita = new FormData();

    for (let [key, value] of formData) {
        if (!/\[.*?\]/.test(key)) {
            if (key === "servicios") {
                continue;
            }
            cita.append(key, value);
        }
        cita.append(key, value);
    }

    const selectElement = form.querySelector("select[name='servicios']");
    const selectValues = Array.from(selectElement.selectedOptions).map(option => option.value);

    // Add each selected value for "servicios" to the FormData object
    selectValues.forEach((value) => {
        cita.append("servicios[]", value);
    });
    cita.append("id_cliente", dni);
    return cita;
}




export const serializarFormulario = (form) => {
        let formData = new FormData(form);

        // ---------------- ///
        let data = {};
        let client = {};

        for( let [name, value] of formData){
            if (!/\[.*?\]/.test(name)) {
                 if(name === "servicios"){
                    continue;
                 }
                 client[name] = value;
            }
        }
        const selectElement = form.querySelector("select[name='servicios']");
        const selectValues = Array.from( selectElement.selectedOptions ).map( option => option.value);
        //data["servicios"] = selectValues;

        const mascotas = [];
        formData.forEach( (value, key) => {
            if( /\[\d+\]$/.test(key)){
             const [field, index] = key.split('[').map( (s) => s.replace(']', '') )
                if( !mascotas[index]  ){
                    mascotas[index] = {};
                }
                if( key != "servicios" ){
                    mascotas[index][field] = value;
              }
            }
        } )

        data["mascotas"] = mascotas;
        data["info"] = client;
        data["info"] = selectValues;

        // ---------------- ///
        console.log(data);

        formData.append("informacion", data["info"]);
        formData.append("mascotas", data["mascotas"]);

        return formData;
    }

function eliminar(e){
   const button = e.target;
   const div = button.parentElement.parentElement;
   div.innerHTML = "";
}


function getCookie(name){
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}


export function showSwal(icon, message, titulo){
   Swal.fire({
     title: titulo,
     text: message,
     icon: icon
   });
}

export default getCookie