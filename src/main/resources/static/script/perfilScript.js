var logradouroElement = document.getElementById("logradouro");
var cidadeElement = document.getElementById("cidade");
var ufElement = document.getElementById("uf");
var tel1Element = document.getElementById("telefone1");
var tel2Element = document.getElementById("telefone2");
var bioElement = document.getElementById("bio");
var ocupElement = document.getElementById("ocup");


if (logradouroElement.textContent.trim() === "") {
    logradouroElement.textContent = "Endereço não encontrado";
}

if (ocupElement.textContent.trim() === "") {
    ocupElement.textContent = "Profissão não encontrada";
}

if (cidadeElement.textContent.trim() === ""){
    cidadeElement.textContent = "Cidade não encontrada ";
}

if (ufElement.textContent.trim() === "") {
    ufElement.textContent = "UF não encontrada";
}

if (tel1Element.textContent.trim() === "") {
    tel1Element.textContent = "(**)****-*****";
}

if (tel2Element.textContent.trim() === "") {
    tel2Element.textContent = "(**)*****-*****";
}

if (bioElement.textContent.trim() === "") {
    bioElement.textContent = "Insira sua bio para as pessoas te conhecerem melhor";
}

