import axios from 'axios';
const USER_API_BASE_URL = 'http://localhost:8080/project';
const config = {
    headers: {
        authorization: localStorage.getItem("token")
    }
}
class ProjectService {

    createProject(project) {
        console.log(project)
        return axios.post(USER_API_BASE_URL, project, config);
    }
    fetchProjects(){
        return axios.get(USER_API_BASE_URL, config)
    }

    deleteProject(projectId) {
        return axios.delete(USER_API_BASE_URL+'/'+projectId, config)
    }

    editProject(project) {
        return axios.put(USER_API_BASE_URL+'/'+project.projectId, project, config)
    }
    fetchProjectById(projectId) {
        return axios.get(USER_API_BASE_URL+'/'+projectId, config)
    }
}

export default new ProjectService();