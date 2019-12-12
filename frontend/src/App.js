import React, {Component} from 'react';
import './App.css';
import AppRouter from "./components/RouterComponent";
import NavBar from "./components/NavBar";
import Container from '@material-ui/core/Container';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {connect} from 'react-redux'


class App extends Component{
    render() {
        console.log(this.props.isAuthenticated)
        return (
            <div>
                <NavBar isAuthenticated={this.props.isAuthenticated}/>
                <Container>
                    <AppRouter isAuthenticated={this.props.isAuthenticated}/>
                </Container>
                <ToastContainer/>
            </div>
        );
    }
}
const mapStateToProps = state => {
    return {
        isAuthenticated: state.auth.token!==null
    }
}

export default connect(mapStateToProps)(App);
