import React, {Component} from 'react';
import CustomPanel from "../CustomPanel";
import {panelItems} from "../../Const/ProjectManagementConstants";
import AuditTeam from "./AuditTeam";
import Box from "@material-ui/core/Box";
import TaskAssignment from "./TaskAssignment";
import ApiService from "../../Service/ApiService";
import {toast} from "react-toastify";

class ProjectManagement extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: this.props.projectId
        }
        this.reloadMembers = this.reloadMembers.bind(this)
    }
    reloadMembers() {
        return ApiService.findMyTeamMembers().catch(error => {
            toast.error('გუნდის წევრების ჩატვირთვის დროს დაფიქსირდა შეცდომა')
        })
    }
    render() {
        return (
            <Box>
                <CustomPanel title={panelItems.auditTeam} content={<AuditTeam reloadMembers={this.reloadMembers}  projectId={this.state.projectId}/>} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.taskManagement} content={<TaskAssignment reloadMembers={this.reloadMembers} projectId={this.state.projectId}/>} projectId={this.state.projectId}/>
            </Box>
        )
    }
}

export default ProjectManagement;