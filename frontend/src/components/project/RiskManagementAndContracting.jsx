import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
class RiskManagementAndContracting extends Component{
    constructor(props) {
        super(props);
        this.state={
            projectId: this.props.projectId
        }

    }
    render() {
        return (
            <div>
                <CustomPanel title={"კლიენტის ბექგროუნდი"} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={"კლიენტის რისკის შეფასება"} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={"ინტერესთა კონფლიქტი კომპანიის შიგნით"} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={"დამოუკიდებლობის დეკლარაცია"} content={"Content4"} projectId={this.state.projectId}/>

                <CustomPanel title={"ბიუჯეტირება"} content={"Content5"} projectId={this.state.projectId}/>

                <CustomPanel title={"პროექტის რისკის შეფასება"} content={"Content6"} projectId={this.state.projectId}/>

                <CustomPanel title={"დაკონტრაქტება"} content={"Content7"} projectId={this.state.projectId}/>

            </div>
        )
    }
}
export default RiskManagementAndContracting