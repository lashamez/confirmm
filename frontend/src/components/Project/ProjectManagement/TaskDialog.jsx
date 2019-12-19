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

class TaskDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
            currentTask: {
                currentMenuItem: '',
                currentPanelItems: [],
                selectedPanelItem: '',
                deadLineDate: new Date(),
            }
        }
        this.handleClickOpen = this.handleClickOpen.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.handleComplete = this.handleComplete.bind(this)
        this.onMenuChange = this.onMenuChange.bind(this)
        this.onPanelItemChange = this.onPanelItemChange.bind(this)
        this.handleDateChange = this.handleDateChange.bind(this)
    }


    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleComplete() {
        this.handleClose()

    }

    onMenuChange(e) {
        console.log(menus[e.target.value].panelItems)
        this.setState({
            currentTask: {
                currentMenuItem: e.target.value,
                currentPanelItems: menus[e.target.value].panelItems,
                deadLineDate: this.state.currentTask.deadLineDate,
                selectedPanelItem: ''
            }
        })
    }

    onPanelItemChange(e) {
        this.setState({
            currentTask: {
                currentMenuItem: this.state.currentTask.currentMenuItem,
                currentPanelItems: this.state.currentTask.currentPanelItems,
                deadLineDate: this.state.currentTask.deadLineDate,
                selectedPanelItem: e.target.value
            }
        })
    }

    handleDateChange = date => {
        this.setState({
            currentTask: {
                currentMenuItem: this.state.currentTask.currentMenuItem,
                currentPanelItems: this.state.currentTask.currentPanelItems,
                deadLineDate: date,
                selectedPanelItem: this.state.currentTask.selectedPanelItem
            }
        })
    }

    render() {
        return (
            <div>
                <Button variant="outlined" color="inherit" onClick={this.handleClickOpen}>
                    დავალება
                </Button>
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
                                        name={'currentMenuItem'}
                                        value={this.state.currentTask.currentMenuItem}
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
                                        name={'selectedPanelItem'}
                                        value={this.state.currentTask.selectedPanelItem}
                                    >
                                        {this.state.currentTask.currentPanelItems.map(panelItem => {
                                            console.log(panelItem)
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
            </div>
        );
    }

}

export default TaskDialog;