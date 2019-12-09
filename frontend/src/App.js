import React from 'react';
import './App.css';
import AppRouter from "./components/RouterComponent";
import NavBar from "./components/NavBar";
import Container from '@material-ui/core/Container';
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
        </div>
    );
}

export default App;
