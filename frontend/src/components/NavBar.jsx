import React, {Component} from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import LoginDialog from './LoginDialog';
import RegistrationDialog from "./RegistrationDialog";
const style = {
    flexGrow: 1
}
class NavBar extends Component {
    constructor(props) {
        super(props);
        this.loginFunction = this.loginFunction.bind(this)
    }

    loginFunction(res) {
        console.log(res.headers.authorization)
        if (res.headers.authorization !== null) {
            localStorage.setItem('token', res.headers.authorization);
        }
    }
    registrationFunction(res) {
        if (res.data.result != null) {
            this.setState({user: res.data.result})
        }
    }
    isAuthorized(){
        return localStorage.getItem("token")!==null;
    }
    render() {
        return (
            <div>
                <AppBar position="static">
                    <Toolbar>
                        <IconButton edge="start" color="inherit" aria-label="Menu">
                            <MenuIcon />
                        </IconButton>
                        <Typography variant="h6" style={style}>
                            Audit
                        </Typography>
                        {!this.isAuthorized() && <LoginDialog loginFunction={this.loginFunction}/>}
                        {!this.isAuthorized()  && <RegistrationDialog registrationFunction={this.registrationFunction}/>}
                    </Toolbar>
                </AppBar>
            </div>
        )
    }
}

export default NavBar;