import axios from 'axios';
const USER_API_BASE_URL = 'http://localhost:8080/users';

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
        console.log("send")
        return axios.post(USER_API_BASE_URL, login);
    }
}

export default new ApiService();