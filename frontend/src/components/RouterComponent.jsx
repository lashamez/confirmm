import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ListUserComponent from "./user/ListUserComponent";
import AddUserComponent from "./user/AddUserComponent";
import EditUserComponent from "./user/EditUserComponent";
import React, {Component} from "react";
import Messager from "./Messager";

class RouterComponent extends Component {
    constructor(props) {
        super(props);
        this.state={
            open: false,
            message: null
        }
        this.printMessage = this.printMessage.bind(this)
    }

    printMessage(message) {
        this.setState({message: message})
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        console.log(prevState)
        if (prevState.message !== null) {
            this.setState({message: null})
        }
    }

    render() {

        return(
            <div style={style}>
                {this.state.message!== null && <Messager message={this.state.message}/>}
                <Router>
                    <Switch>
                        <Route path="/" exact render={(routeProps)=> (<ListUserComponent {...routeProps} printMessage={this.printMessage}/>)} />
                        <Route path="/users" render={(routeProps)=> (<ListUserComponent {...routeProps} printMessage={this.printMessage}/>)} />
                        <Route path="/add-user" render={(routeProps)=> (<AddUserComponent {...routeProps} printMessage={this.printMessage}/>)} />
                        <Route path="/edit-user/:userId" render={(routeProps)=> (<EditUserComponent {...routeProps} printMessage={this.printMessage}/>)} />
                    </Switch>
                </Router>
            </div>
        )
    }
}

const style={
    marginTop:'20px'
}

export default RouterComponent;