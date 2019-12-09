import React, {Component} from 'react';
import CustomPanel from "../CustomPanel";
import {panelItems} from "../../Const/ProjectManagementConstants";

class ProjectManagement extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: this.props.projectId
        }
    }

    render() {
        return (
            <div>
                <CustomPanel title={panelItems.auditTeam} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.taskManagement} content={"Content2"} projectId={this.state.projectId}/>
            </div>
        )
    }
}

export default ProjectManagement;