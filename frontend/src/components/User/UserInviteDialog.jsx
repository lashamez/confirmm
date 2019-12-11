import React from 'react';
import Button from '@material-ui/core/Button/index';
import TextField from '@material-ui/core/TextField/index';
import Dialog from '@material-ui/core/Dialog/index';
import DialogActions from '@material-ui/core/DialogActions/index';
import DialogContent from '@material-ui/core/DialogContent/index';
import DialogTitle from '@material-ui/core/DialogTitle/index';
import ApiService from "../Service/ApiService";

export default function InviteDialog(props) {
    const [open, setOpen] = React.useState(false)
    const [firstName, setFirstName] = React.useState('')
    const [lastName, setLastName] = React.useState('')
    const [email, setEmail] = React.useState('')

    const handleClickOpen = () => {
        setOpen(true)
    };

    const handleClose = () => {
        setOpen(false)
    };

    const handleInvite = () => {
        let login = {
            firstName, lastName, email
        }
        ApiService.invite(login)
            .then(() => {
                handleClose()
                props.addRow(login)
            });
    }

    return (
        <div>
            <Button variant="text" color="primary" fullWidth onClick={handleClickOpen}>
                თანამშრომლის მოწვევა
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
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
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        id="firstName"
                        label="სახელი"
                        type="text"
                        name="firstName"
                        required={true}
                        value={firstName}
                        onChange={e => setFirstName(e.target.value)}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        id="lastName"
                        label="გვარი"
                        type="text"
                        name="lastName"
                        required={true}
                        value={lastName}
                        onChange={e => setLastName(e.target.value)}
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        გაუქმება
                    </Button>
                    <Button onClick={handleInvite} color="primary">
                        მოწვევა
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );

}

