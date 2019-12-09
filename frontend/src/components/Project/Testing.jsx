import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
import {panelItems} from "../Const/TestingConstants";
class Testing extends Component{
    constructor(props) {
        super(props);
        this.state={
            projectId: this.props.projectId
        }

    }
    render() {
        return (
            <div>
                <CustomPanel title={panelItems.controlTesting} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.substantiveTesting} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={panelItems.substantiveTestingSecondaryAccounts} content={"Content3"} projectId={this.state.projectId}/>
            </div>
        )
    }
}
export default Testing