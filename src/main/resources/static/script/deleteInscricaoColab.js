function confirmarDeletar(id) {
    let confirmacao = confirm("Tem certeza que deseja cancelar sua inscrição?");
    if (confirmacao) {
        window.location.href = '/colab/inscricoes/cancelar?id=' + id;
    }
}