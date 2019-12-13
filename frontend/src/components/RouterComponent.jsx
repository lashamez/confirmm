import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ListProjectComponent from "./Project/ListProjectComponent";
import CreateProjectComponent from "./Project/CreateProjectComponent";
import React, {Component} from "react";
import ConfirmComponent from "./User/ConfirmComponent";
import EditProjectComponent from "./Project/EditProjectComponent";
import ProjectDetails from "./Project/ProjectDetails";
import RecipeReviewCard from "./Plan/Plan";
import Dashboard from "./Project/Dashboard";
class RouterComponent extends Component {

    render() {
        return(
            <div style={style}>
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