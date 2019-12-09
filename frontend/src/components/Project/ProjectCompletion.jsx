import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
import {panelItems} from "../Const/ProjectCompletionConstants";
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
                <CustomPanel title={panelItems.materialityOverrate} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.generalRiskUpdate} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.finalAnalytics} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.riskEstimationAndFinalReview} content={"Content4"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.financialReportingProcess} content={"Content5"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.completionAndSignature} content={"Content6"} projectId={this.state.projectId}/>

            </div>
        )
    }
}
export default ProjectCompletion