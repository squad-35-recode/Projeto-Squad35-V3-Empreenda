function confirmarDeletar(id) {
    let confirmacao = confirm("Tem certeza que deseja excluir este usuário?");
    if (confirmacao) {
        window.location.href = '/admin/usuario/deletar?id=' + id;
    }
}