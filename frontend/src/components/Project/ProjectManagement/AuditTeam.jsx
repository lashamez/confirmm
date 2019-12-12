import React, {Component} from 'react'
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core";
import {teamRoles} from "../../Const/AuditTeamRoles";
import ApiService from "../../Service/ApiService";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Table from "@material-ui/core/Table";
import {toast} from "react-toastify";
import ProjectService from "../../Service/ProjectService";


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
            allMembers: props.allMembers,
            projectMembers: [],
            currentMember: {
                email: '',
                role: ''
            }
        }
        this.reloadMembers = this.reloadMembers.bind(this)
        this.onChange = this.onChange.bind(this)
        this.onSave = this.onSave.bind(this)
    }

    reloadMembers() {
        ApiService.findMyTeamMembers().then(res => {
            this.setState({allMembers: res.data})
        }).catch(error => {
            toast.error('გუნდის წევრების ჩატვირთვის დროს დაფიქსირდა შეცდომა')
        })
    }

    onSave() {
        let members = this.state.projectMembers
        if (this.state.currentMember.email === '' || this.state.currentMember.role === '') {
            toast.error('ელ-ფოსტა ან როლი ცარიელია. გთხოვთ შეავსოთ ორივე ველი')
        }else {
            members.push(this.state.currentMember)
            this.setState({projectMembers: members, currentMember: {email: '', role: ''}})
        }

    }

    componentDidMount() {
        this.reloadMembers();
    }

    onChange(e) {
        let current = this.state.currentMember;
        if (e.target.name === 'role') {
            current.role = e.target.value
        } else {
            current.email = e.target.value
        }
        this.setState({currentMember: current});
        console.log(this.state.currentMember)
    }

    render() {
        return (
            <Grid container spacing={2}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">თანამშრომელი</TableCell>
                            <TableCell align="left">როლი</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.projectMembers.map((member, index) => {
                            return (
                                <TableRow key={index}>
                                    <TableCell align="left">{member.email}</TableCell>
                                    <TableCell align="left">{teamRoles.find(role => member.role === role.value).label}</TableCell>
                                </TableRow>
                            )
                        })}

                    </TableBody>
                </Table>
                <Grid item xs={12} sm={6}>
                    <TextField
                        select
                        variant="outlined"
                        fullWidth
                        required={true}
                        name='role'
                        label='როლი'
                        onChange={this.onChange}
                        SelectProps={{
                            native: true
                        }}
                        defaultValue={" "}
                        margin="normal"
                    >
                        {teamRoles.map(option => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </TextField>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        select
                        required={true}
                        variant="outlined"
                        fullWidth
                        name='email'
                        onChange={this.onChange}
                        SelectProps={{
                            native: true
                        }}
                        defaultValue={" "}
                        margin="normal"
                    >
                        {this.state.allMembers.map(option => (
                            <option key={option.email} value={option.email}>
                                {option.firstName} {option.lastName}
                            </option>
                        ))}
                    </TextField>
                </Grid>

                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    onClick={this.onSave}
                    className={useStyles.submit}>
                    დამატება
                </Button>
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    onClick={this.onSave}
                    className={useStyles.submit}>
                    დასრულება
                </Button>
            </Grid>

        )
    }

}

export default AuditTeam