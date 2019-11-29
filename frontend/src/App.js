import React from 'react';
import './App.css';
import AppRouter from "./components/RouterComponent";
import NavBar from "./components/NavBar";
import Container from '@material-ui/core/Container';

function App() {
    return (
        <div>
            <NavBar/>
            <Container>
                <AppRouter/>
            </Container>
        </div>
    );
}

export default App;
