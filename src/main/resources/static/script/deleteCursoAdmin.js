function confirmarDeletar(id) {
    let confirmacao = confirm("Tem certeza que deseja excluir este curso?");
    if (confirmacao) {
        window.location.href = '/admin/curso/delete?id=' + id;
    }
}