'use strict';
const e = React.createElement;

var isErrorShow = true;

class ClientTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {clients: []};


    }

    componentDidMount() {
        this.timerID = setInterval(
            () => this.refreshTable(),
            1000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    refreshTable() {
        fetch('/commands/type/admin?command=clientList')
    		.then(response => response.json())
    		.then((result) => {this.setState({clients: result.clients})},
    		      (error) => {if(isErrorShow){isErrorShow = false; alert(error);}});
    }

    renderTableData() {
        return this.state.clients.map(client => {
            const {IMEI, username, timeout} = client;
            return e('tr', {key: IMEI},
                        e('td', null, IMEI),
                        e('td', null, username),
                        e('td', null, timeout),
                        e('td', null,
                            e('select', null,
                                e('option', {value:'none'}, "None"),
                                e('option', {value:'kick'}, "Kick"),
                                e('option', {value:'addBlacklist'}, "Add to the Blacklist"))));
        });
    }

    render() {
        return e('div', {id: 'divClTable'},
                    e('h2', {id: 'clTitle'}, 'Clients'),
                    e('table', {align:'center', id:'clients'},
                        e('thead', null,
                            e('tr', null,
                                e('th', {key: 'IMEI'}, 'IMEI'),
                                e('th', {key: 'username'}, 'Username'),
                                e('th', {key: 'timeout'}, 'Timeout'),
                                e('th', {key: 'actions'}, 'Actions'))),
                        e('tbody', null,
                            this.renderTableData())),
                    e('button', {id:'btnApplyClients'}, "Apply"));
    }
}

ReactDOM.render(e(ClientTable, null), document.getElementById('root'));
