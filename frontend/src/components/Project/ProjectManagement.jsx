import React, {Component} from 'react';
import CustomPanel from "./CustomPanel";

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
                <CustomPanel title={"Submenu 1"} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={"Submenu 2"} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={"Submenu 3"} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={"Submenu 4"} content={"Content4"} projectId={this.state.projectId}/>
            </div>
        )
    }
}

export default ProjectManagement;