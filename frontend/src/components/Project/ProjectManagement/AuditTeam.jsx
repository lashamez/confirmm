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
import DeleteIcon from '@material-ui/icons/Delete';


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
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

class AuditTeam extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectMembers: [],
            allMembers: [],
            allMembersCopy: [],
            currentMember: {
                email: '',
                role: ''
            }
        }
        this.onChange = this.onChange.bind(this)
        this.onSave = this.onSave.bind(this)
        this.onAdd = this.onAdd.bind(this)
        this.deleteMember = this.deleteMember.bind(this)
    }

    deleteMember(index) {
        let member = this.state.projectMembers[index]
        let originalMember = this.state.allMembersCopy.find(s=>s.email === member.email)
        const projectMembers = this.state.projectMembers.filter(user => user !== member)
        const allMembers = [...this.state.allMembers, originalMember]
        this.setState({projectMembers: projectMembers, allMembers: allMembers})
    }

    onAdd(e) {
        e.preventDefault()
        const members = [...this.state.projectMembers];

        if (this.state.currentMember.email === '' || this.state.currentMember.role === '') {
            toast.error('ელ-ფოსტა ან როლი ცარიელია. გთხოვთ შეავსოთ ორივე ველი')
        } else {
            const current = this.state.allMembers.find(user => user.email === this.state.currentMember.email)
            current.role = this.state.currentMember.role
            members.push(current)
            let allMembers = this.state.allMembers.filter(s => s.email !== this.state.currentMember.email)
            this.setState({projectMembers: members, currentMember: {email: '', role: ''}, allMembers: allMembers})
        }

    }

    onSave() {
        let members = this.state.projectMembers
        ProjectService.assignRoles(this.props.projectId, members).then(res => {
            toast.info('როლები შენახულია')
        }).catch(err => {
            console.log(err)
            toast.error("როლების შენახვისას დაფიქსირდა შეცდომა")
        })
    }

    componentDidMount() {

        this.props.reloadUserRoles().then(res => {
            this.setState({projectMembers: res.data.users})
            this.props.reloadMembers().then(resp => {
                    this.setState({allMembersCopy: resp.data})
                    let unassignedMembers = resp.data.filter(s => this.state.projectMembers.find(user => user.email === s.email) === undefined)
                    console.log(unassignedMembers)
                    this.setState({allMembers: unassignedMembers})
                }
            )
        }).catch(err => {
            toast.error("დაფიქსირდა შეცდომა")
        })

    }

    onChange(e) {
        let current = this.state.currentMember;
        if (e.target.name === 'role') {
            current.role = e.target.value
        } else {
            current.email = e.target.value
        }
        this.setState({currentMember: current});
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
                                    <TableCell
                                        align="left">{teamRoles.find(role => member.role === role.value).label}</TableCell>
                                    <TableCell>
                                        <DeleteIcon onClick={() => this.deleteMember(index)}/>
                                    </TableCell>
                                </TableRow>
                            )
                        })}

                    </TableBody>
                </Table>
                {this.state.allMembers.length > 0 &&
                <Grid item xs={12} sm={6}>
                    <FormControl fullWidth className={useStyles.formControl}>
                        <InputLabel id="role-select-label">როლი</InputLabel>
                        <Select
                            className={useStyles.selectEmpty}
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
                }
                {this.state.allMembers.length > 0 &&
                    <Grid item xs={12} sm={6}>
                    <FormControl fullWidth>
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
                }
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