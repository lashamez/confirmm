import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Fade from '@material-ui/core/Fade';
import TextField from "@material-ui/core/TextField";
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import Button from "@material-ui/core/Button";
import ApiService from "../Service/ApiService";
import {plans} from "../Const/PlansConstants";
import {toast} from "react-toastify";

const useStyles = makeStyles(theme => ({
    root: {
        height: 180,
        margin: "0 auto",
        width: "100%"
    },
    paper: {
        margin: theme.spacing(1),
    },
    field: {
        width: "100%"
    }
}));

export default function BuyPackage(props) {
    const classes = useStyles();
    const [checked, setChecked] = React.useState(false);
    const [email, setEmail] = React.useState('');
    const handleChange = () => {
        setChecked(prev => !prev);
    };

    const handleConfirm = () => {
        const pack ={
            "email":email,
            "packageName":plans.find(plan => plan.id === props.id).value
        }
        ApiService.registerCompany(pack).then(res => {
            toast.success('თქვენ წარმატებით შეიძინეთ პაკეტი, რეგისტრაციის დროს გამოიყენეთ მითითებული ელ-ფოსტა')
        }).catch(error => {
            toast.error('რეგისტრაციის დროს დაფიქსირდა შეცდომა')
        })
    }

    return (
        <div className={classes.root}>
            <Button variant="contained" color="primary" fullWidth onClick={handleChange}>
                <ShoppingCartIcon/>ყიდვა
            </Button>
            <Fade in={checked} >
                <form >
                    <TextField
                        className={classes.field}
                        autoFocus
                        color={"secondary"}
                        variant={"outlined"}
                        margin="dense"
                        label="ელ-ფოსტა"
                        type="text"
                        name="email"
                        required={true}
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <Button fullWidth variant={"contained"} color={"primary"} onClick={handleConfirm}>დადასტურება</Button>
                </form>

            </Fade>
        </div>
    );
}