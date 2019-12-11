import React, {Component} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Stepper from "@material-ui/core/Stepper";
import StepButton from "@material-ui/core/StepButton";
import Step from "@material-ui/core/Step";
import ProjectManagement from "./ProjectManagement/ProjectManagement";
import RiskManagementAndContracting from "./RiskManagementAndContracting";
import ProjectGeneralPrinciples from "./ProjectGeneralPrinciples";
import RiskRate from "./RiskRate";
import Testing from "./Testing";
import ProjectCompletion from "./ProjectCompletion";
import ConstantDocumentation from "./ConstantDocumentation";
import MenuConstants from '../Const/ProjectDetailsConstants'
import ApiService from "../Service/ApiService";
const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
    },
    button: {
        marginRight: theme.spacing(1),
    },
    instructions: {
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1),
    },
}));

class ProjectDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeStep: 0,
            projectId: this.props.match.params.projectId,
            allMembers:[]
        }
        this.reloadMembers = this.reloadMembers.bind(this)
        this.getStep = this.getStep.bind(this)
        this.handleClick = this.handleClick.bind(this)
        this.getStepContent = this.getStepContent.bind(this)
    }

    reloadMembers =() =>{
        ApiService.findMyTeamMembers().then(res => {
            this.setState({allMembers: res.data})
        })
    }
    getStepContent = (step) => {
        switch (step) {
            case 0:
                return <ProjectManagement projectId={this.state.projectId} allMembers={this.state.allMembers}/>
            case 1:
                return <RiskManagementAndContracting projectId={this.state.projectId}/>
            case 2:
                return <ProjectGeneralPrinciples projectId={this.state.projectId}/>
            case 3:
                return <RiskRate projectId={this.state.projectId}/>
            case 4:
                return <Testing projectId={this.state.projectId}/>
            case 5:
                return <ProjectCompletion projectId={this.state.projectId}/>
            case 6:
                return <ConstantDocumentation projectId={this.state.projectId}/>
            default:
                return "Unknown step"
        }
    }
    getStep = (stepNum, stepTitle) => {
        return (<Step key={stepNum}>
            <StepButton onClick={() => this.handleClick(stepNum)}>
                <Typography variant="body1" component={"h5"}>
                    {stepTitle}
                </Typography>
            </StepButton>
        </Step>)
    }
    handleClick = (e) => {
        this.setState({activeStep: e})
    }

    render() {
        return (
            <div className={useStyles.root}>
                <Stepper alternativeLabel nonLinear activeStep={this.state.activeStep}>
                    {MenuConstants.map(step=>this.getStep(step.stepNum, step.title))}
                </Stepper>
                <div>
                    {this.getStepContent(this.state.activeStep)}
                </div>
            </div>
        )
    }
}

export default ProjectDetails;