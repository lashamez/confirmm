import React from 'react'
import ListProjectComponent from "./ListProjectComponent";
import ListProjectUsers from "./ListProjectUsers";
import {Grid} from "@material-ui/core";

export default function Dashboard(props) {
    return (
        <Grid container spacing={2} >
            <Grid item xs={12} sm={6}  >
                <ListProjectComponent />
            </Grid>
            <Grid item xs={12} sm={6}>
                <ListProjectUsers/>
            </Grid>
        </Grid>
    )
}
