var CasuaisContas = CasuaisContas || {};

CasuaisContas.Multiselect = (function() {

    function Multiselect() {
        this.select = $('.js-select');
        this.selectAll = $('.js-select-all');
        this.btnStatus = $('.js-btn-status');
        this.btnActionStatus = $('.js-btn-status');
    }

    Multiselect.prototype.start = function() {
        this.select.on('click', onSelect.bind(this));
        this.selectAll.on('click', onSelectAll.bind(this));
        this.btnStatus.on('click', onBtnStatusClick.bind(this));
    }

    function onSelect() {
        let selected = this.select.filter(':checked');
        this.selectAll.prop('checked', selected.length >= this.select.length);
        btnActionStatus.call(this, selected.length);
    }

    function onSelectAll() {
        this.select.prop('checked', this.selectAll.prop('checked'));
        btnActionStatus.call(this, this.selectAll.prop('checked'));
    }

    function onBtnStatusClick(e) {
        let btn = $((e.currentTarget));
        let url = btn.data('url');
        let status = btn.data('status');
        
        let checkBoxesSelected = this.select.filter(':checked');
        let ids = $.map(checkBoxesSelected, element => {
            return $(element).data('id');
        });

        if(ids.length) {
            $.ajax({
                url,
                method: 'PUT',
                data: {
                    ids,
                    status
                },
                success: onSuccess
            });
        }
       
    }

    function btnActionStatus(active) {
        active ? this.btnActionStatus.removeClass('disabled') : this.btnActionStatus.addClass('disabled');
    }

    function onSuccess() {
        window.location.reload();
    }

    return Multiselect;

})();

$(function () {
    var multiselect = new CasuaisContas.Multiselect();
    multiselect.start();
});