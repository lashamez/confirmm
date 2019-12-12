import React, {Component} from 'react';
import Button from '@material-ui/core/Button/index';
import TextField from '@material-ui/core/TextField/index';
import Dialog from '@material-ui/core/Dialog/index';
import DialogActions from '@material-ui/core/DialogActions/index';
import DialogContent from '@material-ui/core/DialogContent/index';
import DialogTitle from '@material-ui/core/DialogTitle/index';
import ApiService from "../Service/ApiService";
import {toast} from "react-toastify";
import {connect} from 'react-redux'
import * as actions from '../../store/actions/auth'

class LoginDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
            email: "",
            password: ""
        }
        this.handleClickOpen = this.handleClickOpen.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.tryLogin = this.tryLogin.bind(this)

    }


    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    tryLogin() {
        let login = {
            email: this.state.email,
            password: this.state.password
        }
        this.handleClose()
        this.props.onLogin(login)
    }
    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });
    render() {
        return (
            <div>
                <Button variant="outlined" color="inherit" onClick={this.handleClickOpen}>
                    შესვლა
                </Button>
                <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">შესვლა</DialogTitle>
                    <DialogContent>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="email"
                            label="ელ-ფოსტა"
                            type="text"
                            name="email"
                            required={true}
                            value={this.state.email}
                            onChange={this.onChange}
                            fullWidth
                        />
                        <TextField
                            margin="dense"
                            id="password"
                            label="პასვორდი"
                            type="password"
                            name="password"
                            required={true}
                            value={this.state.password}
                            onChange={this.onChange}
                            fullWidth
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            გაუქმება
                        </Button>
                        <Button onClick={() => this.tryLogin()} color="primary">
                            შესვლა
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }

}

const mapStateToProps = state => {
    console.log(state)
    return null
}

const mapDispatchToProps = dispatch => {
    return {
        onLogin: (login) => dispatch(actions.auth(login))
    }
}

export default connect(null, mapDispatchToProps)(LoginDialog);