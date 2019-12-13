import React, {Component} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import Typography from '@material-ui/core/Typography';
import ApiService from "../../Service/ApiService";
import {toast} from "react-toastify";
import {Button} from "@material-ui/core";
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
        console.log(props)
        this.state = {
            projectMembers: [],
        }

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
                    console.log(member)
                    return (
                        <div key={member.userId}>
                            <ListItem alignItems="flex-start">
                                <ListItemAvatar>
                                    <Avatar>{member.firstName[0].toUpperCase()}</Avatar>
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
                                            {" — I'll be in your neighborhood doing errands this…"}
                                        </React.Fragment>
                                    }
                                />
                            </ListItem>
                            <TaskDialog user={member}/>
                            <Divider variant="inset" component="li"/>
                        </div>
                    )
                })}
            </List>
        )
    }

}

export default TaskAssignment