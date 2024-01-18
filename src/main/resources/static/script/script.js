const areaCadastro = document.getElementById("formCadastro");
const nomeCadastro = document.getElementById('nomeCadastro');
const emailCadastro = document.getElementById("emailCadastro");
// const senha1 = document.getElementById("senha1Cadastro").value;
// const senha2 = document.getElementById("senha2Cadastro").value;
const validar = function validarInputs() { };


const isValidEmail = email => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
};


(function () {
    'use strict'
    const forms = document.querySelectorAll('.requires-validation')
    Array.from(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

const senha1 = document.getElementById('senha1Cadastro');
const senha2 = document.getElementById('senha2Cadastro');
const senhasIncoDiv = document.getElementById('senhasInco');

// Adicione um evento 'input' aos campos de senha para verificar enquanto o usuário digita
senha1.addEventListener('input', verificarSenhas);
senha2.addEventListener('input', verificarSenhas);

function verificarSenhas() {
    if (senha1.value === senha2.value) {
        senha1.classList.remove('is-invalid');
        senha2.classList.remove('is-invalid');
        senha1.classList.add('is-valid');
        senha2.classList.add('is-valid');
        senhasIncoDiv.textContent = ''; // Limpa a mensagem de erro
    } else {
        senha1.classList.remove('is-valid');
        senha2.classList.remove('is-valid');
        senha1.classList.add('is-invalid');
        senha2.classList.add('is-invalid');
        senhasIncoDiv.textContent = 'As senhas não coincidem.'; // Mostra mensagem de erro
    }
}

function limparCampos() {
    document.getElementById("formCadastro").reset();
}


