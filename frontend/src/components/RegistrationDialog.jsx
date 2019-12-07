import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import ApiService from "./service/ApiService";

class RegistrationDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
            email:"",
            username: "",
            password: "",
            firstName:"",
            lastName:""
        }
        this.handleClickOpen = this.handleClickOpen.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.tryRegister = this.tryRegister.bind(this)

    }


    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    tryRegister() {
        let login = {
            username: this.state.username,
            password: this.state.password,
            email: this.state.email,
            firstName: this.state.firstName,
            lastName: this.state.lastName
        }
        ApiService.register(login)
            .then(res => {
                this.setState({open:false})
            });
    }
    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });
    render() {
        return (
            <div>
                <Button variant="outlined" color="inherit" onClick={this.handleClickOpen}>
                    რეგისტრაცია
                </Button>
                <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">რეგისტრაცია</DialogTitle>
                    <DialogContent>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="email"
                            label="ელ-ფოსტა"
                            type="email"
                            name="email"
                            required={true}
                            value={this.state.email}
                            onChange={this.onChange}
                            fullWidth
                        />
                        <TextField
                            margin="dense"
                            id="username"
                            label="ალიასი"
                            type="text"
                            name="username"
                            required={true}
                            value={this.state.username}
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
                        <TextField
                            margin="dense"
                            id="firstName"
                            label="სახელი"
                            type="text"
                            name="firstName"
                            required={true}
                            value={this.state.firstName}
                            onChange={this.onChange}
                            fullWidth
                        />
                        <TextField
                            margin="dense"
                            id="lastName"
                            label="გვარი"
                            type="text"
                            name="lastName"
                            required={true}
                            value={this.state.lastName}
                            onChange={this.onChange}
                            fullWidth
                        />

                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            გაუქმება
                        </Button>
                        <Button onClick={this.tryRegister} color="primary">
                            რეგისტრაცია
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }

}

export default RegistrationDialog;