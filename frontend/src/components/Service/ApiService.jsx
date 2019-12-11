import axios from 'axios';
const USER_API_BASE_URL = 'http://localhost:8080/users';
const API_BASE_URL = 'http://localhost:8080'
const config = {
    headers: {
        authorization: localStorage.getItem("token")
    }
}
class ApiService {

    fetchUsers() {
        return axios.get(USER_API_BASE_URL);
    }

    fetchUserById(userId) {
        return axios.get(USER_API_BASE_URL + '/' + userId)
    }

    activateUserByToken(key) {
        return axios.get(USER_API_BASE_URL+'/confirm?token='+key)
    }

    deleteUser(userId) {

        return axios.delete(USER_API_BASE_URL + '/' + userId);
    }

    addUser(user) {
        return axios.post(""+USER_API_BASE_URL, user);
    }

    editUser(user) {
        return axios.put(USER_API_BASE_URL + '/' + user.userId, user);
    }

    login(login) {
        return axios.post(USER_API_BASE_URL + '/login', login);
    }
    register(login) {
        return axios.post(USER_API_BASE_URL, login);
    }
    registerCompany(props) {
        return axios.post(API_BASE_URL+"/register", props, null)
    }

    findMyTeamMembers() {
        return axios.get(USER_API_BASE_URL + "/team", config)
    }

    invite(login) {
        return axios.post(USER_API_BASE_URL + "/invite", login, config)
    }
}

export default new ApiService();