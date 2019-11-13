'use strict';
const e = React.createElement;

class ApplyClButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {actions: [], disabled: true};
        this.handleClick = this.handleClick.bind(this);
        this.addAction = this.addAction.bind(this);
        this.removeAction = this.removeAction.bind(this);
    }

    componentDidMount() {
        this.TimerID = setInterval(() => this.checkProcessedActions(), 1000);
    }

    checkProcessedActions() {
        var activeActions = [];
        if(this.state.actions.length != 0)
            for(var i = 0; i < this.state.actions.length; i++)
                if(!this.state.actions[i].isProcessed)
                    activeActions.push(this.state.actions[i]);
        this.setState({actions: activeActions});
    }

    handleClick() {
        for(var i = 0; i < this.state.actions.length; i++) {
            var action = this.state.actions[i];
            fetch('/commands/type/admin', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify(action)
                        })
                        .then(response => response.json())
                        .then((result) => {var rm = 'isDone: ' + result.isDone + '\nmessage: ' + result.message; if(!result.isDone) {nfcWarn({title: 'Warning', message: rm});} else {nfcSuccess({title: 'Success', message: rm});}},
                              (error) => {nfcErr({title: 'Error', message: error.message});});
            this.state.actions[i].isProcessed = true;
        }
    }

    addAction(action) {
        this.setState(state => {
            if(state.actions.find(_action => _action.IMEI == action.IMEI) == undefined) {
                var newActions = state.actions.concat(action);
                console.log('newActions.length: ' + newActions.length);
                return {actions: newActions, disabled: false};
            } else {
                console.log('i am undefined');
                var foundIndex = state.actions.findIndex(_action => _action.IMEI == action.IMEI);
                if(foundIndex != -1)
                    state.actions[foundIndex] = action;
                return {actions: state.actions, disabled: false};
            }
        });
    }

    removeAction(IMEI) {
        this.setState(state => {
            var newActions = state.actions.filter(action => action.IMEI != IMEI)
            var newDisabled = false;
            if(newActions.length == 0)
                newDisabled = true;
            return {actions: newActions, disabled: newDisabled};
        });
    }

    render() {
        return e('button', {id:'btnApplyClients', disabled: this.state.disabled, onClick: this.handleClick}, 'Apply');
    }
}

export default ApplyClButton;