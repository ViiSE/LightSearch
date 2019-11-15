import IPInput from '/js/input_ip.js';
'use strict';
const e = React.createElement;

class OptionsContainer extends React.Component {
    constructor() {
        super();
    }

    render() {
        return e('div', {id: 'optionsContainer'},
                    e('label', {id:'ctitle'}, 'Options'),
                        e('div', {id:'ds'},
                            e('label', {id:'dsopt'}, 'Datasource'),
                                e(IPInput, null)));
    }
}

export default OptionsContainer;
