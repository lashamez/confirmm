import React, {Component} from "react";
import CustomPanel from "./CustomPanel";
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
                <CustomPanel title={"მთავარი პარტნიორისთვის"} content={"Content1"} projectId={this.state.projectId}/>

                <CustomPanel title={"მატერიალობა"} content={"Content2"} projectId={this.state.projectId}/>

                <CustomPanel title={"მთავარი ანგარიშების განსაზღვრა"} content={"Content3"} projectId={this.state.projectId}/>

                <CustomPanel title={"გეგმითი ანალიტიკური ანალიზი"} content={"Content4"} projectId={this.state.projectId}/>

                <CustomPanel title={"ზოგადი რისკები"} content={"Content5"} projectId={this.state.projectId}/>

                <CustomPanel title={"კომპანიის რისკები"} content={"Content6"} projectId={this.state.projectId}/>

                <CustomPanel title={"კომპანიის შიდა კონტროლები"} content={"Content7"} projectId={this.state.projectId}/>

                <CustomPanel title={"ჯგუფთან დაკავშირებული საკითხები"} content={"Content8"} projectId={this.state.projectId}/>

                <CustomPanel title={"სხვა რისკის შეფასება ( ჯურნაური გატარებები, რეპორტინგის კონტროლები)"} content={"Content9"} projectId={this.state.projectId}/>

                <CustomPanel title={"პროცესები"} content={"Content10"} projectId={this.state.projectId}/>

                <CustomPanel title={"სპეციალისტები"} content={"Content11"} projectId={this.state.projectId}/>

                <CustomPanel title={"რისკების შეფასების შეხვედრა"} content={"Content12"} projectId={this.state.projectId}/>
            </div>
        )
    }
}
export default RiskRate