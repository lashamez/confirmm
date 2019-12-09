import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
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
                <CustomPanel title={"კონტროლების ტესტირება"} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={"საბსთენთივე თესტინგი"} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={"საბსთენთივე თესთინგი არამთავარი ანგარიშები და სხვა ანგარიშგების ნაწილები"} content={"Content3"} projectId={this.state.projectId}/>
            </div>
        )
    }
}
export default Testing