import React, {Component} from "react";
import CustomPanel from "./CustomPanel";

class ConstantDocumentation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: this.props.projectId
        }

    }

    render() {
        return (
            <div>
                <CustomPanel title={"Menu1"} content={"Content1"} projectId={this.state.projectId}/>
            </div>
        )
    }
}

export default ConstantDocumentation