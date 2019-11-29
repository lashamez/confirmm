import React, {Component} from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import LoginDialog from './LoginDialog';
const style = {
    flexGrow: 1
}
class NavBar extends Component {
    constructor(props) {
        super(props);
        this.state={
            user:null
        }
        this.loginFunction = this.loginFunction.bind(this)
    }

    loginFunction(res) {
        console.log(res)
        if (res.data.result != null) {
            this.setState({user: res.data.result})
        }
        this.props.printMessage(res.data.message)
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
                            Confirmation
                        </Typography>
                        <LoginDialog loginFunction={this.loginFunction}/>
                    </Toolbar>
                </AppBar>
            </div>
        )
    }
}

export default NavBar;