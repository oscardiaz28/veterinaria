const navLinks = document.querySelector('.nav_links');
const menu = document.querySelector('#menu');
const timeContainer = document.querySelector("#time-container")
const loader = document.querySelector("#loader");
const timeBtns = document.querySelectorAll(".time-btn");
const pet_container = document.querySelector('#pet_container');
const add_pet = document.querySelector('#add_pet');

if( add_pet ){
    add_pet.addEventListener('click', agregarNuevoForm )
}

let formCount = 1;
function agregarNuevoForm(){
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
            <button onclick="eliminar(event)" class="bg-red-900 p-1 px-3 rounded-md text-white" >Eliminar formulario</button>
        </div>
    `;
    pet_container.appendChild(newForm);
}

function eliminar(e){
   const button = e.target;
   const div = button.parentElement.parentElement;
   div.innerHTML = "";
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

const formulario = document.querySelector('#solicitarServicio');
if( formulario ){

    const serializarFormulario = (form) => {
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

    formulario.addEventListener('submit', e => {
        e.preventDefault();
        const form = e.target;
        let cita = serializarFormulario(form);
        console.log(cita);
        realizarCita(cita);

    })
}

function realizarCita( payload ){

    if( loader ){
        loader.classList.remove('hidden');
    }
    setTimeout( async () => {
        const response = await fetch("http://localhost:8085/api/crear_cita", {
            method: "POST",
            //body: JSON.stringify(payload)
            body: payload
        })
        const data = await response.json();
        mostrarAlerta(data);
    }, 1000)
}

function mostrarAlerta( data ){
    loader.classList.add('hidden');
    const {message} = data;
    console.log(data);
    if( formulario && message == "Cita registrada correctamente"){
        timeBtns.forEach( b => b.classList.remove('active') )
        formulario.reset();
        showSwal("success", message, "Excelente")
    }else{
        showSwal("info", message, "Error");
    }
}

function showSwal(icon, message, titulo){
    Swal.fire({
      title: titulo,
      text: message,
      icon: icon
    });
}


