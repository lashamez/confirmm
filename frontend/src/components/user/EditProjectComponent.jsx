import React, { Component } from 'react'
import ProjectService from "../service/ProjectService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Grid from "@material-ui/core/Grid";
import {makeStyles} from "@material-ui/core";

class EditProjectComponent extends Component {

    constructor(props){
        super(props);
        this.state ={
            name: '',
            startYear: '',
            endYear: '',
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
                console.log(res.data)
                let project = res.data;
                this.setState({
                    name: project.name,
                    projectType: project.projectType,
                    startYear: project.startYear,
                    endYear: project.endYear
                })
            });
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });

    }

    saveProject = (e) => {
        e.preventDefault();
        let project = {
            projectId: this.state.projectId,
            name: this.state.name,
            projectType: this.state.projectType,
            startYear: this.state.startYear,
            endYear: this.state.endYear
        };
        ProjectService.editProject(project)
            .then(res => {
                this.props.history.push({
                    pathname: '/',
                });
                console.log(res.data)
            });
    }

    render() {

        return (
            <div>
                <Typography variant="h4" style={style}>პროექტის რედაქტირება</Typography>
                <form className={useStyles.form} noValidate>
                    <Grid container spacing={2}>

                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="name"
                                disabled={true}
                                label="პროექტის სახელი"
                                value={this.state.name}
                                onChange={this.onChange}
                                name="name"
                                autoComplete="name"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                select
                                variant="outlined"
                                fullWidth
                                label="პროექტის ტიპი"
                                value={this.state.projectType}
                                onChange={this.onChange}
                                SelectProps={{
                                    native: true
                                }}
                                margin="normal"
                            >
                                {currencies.map(option => (
                                    <option key={option.value} value={option.value}>
                                        {option.label}
                                    </option>
                                ))}
                            </TextField>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                variant="outlined"
                                name="startYear"
                                fullWidth
                                margin={"normal"}
                                id="startYear"
                                label="დაწყების დრო"
                                type="date"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                required={true}
                                value={this.state.startYear}
                                onChange={this.onChange}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                variant="outlined"
                                name="endYear"
                                fullWidth
                                margin={"normal"}
                                id="endYear"
                                label="დასრულების დრო"
                                type="date"
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                required={true}
                                value={this.state.endYear}
                                onChange={this.onChange}
                            />
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
const currencies = [
    {
        value: 'large',
        label: 'დიდი',
    },
    {
        value: 'medium',
        label: 'საშუალო',
    },
    {
        value: 'small',
        label: 'პატარა',
    }
];
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