import React from 'react';
import './App.css';
import AppRouter from "./components/RouterComponent";
import NavBar from "./components/NavBar";
import Container from '@material-ui/core/Container';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
function isAuthorized(){
    return localStorage.getItem("token")!==null;
}
function App() {
    return (
        <div>
            <NavBar isAuthorized={isAuthorized}/>
            <Container>
                <AppRouter isAuthorized={isAuthorized}/>
            </Container>
            <ToastContainer/>
        </div>
    );
}

export default App;
