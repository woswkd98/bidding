import React from 'react'
import RequestCard from '../../../components/RequestCard'
import { Container, Grid, Typography, makeStyles } from '@material-ui/core'
import CancelIcon from '@material-ui/icons/Cancel';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        color: 'rgb(104,104,106)'
    },
    gridStyle: {
        margin: '4% auto',
        width: "80%",
    },
    icon: {
        display: 'block',
        fontSize: 50,
        marginTop : '50px',
        margin: 'auto',
    }
}));

const Failed = ({ data }) => {

    const classes = useStyles();

    return (
        <Container className={classes.root}>
            <Grid className={classes.gridStyle} container spacing={7}>
                <Grid item xs={12} md={6}>
                    <RequestCard obj={data.request} />
                </Grid>
                <Grid item xs={12} md={6}>
                    <br />
                    <CancelIcon className={classes.icon} color="error" />
                    <br />
                    <Typography align="center" variant="h5" paragraph>
                        유찰되었습니다.
                    </Typography>
                </Grid>
            </Grid>
        </Container>
    )
}

export default Failed;