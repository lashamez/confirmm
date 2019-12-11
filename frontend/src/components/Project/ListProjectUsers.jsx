import React, {Component} from 'react'
import {Paper} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import ApiService from "../Service/ApiService";
import Typography from "@material-ui/core/Typography";
import InviteDialog from "../User/UserInviteDialog";

import ClearIcon from '@material-ui/icons/Clear';
import CheckIcon from '@material-ui/icons/Check';

class ListProjectUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: []
        }
        this.reloadUsers = this.reloadUsers.bind(this)
    }

    reloadUsers() {
        ApiService.findMyTeamMembers().then(res => {
                this.setState({
                    users: res.data
                })
            }
        )

    }

    componentDidMount() {
        this.reloadUsers()
    }

    render() {
        return (
            <Paper elevation={10} title={"მომხმარებლები"}>
                <br/>
                <Typography variant="h4" style={style}>მომხმარებლები</Typography>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">სახელი</TableCell>
                            <TableCell align="left">გვარი</TableCell>
                            <TableCell align="left">ელ-ფოსტა</TableCell>
                            <TableCell align="left">აქტიური</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.users.map(row => {
                            return (
                                <TableRow key={row.email}>
                                    <TableCell align="left">{row.firstName}</TableCell>
                                    <TableCell align="left">{row.lastName}</TableCell>
                                    <TableCell align="left">{row.email}</TableCell>
                                    <TableCell align="left">{row.enabled?
                                        <CheckIcon fontSize={"large"} style={{color:"#4BB543"}}/> :
                                        <ClearIcon fontSize={"large"} style={{color:"#D8000C"}}/>}
                                    </TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
                <InviteDialog addRow={this.reloadUsers}/>
            </Paper>
        )
    }
}
const style = {
    display: 'flex',
    justifyContent: 'center'
}

export default ListProjectUsers
