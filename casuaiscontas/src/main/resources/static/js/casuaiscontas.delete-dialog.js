var CasuaisContas = CasuaisContas || {};

CasuaisContas.DeleteDialog = (function() {

    function DeleteDialog() {
        this.btnDelete = $('.js-delete');
    }

    DeleteDialog.prototype.start = function() {
        this.btnDelete.on('click', onBtnDeleteClick.bind(this));

        if(window.location.search.indexOf('deleted') > -1) {
            swal("Feito! Sua conta foi excluída com sucesso.", { icon: "success" });  
        }
    }

    function onBtnDeleteClick(e) {
        e.preventDefault();
        let btnClicked = $(e.currentTarget);
        let url = btnClicked.data('url');
        let obj = btnClicked.data('object');
        
        let options = {            
            title: 'Tem certeza?',
            text: `Excluir '${obj}'? Essa operação não poderá ser desfeita.`,
            icon: "warning",
            buttons: true,
            dangerMode: true,
            buttons: ['Cancelar', 'Sim, excluir agora!'],                        
        }
        swal(options).then(willDelete => {
            if(willDelete) {        
                onDeleteConfirm(url);
            }
        });
    }

    function onDeleteConfirm(url) {
        $.ajax({
            url: url,
            method: 'DELETE',
            success: onSuccessDelete,
            error: onErrorDelete
        })        
    }

    function onSuccessDelete() {         
        let currentUrl = window.location.href;
        let separator = currentUrl.indexOf('?') > -1 ? '&' : '?';
        let newUrl = currentUrl.indexOf('deleted') > -1 ? currentUrl : `${currentUrl}${separator}deleted`;

        window.location = newUrl;
    }

    function onErrorDelete(e) {
        swal(`Oops! ${e.responseText}.`, { icon: "error" });  
    }

    return DeleteDialog;

})();

$(function() {
    var deleteDialog = new CasuaisContas.DeleteDialog();
    deleteDialog.start();
});