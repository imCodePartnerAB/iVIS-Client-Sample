function InputAutocomplete(inputSelector, selectSelectorHiddenStorage) {
    this.inputSelector = inputSelector;
    this.selectSelectorHiddenStorage = selectSelectorHiddenStorage;
}

(function () {
    function list(selectSelectorHiddenStorage) {
        var data = [];
        $(selectSelectorHiddenStorage + ' > option').each(function () {
            data.push({
                label: this.text,
                value: this.value
            });
        });
        return data;
    }

    InputAutocomplete.prototype = {
        autoComplete: function (name, divSelectorAccumulator) {
            var inputSelector = this.inputSelector;
            var selectSelectorHiddenStorage = this.selectSelectorHiddenStorage;
            $(inputSelector).autocomplete({
                source: list(selectSelectorHiddenStorage),
                select: function( event, ui ) {
                    var prefix = 'pupil';
                    var $span = $('<span>')
                        .html(' X')
                        .attr('style', 'color: red;');
                    var $label = $('<label>')
                        .attr('for', prefix + ui.item.value)
                        .html(ui.item.label)
                        .append($span);
                    var $input = $('<input>')
                        .attr('id', prefix + ui.item.value)
                        .attr('type', 'hidden')
                        .attr('name', name)
                        .val(ui.item.value);
                    var $br = $('<br>');

                    $(divSelectorAccumulator).append($label).append($input).append($br);
                    $label.click(function () {
                        $input.remove();
                        $label.remove();
                        $br.remove();
                    });
                    $(inputSelector).val('');
                    return false;
                }
            });
        }
    };
})();
