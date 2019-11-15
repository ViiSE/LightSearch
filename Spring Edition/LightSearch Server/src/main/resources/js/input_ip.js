import ApplyButtonOptions from '/js/button_apply_options.js';
'use strict';
const e = React.createElement;

class IPInput extends React.Component {
    constructor() {
        super();
        this.state = {ip: '', validate: false};
        this.inputOnChange = this.inputOnChange.bind(this);
    }

    inputOnChange(e) {
        if(e.target.value != '') {
            var pattern = new RegExp('\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\.|$)){4}\b');
            var cValidate = pattern.test(e.target.value);
            this.setState({ip: e.target.value, validate: cValidate});
        }
    }

    render() {
        return e('div', null,
                    e('label', {id: 'iplabel'}, 'Input IP: '),
                    e('input', {id: 'ipinput', onChange: this.inputOnChange, value: this.state.ip}, null),
                    e(ApplyButtonOptions, {id: 'btnApplyDs', disabled: !this.state.validate}));
    }
}

export default IPInput;