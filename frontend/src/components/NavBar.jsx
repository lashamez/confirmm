import React, {Component} from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import LoginDialog from './User/LoginDialog';
import RegistrationDialog from "./User/RegistrationDialog";
import Link from "@material-ui/core/Link";
import ProfileTopBar from "./ProfileTopBar";
import {toast} from "react-toastify";

const style = {
    flexGrow: 1
}
class NavBar extends Component {
    constructor(props) {
        super(props);
        this.loginFunction = this.loginFunction.bind(this)
        this.logout = this.logout.bind(this)
        this.state={
            loggedIn: false
        }
    }

    loginFunction(res) {
        console.log(res.headers.authorization)
        if (res.headers.authorization !== null) {
            localStorage.setItem('token', res.headers.authorization);
            localStorage.setItem('')
            this.setState({loggedIn:true})
        }
        window.location.reload();
    }
    registrationFunction(res) {
        if (res.data.result != null) {
            this.setState({user: res.data.result})
        }
    }
    logout() {
        localStorage.clear()
        toast.info('See you soon !')
        this.setState({loggedIn:false})
        console.log("here")
        window.location.reload();
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
                           <Link href={'/'} color={"inherit"} style={{textDecoration:"none"}}>Audit</Link>
                        </Typography>
                        {!this.props.isAuthorized() && <LoginDialog loginFunction={this.loginFunction}/>}
                        {!this.props.isAuthorized()  && <RegistrationDialog registrationFunction={this.registrationFunction}/>}
                        {this.props.isAuthorized() && (
                            <ProfileTopBar logout={this.logout}/>
                        )}
                    </Toolbar>
                </AppBar>
            </div>
        )
    }
}

export default NavBar;