import React, {Component} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import TaskDialog from "./TaskDialog";
import ProjectService from "../../Service/ProjectService";

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        maxWidth: "1000",
        backgroundColor: theme.palette.background.paper,
    },
    inline: {
        display: 'inline',
    },
}));

const columns = {
    columns: [
        { title: 'სახელი', field: 'firstName' },
        { title: 'გვარი', field: 'lastName' },
        { title: 'ელ-ფოსტა', field: 'email' },
        { title: 'ალიასი', field: 'alias' },
        { title: 'დავალება', field: 'task' },
        { title: 'დედლაინი', field: 'deadline', type: 'date' },
    ]
}

class TaskAssignment extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectMembers: [],
            tasks: [],
            currentTasks: []
        }

    }

    addTask = (task, user) => {
        ProjectService.assignTask(task, user, this.props.projectId)
        let tasks = [...this.state.tasks, {task: task, user: user}]
        this.setState({tasks: tasks})
    }

    findTasks =(userId) => {
        return this.state.currentTasks.filter(task => task.assignedUser.userId === userId)
    }

    componentDidMount() {
        this.props.reloadMembers().then(res => {
                this.setState({projectMembers: res.data})
            }
        ).then(()=>console.log('hm', this.state.projectMembers))
        this.props.reloadTasks(this.props.projectId).then(res => {
                this.setState({currentTasks: res.data})
            }
        ).then(()=>console.log(this.state.currentTasks))
    }

    render() {
        return (
            <List className={useStyles.root}>
                {this.state.projectMembers.map((member, index) => {
                    return (
                        <div key={member.userId}>
                            <ListItem alignItems="flex-start">
                                <ListItemAvatar>
                                    <Avatar>{member.firstName[0]}</Avatar>
                                </ListItemAvatar>

                                <ListItemText
                                    primary={member.firstName + " " + member.lastName}
                                    secondary={
                                        <React.Fragment>
                                            <Typography
                                                component="span"
                                                variant="body2"
                                                className={useStyles.inline}
                                                color="primary"
                                            >
                                                {member.email}
                                            </Typography>
                                        </React.Fragment>
                                    }
                                />
                                <TaskDialog user={member} addTask={this.addTask} tasks={this.findTasks(member.userId)}/>
                            </ListItem>
                            <Divider variant="inset" component="li"/>
                        </div>
                    )
                })}
            </List>
        )
    }

}

export default TaskAssignment