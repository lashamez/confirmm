import React, {Component} from 'react'
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import {fields, projectTypes} from "../../Const/CreateProjectFieldLabelConstants";
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core";
import {teamRoles} from "../../Const/AuditTeamRoles";
const useStyles = makeStyles(theme => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));
class AuditTeam extends Component {
    constructor(props) {
        super(props);
        this.state = {
            members: []
        }
    }

    render() {
        return (
            <form className={useStyles.form} noValidate>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        {teamRoles.map(field=>{
                            return <TextField
                                variant="outlined"
                                required
                                fullWidth
                                label={fields.name.label}
                                value={this.state.name}
                                onChange={this.onChange}
                                name={fields.name.name}
                                autoComplete="name"
                            />
                        })}
                    </Grid>
                </Grid>
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    onClick={this.saveProject}
                    className={useStyles.submit}>
                    შექმნა
                </Button>
            </form>

        )
    }

}