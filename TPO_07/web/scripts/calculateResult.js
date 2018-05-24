const getFormId = "getForm";
const postFromId = "postForm";
const addServlet = "addValues";

$(document).ready(function () {
    $("input").blur(function () {
        var $currForm = $(this.form);
        var $resultLabel = $('#' + $currForm.attr('class') + 'Label');

        if (!$currForm.valid()) {
            $resultLabel.html('');
            return;
        }

        var parameters = {};
        $.each($currForm.serializeArray(), function (i, input) {
            parameters[input.name] = input.value;
        });
        console.log(parameters);

        var processResponse = function (response, status) {
            console.log('processing response');
            if (status === 'success') {
                $resultLabel.html('result = ' + response);
            } else {
                console.log(status);
            }
        };

        if ($currForm.attr("id") === getFormId) {
            $.get(addServlet, parameters, processResponse);
        } else {
            $.post(addServlet, parameters, processResponse);
        }

    });

});