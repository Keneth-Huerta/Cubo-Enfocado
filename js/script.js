// animaci&oacute;n de boton activo del nav
function animacion() {
    var botones = document.getElementsByClassName('a');
    botones[0].classList.remove('animacion');
    botones[1].classList.remove('animacion');
    if (botones[0] == botones[0]) {
        botones[0].classList.add('animacion');
        botones[1].classList.remove('animacion');
    }
};
function animacion1() {
    var botones = document.getElementsByClassName('a');
    botones[0].classList.remove('animacion');
    botones[1].classList.remove('animacion');
    if (botones[1] = botones[1]) {
        botones[0].classList.remove('animacion');
        botones[1].classList.add('animacion');
    }
}
// function animacion2() {
//     var botones = document.getElementsByClassName('a');
//     botones[0].classList.remove('animacion');
//     botones[1].classList.remove('animacion');
//     if (botones[2] = botones[2]) {
//         botones[0].classList.remove('animacion');
//         botones[1].classList.remove('animacion');
//     }
// }
// boton desaparece
function accion() {
    console.log("esta funcionando mi btn");
    var ancla = document.getElementsByClassName("nav-enlace");
    for (var i = 0; i < ancla.length; i++) {
        ancla[i].classList.toggle("desaparece");
    }
}
