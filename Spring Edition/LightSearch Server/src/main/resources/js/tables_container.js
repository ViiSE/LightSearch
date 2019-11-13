import ClientsTable from '/js/table_cl.js';
'use strict';
const e = React.createElement;

class TablesContainer extends React.Component {
    constructor() {
        super();
    }

    render() {
        return e('div', {id: 'tablesContainer'},
                    e(ClientsTable, null));
    }
}

export default Tables;