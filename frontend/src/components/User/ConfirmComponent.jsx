import React, { Component } from 'react'
import ApiService from "../Service/ApiService";
import queryString from 'query-string';
import {toast} from "react-toastify";
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
                toast.success('ექაუნთი წარმატებით გააქტიურდა')
                this.props.history.push('/')
            }).catch(error => {
                toast.error('ექაუნთის გააქტიურების დროს დაფიქსირდა შეცდომა !')
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