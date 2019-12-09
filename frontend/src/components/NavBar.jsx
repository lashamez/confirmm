import React, {Component} from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import LoginDialog from './LoginDialog';
import RegistrationDialog from "./RegistrationDialog";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
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
            this.setState({loggedIn:true})
        }
    }
    registrationFunction(res) {
        if (res.data.result != null) {
            this.setState({user: res.data.result})
        }
    }
    logout() {
        console.log(localStorage)
        localStorage.clear()
        this.setState({loggedIn:false})
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
                        {this.props.isAuthorized() && <Button color={"inherit"} onClick={this.logout}>გამოსვლა</Button>}
                    </Toolbar>
                </AppBar>
            </div>
        )
    }
}

export default NavBar;