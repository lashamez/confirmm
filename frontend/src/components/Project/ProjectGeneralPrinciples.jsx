import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
import {panelItems} from "../Const/ProjectGeneralPrinciplesConstants";
class ProjectGeneralPrinciples extends Component{
    constructor(props) {
        super(props);
        this.state={
            projectId: this.props.projectId
        }

    }
    render() {
        return (
            <div>
                <CustomPanel title={panelItems.scope} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.scaling} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.projectTeam} content={"Content3"} projectId={this.state.projectId}/>
            </div>
        )
    }
}
export default ProjectGeneralPrinciples