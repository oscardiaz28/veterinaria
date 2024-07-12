// Selección de elementos fuera del evento `DOMContentLoaded`
export let navLinks;
export let menu;
export let timeContainer;
export let loader;
export let timeBtns;
export let pet_container;
export let add_pet;
export let authLink;
export let selectMascotas;
export let formulario;

document.addEventListener('DOMContentLoaded', () => {
    navLinks = document.querySelector('.nav_links');
    menu = document.querySelector('#menu');
    timeContainer = document.querySelector("#time-container");
    loader = document.querySelector("#loader");
    timeBtns = document.querySelectorAll(".time-btn");
    pet_container = document.querySelector('#pet_container');
    add_pet = document.querySelector('#add_pet');
    authLink = document.querySelector('#authLink');
    selectMascotas = document.querySelector('#selectMascotas');
    formulario = document.querySelector('#solicitarServicio');
});
