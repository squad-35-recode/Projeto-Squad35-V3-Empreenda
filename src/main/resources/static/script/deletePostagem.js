function confirmarDeletar(id) {
    let confirmacao = confirm("Tem certeza que deseja excluir esta postagem?");
    if (confirmacao) {
        window.location.href = '/admin/postagem/deletar?id=' + id;
    }
}