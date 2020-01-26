import React, {Component} from 'react';
import Button from '@material-ui/core/Button/index';
import Dialog from '@material-ui/core/Dialog/index';
import DialogActions from '@material-ui/core/DialogActions/index';
import DialogContent from '@material-ui/core/DialogContent/index';
import DialogTitle from '@material-ui/core/DialogTitle/index';
import menus from "../../Const/ProjectDetailsConstants";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import {KeyboardDatePicker, KeyboardTimePicker, MuiPickersUtilsProvider} from '@material-ui/pickers';
import Grid from "@material-ui/core/Grid";
import DateFnsUtils from "@date-io/date-fns";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import ListItem from "@material-ui/core/ListItem";
import List from "@material-ui/core/List";
import {Table, TableCell, TableHead, Typography} from "@material-ui/core";
import TableRow from "@material-ui/core/TableRow";
import TableBody from "@material-ui/core/TableBody";

class TaskDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
            tasksOpen: false,
            currentTask: {
                tasksTitleId: '',
                concreteTasks: [],
                selectedTask: '',
                deadLineDate: new Date(),
            }
        }

    }


    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };
    handleTasksClose = () => {
        this.setState({tasksOpen: false})
    }

    handleTasksOpen = () => {
        this.setState({tasksOpen: true})
    }

    handleComplete =()=> {
        this.props.addTask(this.state.currentTask, this.props.user)
        this.handleClose()
    }

    onMenuChange =(e)=> {
        this.setState({
            currentTask: {
                tasksTitleId: e.target.value,
                concreteTasks: menus[e.target.value].panelItems,
                deadLineDate: this.state.currentTask.deadLineDate,
                selectedTask: ''
            }
        })
    }

    onPanelItemChange =(e)=> {
        this.setState({
            currentTask: {
                tasksTitleId: this.state.currentTask.tasksTitleId,
                concreteTasks: this.state.currentTask.concreteTasks,
                deadLineDate: this.state.currentTask.deadLineDate,
                selectedTask: e.target.value
            }
        })
    }

    handleDateChange = date => {
        this.setState({
            currentTask: {
                tasksTitleId: this.state.currentTask.tasksTitleId,
                concreteTasks: this.state.currentTask.concreteTasks,
                deadLineDate: date,
                selectedTask: this.state.currentTask.selectedTask
            }
        })
    }

    findLabel = value => {
        let flatPanelItems = []
        menus.forEach(item => {
            item.panelItems.forEach(panelItem => flatPanelItems.push(panelItem))
        })
        return flatPanelItems.find(item => item.value === value).label
    }

    render() {
        return (
            <div>
                <ButtonGroup variant="contained" color="primary" aria-label="contained primary button group">
                    <Button onClick={this.handleTasksOpen}>მიმდინარე დავალებები</Button>
                    <Button onClick={this.handleClickOpen}>ახალი დავალება</Button>
                </ButtonGroup>
                <Dialog fullWidth open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">აირჩიეთ დავალებები</DialogTitle>
                    <DialogContent>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={12}>
                                <FormControl  fullWidth>
                                    <InputLabel variant={"standard"} id="task-select-label">დავალებები</InputLabel>
                                    <Select
                                        variant={"standard"}
                                        labelId="task-select-label"
                                        id="task-simple-select"
                                        onChange={this.onMenuChange}
                                        name={'tasksTitleId'}
                                        value={this.state.currentTask.tasksTitleId}
                                    >
                                        {menus.filter(menuItem => menuItem.stepNum > 1).map(menuItem => {
                                            return (
                                                <MenuItem key={menuItem.stepNum} value={menuItem.stepNum}>
                                                    {menuItem.title}
                                                </MenuItem>
                                            )
                                        })}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={12}>

                                <FormControl fullWidth>
                                    <InputLabel variant={"standard"} id="exact-task-select-label">კონკრეტული
                                        დავალება</InputLabel>
                                    <Select
                                        variant={"standard"}
                                        labelId="exact-task-select-label"
                                        id="exact-task-simple-select"
                                        onChange={this.onPanelItemChange}
                                        name={'selectedTask'}
                                        value={this.state.currentTask.selectedTask}
                                    >
                                        {this.state.currentTask.concreteTasks.map(panelItem => {
                                            return (
                                                <MenuItem key={panelItem.id} value={panelItem.value}>
                                                    {panelItem.label}
                                                </MenuItem>
                                            )
                                        })}
                                    </Select>
                                </FormControl>
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <FormControl fullWidth>

                                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                        <KeyboardDatePicker
                                            required={true}
                                            disableToolbar
                                            format="dd/MM/yyyy"
                                            margin="normal"
                                            name={'deadlineDate'}
                                            label={"დედლაინის დღე"}
                                            value={this.state.currentTask.deadLineDate}
                                            onChange={this.handleDateChange}
                                            KeyboardButtonProps={{
                                                'aria-label': 'change date',
                                            }}
                                            inputVariant="standard"
                                        />
                                    </MuiPickersUtilsProvider>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <FormControl fullWidth>

                                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                                        <KeyboardTimePicker
                                            required={true}
                                            inputVariant="standard"
                                            margin="normal"
                                            id="time-picker"
                                            disableToolbar
                                            label="დედლაინის დრო"
                                            value={this.state.currentTask.deadLineDate}
                                            onChange={this.handleDateChange}
                                            KeyboardButtonProps={{
                                                'aria-label': 'change time',
                                            }}
                                        />
                                    </MuiPickersUtilsProvider>
                                </FormControl>
                            </Grid>

                        </Grid>

                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            გაუქმება
                        </Button>
                        <Button onClick={this.handleComplete} color="primary">
                            დასრულება
                        </Button>
                    </DialogActions>
                </Dialog>
                <Dialog fullWidth open={this.state.tasksOpen} onClose={this.handleTasksClose}>
                    <DialogTitle id="form-dialog-title2">მიმდინარე დავალებები</DialogTitle>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><Typography color={"textPrimary"} >დავალება</Typography></TableCell>
                                <TableCell><Typography color={"textPrimary"}>დედლაინი</Typography></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.props.tasks.map((task, index) => {
                                    return (
                                        <TableRow key={index}>
                                            <TableCell><Typography color={"textPrimary"}>{this.findLabel(task.task)}</Typography> </TableCell>
                                            <TableCell><Typography color={"textPrimary"}>{new Date(task.deadline).toUTCString()}</Typography></TableCell>
                                        </TableRow>
                                    )
                                }
                            )}
                        </TableBody>
                    </Table>
                </Dialog>
            </div>
        );
    }

}

export default TaskDialog;