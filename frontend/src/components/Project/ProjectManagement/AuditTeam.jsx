import React, {Component} from 'react'
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core";
import {teamRoles} from "../../Const/AuditTeamRoles";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Table from "@material-ui/core/Table";
import {toast} from "react-toastify";
import ProjectService from "../../Service/ProjectService";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";


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
            projectMembers: [],
            allMembers:[],
            currentMember: {
                email: '',
                role: ''
            }
        }
        this.onChange = this.onChange.bind(this)
        this.onSave = this.onSave.bind(this)
        this.onAdd = this.onAdd.bind(this)
    }

    onAdd(e) {
        e.preventDefault()
        console.log(this.state.allMembers)

        let members = this.state.projectMembers
        if (this.state.currentMember.email === '' || this.state.currentMember.role === '') {
            toast.error('ელ-ფოსტა ან როლი ცარიელია. გთხოვთ შეავსოთ ორივე ველი')
        }else {
            members.push(this.state.currentMember)
            let allMembers = this.state.allMembers.filter(s=>s.email!==this.state.currentMember.email)
            this.setState({projectMembers: members, currentMember: {email: '', role: ''}, allMembers:allMembers})
        }

    }

    onSave() {
        let members = this.state.projectMembers
        ProjectService.assignRoles( this.props.projectId, members).then(res => {
            toast.info('როლები შენახულია')
        }).catch(err => {
            console.log(err)
            toast.error("როლების შენახვისას დაფიქსირდა შეცდომა")
        })
    }

    componentDidMount() {
        this.props.reloadMembers().then(res => {
                this.setState({allMembers: res.data})
            }
        )
    }

    onChange(e) {
        console.log(e)
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
                    <FormControl fullWidth >
                        <InputLabel id="role-select-label">როლი</InputLabel>
                        <Select
                            variant={"outlined"}
                            labelId="role-select-label"
                            id="role-simple-select"
                            value={this.state.currentMember.role}
                            onChange={this.onChange}
                            name={'role'}
                        >
                            {teamRoles.map(option => (
                                <MenuItem key={option.value} value={option.value}>
                                    {option.label}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormControl fullWidth >
                        <InputLabel id="user-select-label">მომხმარებელი</InputLabel>
                        <Select
                            variant={"outlined"}
                            labelId="user-select-label"
                            id="user-simple-select"
                            onChange={this.onChange}
                            name={'user'}
                            value={this.state.currentMember.email}
                        >
                            {this.state.allMembers.map(option => (
                                <MenuItem key={option.email} value={option.email}>
                                    {option.firstName} {option.lastName}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>

                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    onClick={this.onAdd}
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