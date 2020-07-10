var CasuaisContas = CasuaisContas || {}; 

CasuaisContas.MaskDate = (function() {

    function MaskDate() {
        this.inputDate = $('.js-date');
    }

    MaskDate.prototype.start = function() {
        this.inputDate.mask('00/00/0000');
        let options = {
            language: 'pt-BR',
            container: '.js-datepicker-container',
            orientation: 'bottom',
            autoclose: true,
            todayHighlight: true
        }

        this.inputDate.datepicker(options);
    }

    return MaskDate;

})();

CasuaisContas.MaskPhone = (function() {

    function MaskPhone() {
        this.inputPhone = $('.js-phone');
    }

    MaskPhone.prototype.start = function() {       
		var maskBehavior = function (val) {
            return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
        };

        var options = {               
            onKeyPress: function(val, e, field, options) {
                field.mask(maskBehavior.apply({}, arguments), options);
            }
        };
        
        this.inputPhone.mask(maskBehavior, options);
    }

    return MaskPhone;

})();

CasuaisContas.MaskCep = (function() {

    function MaskCep() {
        this.inputDate = $('.js-cep');
    }

    MaskCep.prototype.start = function() {
        this.inputDate.mask('00000-000');
    }

    return MaskCep;

})();

CasuaisContas.MaskCpf = (function() {

    function MaskCpf() {
        this.inputDate = $('.js-cpf');
    }

    MaskCpf.prototype.start = function() {
        this.inputDate.mask('000.000.000-00');
    }

    return MaskCpf;

})();

CasuaisContas.Switch = (function() {

    function Switch() {
        this.switch = $('.js-status');
    }

    Switch.prototype.start = function() {
        let options = {
            size: 'mini',
            onColor: 'success',
            offColor: 'danger',
            onText: 'Sim',
            offText: 'Não'

        }

        $('.js-status').bootstrapSwitch(options);
    }

    return Switch;

})();

CasuaisContas.MaskMoney = (function() {

    function MaskMoney() {
        this.decimal = $('.js-decimal');
        this.plain = $('.js-plain');
    }

    MaskMoney.prototype.start = function() {
        this.plain.mask("#.##0", { reverse: true });
        this.decimal.mask("#.##0,00", { reverse: true });
    }

    return MaskMoney;

})();

$(function() {
    var maskDate = new CasuaisContas.MaskDate();
    maskDate.start();

    var maskPhone = new CasuaisContas.MaskPhone();
    maskPhone.start();

    var maskCep = new CasuaisContas.MaskCep();
    maskCep.start();

    var maskCpf = new CasuaisContas.MaskCpf();
    maskCpf.start();

    var bSwitch = new CasuaisContas.Switch();
    bSwitch.start();

    var maskMoney = new CasuaisContas.MaskMoney();
    maskMoney.start();
});