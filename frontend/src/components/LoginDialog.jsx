import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import ApiService from "./service/ApiService";

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
        ApiService.login(login)
            .then(res => {
                this.handleClose()
                this.props.loginFunction(res)
            });
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
                        <Button onClick={this.tryLogin} color="primary">
                            შესვლა
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }

}

export default LoginDialog;