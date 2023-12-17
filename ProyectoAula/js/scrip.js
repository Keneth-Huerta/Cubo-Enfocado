function accion() {
    console.log("esta funcionando mi btn");
    var ancla = document.getElementsByClassName("nav-enlace");
    for (var i = 0; i < ancla.length; i++) {
        ancla[i].classList.toggle("desaparece");
    }
}
function ani(){
    var anima=document.getElementById("qr");
    anima.classList.toggle("qr")
}