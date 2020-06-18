var CasuaisContas = CasuaisContas || {}; 

CasuaisContas.StateCombo = (function() {

    function StateCombo() {
        this.comboState = $('#state');

        this.emitter = $({});
        this.on = this.emitter.on.bind(this.emitter);
    }

    StateCombo.prototype.start = function() {
        this.comboState.on('change', onStateSelected.bind(this));
    }

    function onStateSelected() {
        this.emitter.trigger('selected', this.comboState.val());
    }

    return StateCombo;

})();

CasuaisContas.CityCombo = (function() {

    function CityCombo(comboState) {
        this.comboState = comboState;
        this.comboCity = $('#city');
        this.inputHiddenSelectedCity = $('#selectedCityId');
    }

    CityCombo.prototype.start = function() {
        this.comboState.on('selected', onStateSelected.bind(this));

        let stateId = this.comboState.comboState.val();
        findCities.call(this, stateId);
    }

    function onStateSelected(event, stateId) {
        this.inputHiddenSelectedCity.val('');
        findCities.call(this, stateId);
    }

    function findCities(stateId) {
        if(stateId) {
            var response = $.ajax({
                url: '/cidades',
                method: 'GET',
                contentType: 'application/json',
                data: {
                    'state': stateId
                }
            });

            response.done(onFindCitiesDone.bind(this));

        } else {
            resetComboCity.call(this);
        }
    }

    function onFindCitiesDone(cities) { 
        let options = [];

        cities.forEach(city => {
            options.push(`<option value="${city.id}">${city.name}</option>`);
        });

        this.comboCity.html(options.join(''));
        this.comboCity.removeAttr('disabled');

        let selectedCityId = this.inputHiddenSelectedCity.val();
        if(selectedCityId) {
            this.comboCity.val(selectedCityId);
        }
    }

    function resetComboCity() {
        this.comboCity.html('<option value="">Selecione uma cidade</option>');
        this.comboCity.val('');
        this.comboCity.attr('disabled', 'disabled');
    }

    return CityCombo;

})();

$(function() {
    var comboState = new CasuaisContas.StateCombo();
    comboState.start();

    var comboCity =  new CasuaisContas.CityCombo(comboState);
    comboCity.start();
});