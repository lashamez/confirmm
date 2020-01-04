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

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
    inline: {
        display: 'inline',
    },
}));

class TaskAssignment extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectMembers: [],
            tasks:[]
        }

    }
    addTask =(task, user)=>{
        let tasks = [...this.state.tasks, {task: task, user: user}]
        this.setState({tasks: tasks})
        console.log(tasks)
    }

    componentDidMount() {
        this.props.reloadMembers().then(res => {
                this.setState({projectMembers: res.data})
            }
        )
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
                            </ListItem>
                            <TaskDialog user={member} addTask={this.addTask}/>
                            <Divider variant="inset" component="li"/>
                        </div>
                    )
                })}
            </List>
        )
    }

}

export default TaskAssignment