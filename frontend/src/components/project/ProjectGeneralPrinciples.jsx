import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
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
                <CustomPanel title={"სქოუფი"} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={"სქეილინგი"} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={"პროექტის გუნდის შემადგენლობა"} content={"Content3"} projectId={this.state.projectId}/>
            </div>
        )
    }
}
export default ProjectGeneralPrinciples