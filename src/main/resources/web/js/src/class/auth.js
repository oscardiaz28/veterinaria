import { authLink } from '../selectors/selectors.js';

export class Auth{
    static editLink( dni ){
        if( dni ){
            console.log(authLink)
            authLink.innerHTML = "Cerrar Sesión";
            authLink.href = "/logout";
            authLink.classList.add('font-bold', 'hidden', 'md:inline-block', 'py-3', 'px-3', 'text-white',
            'rounded-md', 'bg-blue-800')
        }else{
            authLink.innerHTML = "Iniciar Sesión";
            authLink.href = "/login.html";
            authLink.classList.add('font-bold', 'hidden', 'md:inline-block', 'py-3', 'px-3', 'text-white',
            'rounded-md', 'bg-[#FD4C82]')
        }
    }
}