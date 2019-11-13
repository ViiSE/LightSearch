import ApplyBlButton from '/js/button_apply_bl.js';
'use strict';
const e = React.createElement;

var isErrorShow = true;
var isRefresh = true;
var currentIMEI = '';

class BlacklistTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {clients: []};

        this.mouseOverSelect = this.mouseOverSelect.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.applyBlButton = React.createRef();
    }

    componentDidMount() {
        this.timerID = setInterval(() => this.refreshTable(), 1000);
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    refreshTable() {
        if(isRefresh) {
            fetch('/commands/type/admin?command=clientList')
    		    .then(response => response.json())
    		    .then((result) => {this.setState({clients: result.clients})},
    		          (error) => {if(isErrorShow){isErrorShow = false; nfcErr({title: 'Error', message: error.message});} if(error.message.includes('NetworkError')) {isRefresh = false;}});
        }
    }

    mouseOverSelect(event) {
        currentIMEI = this.state.clients[event.currentTarget.rowIndex-1].IMEI;
    }

    handleSelect(event) {
        var actionVal = event.currentTarget.value;
        if(actionVal != 'none') {
            var action = new Object();
            action.command = actionVal;
            action.IMEI = currentIMEI;
            action.isProcessed = false;
            this.applyClButton.current.addAction(action);
        } else
            this.applyClButton.current.removeAction(currentIMEI);
        currentIMEI = '';
    }

    renderTableData() {
        return this.state.clients.map(client => {
            const {IMEI, username, timeout} = client;
            return e('tr', {key: IMEI, onMouseOver: this.mouseOverSelect},
                        e('td', null, IMEI),
                        e('td', null, username),
                        e('td', null, timeout),
                        e('td', null,
                            e('select', {onChange:this.handleSelect},
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
                    e(ApplyClButton, {ref: this.applyClButton}));
    }
}

export default ClientsTable;
