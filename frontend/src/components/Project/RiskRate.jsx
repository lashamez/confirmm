import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
import {panelItems} from "../Const/RiskRateConstants";
class RiskRate extends Component{
    constructor(props) {
        super(props);
        this.state={
            projectId: this.props.projectId
        }

    }
    render() {
        return (
            <div>
                <CustomPanel title={panelItems.mainForPartner} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.materiality} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.mainAccountDetermination} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.planarAnaliticalAnalysis} content={"Content4"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.generalRisks} content={"Content5"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.companyRisks} content={"Content6"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.companyInnerControl} content={"Content7"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.groupRelatedIssues} content={"Content8"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.otherRisksEstimation} content={"Content9"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.processes} content={"Content10"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.specialists} content={"Content11"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.riskEstimationMatch} content={"Content12"} projectId={this.state.projectId}/>
            </div>
        )
    }
}
export default RiskRate