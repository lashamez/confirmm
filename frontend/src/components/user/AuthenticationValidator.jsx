import React, {Component} from "react";
import ApiService from "../service/ApiService";
class AuthenticationValidator{

    isAuthorized() {
        return ApiService.isValid(localStorage.getItem("token"))
    }
}
export default AuthenticationValidator