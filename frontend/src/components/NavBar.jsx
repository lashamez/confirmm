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
import * as actions from "../store/actions/auth";
import {connect} from "react-redux";
const style = {
    flexGrow: 1
}
export function NavBar(props) {
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
                    {!props.isAuthenticated && <LoginDialog/>}
                    {!props.isAuthenticated  && <RegistrationDialog />}
                    {props.isAuthenticated && (
                        <ProfileTopBar logout={props.onLogout}/>
                    )}
                </Toolbar>
            </AppBar>
        </div>
    )
}


const mapDispatchToProps = dispatch => {
    return {
        onLogout: () => dispatch(actions.logout())
    }
}

export default connect(null, mapDispatchToProps)(NavBar);
