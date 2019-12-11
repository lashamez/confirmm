import React, {Component} from 'react';
import CustomPanel from "../CustomPanel";
import {panelItems} from "../../Const/ProjectManagementConstants";
import AuditTeam from "./AuditTeam";
import Box from "@material-ui/core/Box";

class ProjectManagement extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: this.props.projectId
        }
    }

    render() {
        return (
            <Box>
                <CustomPanel title={panelItems.auditTeam} content={<AuditTeam allMembers={this.props.allMembers}/>} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.taskManagement} content={"Content2"} projectId={this.state.projectId}/>
            </Box>
        )
    }
}

export default ProjectManagement;