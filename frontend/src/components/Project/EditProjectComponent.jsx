import React, { Component } from 'react'
import ProjectService from "../Service/ProjectService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Grid from "@material-ui/core/Grid";
import {makeStyles} from "@material-ui/core";
import {KeyboardDatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import DateFormatter from "../helper/DateFormatter";
import {fields, projectTypes} from "../Const/CreateProjectFieldLabelConstants";
import {toast} from "react-toastify";

class EditProjectComponent extends Component {

    constructor(props){
        super(props);
        const e = new Date()
        this.state ={
            name: '',
            startYear:  e.getFullYear()+"-"+(e.getMonth()+1)+"-"+(e.getDate()),
            endYear:  e.getFullYear()+"-"+(e.getMonth()+1)+"-"+(e.getDate()),
            projectId: this.props.match.params.projectId,
            projectType: ''
        }
        this.saveProject = this.saveProject.bind(this);
        this.loadProject = this.loadProject.bind(this);
    }

    componentDidMount() {
        this.loadProject();
    }

    loadProject() {
        ProjectService.fetchProjectById(this.state.projectId)
            .then((res) => {
                let project = res.data;
                this.setState({
                    name: project.name,
                    projectType: project.projectType,
                    startYear: DateFormatter.convertLocalDateToField(project.startYear),
                    endYear: DateFormatter.convertLocalDateToField(project.endYear)
                })
            });
    }
    onStartDateChange = (e) => {
        let val = DateFormatter.convertToFieldString(e)
        this.setState({startYear: val})
    }
    onEndDateChange = (e) => {
        let val = DateFormatter.convertToFieldString(e)
        this.setState({endYear: val})
    }
    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    saveProject = (e) => {
        e.preventDefault();
        let project = {
            projectId: this.props.match.params.projectId,
            name: this.state.name,
            projectType: this.state.projectType,
            startYear: this.state.startYear,
            endYear: this.state.endYear
        };
        ProjectService.editProject(project)
            .then(res => {
                toast.success('პროექტი წარმატებით განახლდა')
                this.props.history.push({
                    pathname: '/',
                });
            }).catch(error => {
                toast.error('პროექტის განახლებისას დაფიქსირდა შეცდომა')
        });
    }

    render() {

        return (
            <div>
                <br/>
                <Typography variant="h4" style={style}>პროექტის რედაქტირება</Typography>
                <br/>
                <form className={useStyles.form} noValidate>
                    <Grid container spacing={2}>

                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id={fields.name.id}
                                label={fields.name.label}
                                value={this.state.name}
                                onChange={this.onChange}
                                name={fields.name.name}
                                autoComplete="name"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                select
                                variant="outlined"
                                fullWidth
                                name={fields.projectType.name}
                                id={fields.projectType.id}
                                label={fields.projectType.label}
                                value={this.state.projectType}
                                onChange={this.onChange}
                                SelectProps={{
                                    native: true
                                }}
                                margin="normal"
                            >
                                {projectTypes.map(option => (
                                    <option key={option.value} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </TextField>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    required={true}
                                    fullWidth
                                    disableToolbar
                                    format="dd/MM/yyyy"
                                    margin="normal"
                                    name={fields.startYear.name}
                                    id={fields.startYear.id}
                                    label={fields.startYear.label}
                                    value={this.state.startYear}
                                    onChange={this.onStartDateChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                    inputVariant="outlined"
                                />
                            </MuiPickersUtilsProvider>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                <KeyboardDatePicker
                                    required={true}
                                    fullWidth
                                    disableToolbar
                                    format="dd/MM/yyyy"
                                    margin="normal"
                                    name={fields.endYear.name}
                                    id={fields.endYear.id}
                                    label={fields.endYear.label}
                                    value={this.state.endYear}
                                    onChange={this.onEndDateChange}
                                    KeyboardButtonProps={{
                                        'aria-label': 'change date',
                                    }}
                                    inputVariant="outlined"
                                />
                            </MuiPickersUtilsProvider>
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        onClick={this.saveProject}
                        className={useStyles.submit}>
                        შენახვა
                    </Button>
                </form>
            </div>
        );
    }
}

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
const style ={
    display: 'flex',
    justifyContent: 'center'
}

export default EditProjectComponent;