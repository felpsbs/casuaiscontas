var CasuaisContas = CasuaisContas || {};

CasuaisContas.LastSixMonethsGrafic = (function() {

    function LastSixMonethsGrafic() {
        this.ctx = $('#lastSixMonths')[0].getContext('2d');
    }

    LastSixMonethsGrafic.prototype.start = function() {
        var myChart = new Chart(this.ctx, {
            type: 'bar',
            data: {
                labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'],
                datasets: [{
                    label: 'Gastos no mês R$',
                    data: [1500.99, 800, 35.90, 100, 400, 956.90],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }

    return LastSixMonethsGrafic;

})();

CasuaisContas.StatusBillGrafic = (function() {

    function StatusBillGrafic() {
        this.grafic = $('#statusBillGrafic');
        this.ctx = this.grafic[0].getContext('2d');
    }

    StatusBillGrafic.prototype.start = function() {
        $.ajax({
			url: this.grafic.data('url'),
			method: 'GET',
			success: onSuccessData.bind(this)
		})
    }

    function onSuccessData(response) {
        let data = [response.paid, response.notPaid];
        var myPieChart = new Chart(this.ctx, {
            type: 'doughnut',
            data: {
                labels: ['Contas Pagas R$', 'Contas não pagas R$'],
                datasets: [{
                    data,
                    backgroundColor: [
                        'rgba(0, 230, 64, 1)',
                        'rgba(242, 38, 19, 1)',                          
                    ],
                    borderColor: [
                        'rgba(0, 230, 64, 0.2)',
                        'rgba(242, 38, 19, 0.2)',                         
                    ],
                    borderWidth: 1
                }]
            },
        });
    }

    return StatusBillGrafic;

})();

$(function() {
    var lastSixMonethsGrafic = new CasuaisContas.LastSixMonethsGrafic();
    lastSixMonethsGrafic.start();

    var statusBillGrafic = new CasuaisContas.StatusBillGrafic();
    statusBillGrafic.start();
});