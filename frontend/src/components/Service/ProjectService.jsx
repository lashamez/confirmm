import axios from 'axios';
const PROJECT_API_BASE_URL = 'http://localhost:8080/project';
const config = {
    headers: {
        authorization: localStorage.getItem("token")
    }
}
class ProjectService {

    createProject(project) {
        return axios.post(PROJECT_API_BASE_URL, project, config);
    }
    fetchProjects(){
        return axios.get(PROJECT_API_BASE_URL, config)
    }

    deleteProject(projectId) {
        return axios.delete(PROJECT_API_BASE_URL+'/'+projectId, config)
    }

    editProject(project) {
        return axios.put(PROJECT_API_BASE_URL+'/'+project.projectId, project, config)
    }
    fetchProjectById(projectId) {
        return axios.get(PROJECT_API_BASE_URL+'/'+projectId, config)
    }

    assignRoles(projectId, members) {
        let assignedRoles = []
        members.forEach( member => {
            assignedRoles.push({projectId: projectId, email: member.user.email, role: member.role})
        })
        return axios.post(PROJECT_API_BASE_URL+'/'+projectId+'/users', assignedRoles, config)
    }
}

export default new ProjectService();