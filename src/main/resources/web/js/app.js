const navLinks = document.querySelector('.nav_links');
const menu = document.querySelector('#menu');
const timeContainer = document.querySelector("#time-container")
const loader = document.querySelector("#loader");
const timeBtns = document.querySelectorAll(".time-btn");


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
        let objeto = {};
        for( let [name, value] of formData){
            if(name === "servicios"){
                continue;
            }
            objeto[name] = value;
        }
        const selectElement = form.querySelector("select[name='servicios']");
        const selectValues = Array.from( selectElement.selectedOptions ).map( option => option.value);
        objeto["servicios"] = selectValues;
        return objeto;
    }

    formulario.addEventListener('submit', e => {
        e.preventDefault();
        const form = e.target;
        let cita = serializarFormulario(form);
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
            body: JSON.stringify(payload)
        })
        const data = await response.json();
        mostrarAlerta(data);
    }, 1000)
}

function mostrarAlerta( data ){
    loader.classList.add('hidden');
    const {message} = data;
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


