import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ListProjectComponent from "./Project/ListProjectComponent";
import CreateProjectComponent from "./Project/CreateProjectComponent";
import React, {Component} from "react";
import Messager from "./Messager";
import ConfirmComponent from "./User/ConfirmComponent";
import EditProjectComponent from "./Project/EditProjectComponent";
import ProjectDetails from "./Project/ProjectDetails";
import RecipeReviewCard from "./Plan/Plan";
import Dashboard from "./Project/Dashboard";
class RouterComponent extends Component {
    constructor(props) {
        super(props);
        this.state={
            open: false,
            message: null
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
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

    render() {
        return(
            <div style={style}>
                {this.state.message!== null && <Messager message={this.state.message}/>}
                <Router>
                    <Switch>
                        {this.props.isAuthorized() && <Route path="/" exact render={(routeProps)=> (<Dashboard {...routeProps} />)}/>}
                        <Route path="/confirm" render={(routeProps)=> (<ConfirmComponent {...routeProps} />)}/>
                        {this.props.isAuthorized() && <Route path="/projects" exact render={(routeProps)=> (<ListProjectComponent {...routeProps} />)}/>}
                        {this.props.isAuthorized() && <Route path="/edit-project/:projectId" render={(routeProps)=> (<EditProjectComponent {...routeProps} />)}/>}
                        {this.props.isAuthorized() && <Route path="/projects/:projectId" render={(routeProps)=> (<ProjectDetails {...routeProps} />)}/>}
                        {this.props.isAuthorized() && <Route path="/add-project" render={(routeProps)=> (<CreateProjectComponent {...routeProps} />)}/>}
                        {!this.props.isAuthorized() && <Route path="/" exact render={(routeProps)=> (<RecipeReviewCard {...routeProps} />)}/>}
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