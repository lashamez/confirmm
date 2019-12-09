import React, { Component } from 'react'
import ApiService from "../service/ApiService";
import queryString from 'query-string';
class ConfirmComponent extends Component {

    constructor(props){
        super(props);
        this.state ={
            confirmed:false
        }
        this.loadUser = this.loadUser.bind(this);
    }

    componentDidMount() {
        this.loadUser();
    }

    loadUser() {
        let url = this.props.location.search;
        let params = queryString.parse(url);
        let key = params.token;
        ApiService.activateUserByToken(key)
            .then((res) => {
                this.props.history.push('/')
            });
    }

    render() {
        return (
            <div>

            </div>
        );
    }
}

export default ConfirmComponent;