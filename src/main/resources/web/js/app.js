const navLinks = document.querySelector('.nav_links');
const menu = document.querySelector('#menu');

menu.addEventListener('click', () => {
    navLinks.style.display = (navLinks.style.display == "none" || navLinks.style.display == "" ) ? "flex" : "none";
})

