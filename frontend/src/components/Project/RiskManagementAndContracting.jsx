import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
import {panelItems} from "../Const/RiskManagementAndContractingConstants";

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
                <CustomPanel title={panelItems.clientBackground} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.clientRiskRate} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.interestConflict} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.independenceDeclaration} content={"Content4"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.budgeting} content={"Content5"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.projectRiskRate} content={"Content6"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.contracting} content={"Content7"} projectId={this.state.projectId}/>

            </div>
        )
    }
}
export default RiskManagementAndContracting