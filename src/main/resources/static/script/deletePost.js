function confirmarDeletar(idPost) {
    var confirmacao = confirm("Tem certeza que deseja deletar essa postagem?");
    if (confirmacao) {
        window.location.href = '/post/user/delete?postId=' + idPost;
    }
}