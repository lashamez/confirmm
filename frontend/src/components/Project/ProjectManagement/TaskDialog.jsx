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

class TaskDialog extends Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false,
            currentTask: {
                currentMenuItem: '',
                currentPanelItems: [],
                selectedPanelItem: null,
                deadLine: null
            }
        }
        this.handleClickOpen = this.handleClickOpen.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.tryLogin = this.tryLogin.bind(this)
        this.onMenuChange = this.onMenuChange.bind(this)

    }


    handleClickOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    tryLogin() {
        //todo
    }

    onMenuChange(e) {
        this.setState({
            currentTask: {
                currentMenuItem: e.target.value,
                currentPanelItems: menus[e.target.value].panelItems,
                deadLine: this.state.currentTask.deadLine,
                selectedPanelItem: null
            }
        })
    }

    render() {
        return (
            <div>
                <Button variant="outlined" color="inherit" onClick={this.handleClickOpen}>
                    დავალება
                </Button>
                <Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">აირჩიეთ დავალებები</DialogTitle>
                    <DialogContent>
                        <FormControl fullWidth>
                            <InputLabel id="task-select-label">დავალებები</InputLabel>
                            <Select
                                variant={"outlined"}
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

                        <FormControl fullWidth>
                            <InputLabel id="exact-task-select-label">კონკრეტული დავალება</InputLabel>
                            <Select
                                variant={"outlined"}
                                labelId="exact-task-select-label"
                                id="exact-task-simple-select"
                                onChange={this.onPanelItemChange}
                                name={'currentMenuItem'}
                                value={this.state.currentTask.selectedPanelItem}
                            >
                                {menus[this.state.currentTask.currentMenuItem].panelItems.map(panelItem => {
                                    return (
                                        <MenuItem key={panelItem.stepNum} value={menuItem.stepNum}>
                                            {menuItem.title}
                                        </MenuItem>
                                    )
                                })}
                            </Select>
                        </FormControl>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary">
                            გაუქმება
                        </Button>
                        <Button onClick={this.tryLogin} color="primary">
                            დასრულება
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }

}

export default TaskDialog;