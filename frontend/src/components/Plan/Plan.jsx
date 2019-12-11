import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import {red} from '@material-ui/core/colors';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import {plans} from "../Const/PlansConstants";
import Grid from "@material-ui/core/Grid";
import {Paper} from "@material-ui/core";
import BuyPackage from "../Package/BuyPackage";
import Divider from "@material-ui/core/Divider";
import PlanFeaturesList from "./PlanFeaturesList";
const useStyles = makeStyles(theme => ({
    card: {
        maxWidth: 345,
    },
    media: {
        height: 0,
        paddingTop: '56.25%', // 16:9
    },
    expand: {
        transform: 'rotate(0deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    },
    expandOpen: {
        transform: 'rotate(180deg)',
    },
    avatar: {
        backgroundColor: red[500],
    },
}));

export default function RecipeReviewCard() {
    const classes = useStyles();

    return (
        <Paper color={"primary"}>
            <Grid container spacing={1} alignContent={"center"} alignItems={"center"} style={{margin:"0 auto"}}>
                {plans.map(plan => {
                    return (
                        <Grid key={plan.id} xs={12} sm={4} item={true} style={{marginBottom: 30}}>
                            <Card className={classes.card} style={{margin:"0 auto"}}>
                                <CardHeader
                                    avatar={
                                        <Avatar aria-label="recipe" className={classes.avatar}>
                                            {plan.label[0]}
                                        </Avatar>
                                    }
                                    action={
                                        <IconButton aria-label="settings">
                                            <MoreVertIcon/>
                                        </IconButton>
                                    }
                                    title={plan.label}
                                />
                                <Divider />
                                <CardMedia
                                    className={classes.media}
                                    image={plan.image}
                                    title={plan.label}
                                />
                                <CardContent>
                                    <Typography variant="body1" color="textPrimary" component="p">
                                        {plan.registrationFee} ლ, {plan.users} მომხმარებელი
                                    </Typography>
                                    <PlanFeaturesList plan={plan}/>
                                </CardContent>
                                <CardActions disableSpacing style={{alignItems: "center"}}>
                                    <BuyPackage id={plan.id}/>
                                </CardActions>
                            </Card>
                        </Grid>
                    )
                })
                }
            </Grid>

        </Paper>

    );
}