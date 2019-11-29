import React, { Component } from 'react'
import ApiService from "../service/ApiService";
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class AddUserComponent extends Component{

    constructor(props){
        super(props);
        this.state ={
            userName: '',
            password: '',
            firstName: '',
            lastName: '',
            age: '',
            salary: '',
            message: null
        }
        this.saveUser = this.saveUser.bind(this);
    }

    saveUser = (e) => {
        e.preventDefault();
        let user = {userName: this.state.userName,
            password: this.state.password,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            age: this.state.age,
            salary: this.state.salary};
        ApiService.addUser(user)
            .then(res => {
                this.setState({message : 'User added successfully.'});
                this.props.printMessage(res.data.message)
                this.props.history.push('/users', { message : 'User added successfully.'});
            });

    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {
        return(
            <div>
                <Typography variant="h4" style={style}>Add User</Typography>
                <form style={formContainer}>

                    <TextField type="text" placeholder="username" fullWidth margin="normal" name="userName" value={this.state.userName} onChange={this.onChange}/>

                    <TextField type="password" placeholder="password" fullWidth margin="normal" name="password" value={this.state.password} onChange={this.onChange}/>

                    <TextField placeholder="First Name" fullWidth margin="normal" name="firstName" value={this.state.firstName} onChange={this.onChange}/>

                    <TextField placeholder="Last name" fullWidth margin="normal" name="lastName" value={this.state.lastName} onChange={this.onChange}/>

                    <TextField type="number" placeholder="age" fullWidth margin="normal" name="age" value={this.state.age} onChange={this.onChange}/>

                    <TextField type="number" placeholder="salary" fullWidth margin="normal" name="salary" value={this.state.salary} onChange={this.onChange}/>

                    <Button variant="contained" color="primary" onClick={this.saveUser}>Save</Button>
                </form>
            </div>
        );
    }
}

const formContainer = {
    display: 'flex',
    flexFlow: 'row wrap'
};

const style ={
    display: 'flex',
    justifyContent: 'center'

}

export default AddUserComponent;