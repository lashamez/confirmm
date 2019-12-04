import React, { Component } from 'react'
import ApiService from "../service/ApiService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class EditUserComponent extends Component {

    constructor(props){
        super(props);
        this.state ={
            userName: '',
            userId: this.props.match.params.userId,
            firstName: '',
            lastName: '',
            age: '',
            salary: '',
        }
        this.saveUser = this.saveUser.bind(this);
        this.loadUser = this.loadUser.bind(this);
    }

    componentDidMount() {
        this.loadUser();
    }

    loadUser() {
        ApiService.fetchUserById(this.state.userId)
            .then((res) => {
                console.log(res)
                let user = res.data.result;
                this.setState({
                    userId: user.userId,
                    userName: user.userName,
                    firstName: user.firstName,
                    lastName: user.lastName,
                    age: user.age,
                    salary: user.salary,
                })
            });
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });

    }

    saveUser = (e) => {
        e.preventDefault();
        let user = {userId: this.state.userId,
            userName: this.state.userName,
            password: this.state.password,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            age: this.state.age,
            salary: this.state.salary};
        ApiService.editUser(user)
            .then(res => {
                this.props.history.push({
                    pathname: '/users',
                });
                console.log(res.data)
                this.props.printMessage(res.data.message)
            });
    }

    render() {

        return (
            <div>
                <Typography variant="h4" style={style}>Edit User</Typography>
                <form>

                    <TextField type="text" placeholder="username" fullWidth margin="normal" name="userName" disabled={true} label="Username" readOnly={true} value={this.state.userName}/>

                    <TextField placeholder="First Name" fullWidth margin="normal" name="firstName" value={this.state.firstName} label="First Name" required={true} onChange={this.onChange}/>

                    <TextField placeholder="Last name" fullWidth margin="normal" name="lastName" value={this.state.lastName} label="Last Name" required={true} onChange={this.onChange}/>

                    <TextField type="number" placeholder="age" fullWidth margin="normal" name="age" value={this.state.age} label="Age" required={true} onChange={this.onChange}/>

                    <TextField type="number" placeholder="salary" fullWidth margin="normal" name="salary" value={this.state.salary} label="Salary" required={true} onChange={this.onChange}/>

                    <Button variant="contained" color="primary" onClick={this.saveUser}>Save</Button>

                </form>
            </div>
        );
    }
}

const style ={
    display: 'flex',
    justifyContent: 'center'
}

export default EditUserComponent;