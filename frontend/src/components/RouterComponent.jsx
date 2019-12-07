import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ListProjectComponent from "./user/ListProjectComponent";
import CreateProjectComponent from "./user/CreateProjectComponent";
import React, {Component} from "react";
import Messager from "./Messager";
import ConfirmComponent from "./user/ConfirmComponent";
import EditProjectComponent from "./user/EditProjectComponent";
import ProjectDetails from "./project/ProjectDetails";
class RouterComponent extends Component {
    constructor(props) {
        super(props);
        this.state={
            open: false,
            message: null
        }
    }


    componentDidUpdate(prevProps, prevState, snapshot) {
        console.log(prevState)
        if (prevState.message !== null) {
            this.setState({message: null})
        }
    }
    componentWillUnmount() {
        this.setState({message: null})
    }

    componentDidMount() {
        this.setState({message: null})
    }
    isAuthorized(){
        console.log()
        return localStorage.getItem("token")!==null;
    }
    render() {
        return(
            <div style={style}>
                {this.state.message!== null && <Messager message={this.state.message}/>}
                <Router>
                    <Switch>
                        {this.isAuthorized() && <Route path="/" exact render={(routeProps)=> (<ListProjectComponent {...routeProps} />)}/>}
                        <Route path="/confirm" render={(routeProps)=> (<ConfirmComponent {...routeProps} />)}/>
                        {this.isAuthorized() && <Route path="/project" render={(routeProps)=> (<ListProjectComponent {...routeProps} />)}/>}
                        {this.isAuthorized() && <Route path="/edit-project/:projectId" render={(routeProps)=> (<EditProjectComponent {...routeProps} />)}/>}
                        {this.isAuthorized() && <Route path="/projectDetails/:projectId" render={(routeProps)=> (<ProjectDetails {...routeProps} />)}/>}
                        {this.isAuthorized() && <Route path="/add-project" render={(routeProps)=> (<CreateProjectComponent {...routeProps} />)}/>}
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