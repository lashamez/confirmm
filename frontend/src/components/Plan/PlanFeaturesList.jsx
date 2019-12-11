import React from 'react';
import ListItemText from "@material-ui/core/ListItemText";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";
import List from "@material-ui/core/List";
import ClearIcon from '@material-ui/icons/Clear';
import CheckIcon from '@material-ui/icons/Check';
export default function PlanFeaturesList(props) {
    return (
        <List component={"nav"}>
            <ListItem >
                <ListItemText primary={"სასწავლო პროგამა"}/>
                {props.plan.study && <CheckIcon fontSize={"large"} style={{color:"#4BB543"}}/>}
                {!props.plan.study && <ClearIcon fontSize={"large"} style={{color:"#D8000C"}}/>}
            </ListItem>
            <Divider />
            <ListItem >
                <ListItemText primary={"რეპორტინგი"}/>
                {props.plan.reporting && <CheckIcon fontSize={"large"} style={{color:"#4BB543"}}/>}
                {!props.plan.reporting && <ClearIcon fontSize={"large"} style={{color:"#D8000C"}}/>}
            </ListItem>
            <Divider variant={"fullWidth"}  />
            <ListItem >
                <ListItemText primary={"ანალიტიკა"}/>
                {props.plan.analytics && <CheckIcon fontSize={"large"} style={{color:"#4BB543"}}/>}
                {!props.plan.analytics && <ClearIcon fontSize={"large"} style={{color:"#D8000C"}}/>}
            </ListItem>
            <Divider/>
        </List>

    );
}