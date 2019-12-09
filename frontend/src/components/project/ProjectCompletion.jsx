import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
class ProjectCompletion extends Component{
    constructor(props) {
        super(props);
        this.state={
            projectId: this.props.projectId
        }

    }
    render() {
        return (
            <div>
                <CustomPanel title={"მატერიალობის გადაფასება"} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={"ზოგადი რისკების განახლება"} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={"ფინალური ანალიტიკა"} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={"რისკების შეფასების განახლება და საბოლოო მიმოხილვა"} content={"Content4"} projectId={this.state.projectId}/>

                <CustomPanel title={"ფინანსური რეპორტინგის პროცესი"} content={"Content5"} projectId={this.state.projectId}/>

                <CustomPanel title={"დასრულება და ხელმოწერა"} content={"Content6"} projectId={this.state.projectId}/>

            </div>
        )
    }
}
export default ProjectCompletion