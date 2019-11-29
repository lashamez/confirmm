import React, { Component } from 'react'
import ApiService from "../service/ApiService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from '@material-ui/icons/Delete';
import Typography from '@material-ui/core/Typography';
import { Link } from 'react-router-dom';

class ListUserComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            users: [],
        }
        this.deleteUser = this.deleteUser.bind(this);
        this.editUser = this.editUser.bind(this);
        this.reloadUserList = this.reloadUserList.bind(this);
    }

    componentDidMount() {
        this.reloadUserList();
    }

    reloadUserList() {
        ApiService.fetchUsers()
            .then((res) => {
                this.setState({users: res.data.result})
            });
    }

    deleteUser(userId) {
        ApiService.deleteUser(userId)
            .then(res => {
                this.setState({users: this.state.users.filter(user => user.userId !== userId)});
                this.props.printMessage(res.data.message)
            })
    }

    editUser(id) {
        this.props.history.push('/edit-user/'+id);
    }


    render() {
        return (
            <div>
                <Typography variant="h4" style={style}>User Details</Typography>
                <Button variant="contained" color="primary" href="/add-user">
                    Add User
                </Button>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell  align="left">First Name</TableCell>
                            <TableCell align="left">Last Name</TableCell>
                            <TableCell align="left">User Name</TableCell>
                            <TableCell align="left">Age</TableCell>
                            <TableCell align="left">Salary</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.users.map(row => (
                            <TableRow key={row.userId}>
                                <TableCell align="left">{row.firstName}</TableCell>
                                <TableCell align="left">{row.lastName}</TableCell>
                                <TableCell align="left">{row.userName}</TableCell>
                                <TableCell align="left">{row.age}</TableCell>
                                <TableCell align="left">{row.salary}</TableCell>
                                <TableCell align="left"><Link to={"edit-user/"+row.userId}><CreateIcon /></Link> </TableCell>
                                <TableCell align="left" onClick={() => this.deleteUser(row.userId)}><DeleteIcon /></TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>

            </div>
        );
    }

}

const style ={
    display: 'flex',
    justifyContent: 'center'
}

export default ListUserComponent;