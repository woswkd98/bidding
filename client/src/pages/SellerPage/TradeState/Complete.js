import React from 'react'
import RequestCard from '../../../components/RequestCard'
import { Container, Grid, Typography,  makeStyles } from '@material-ui/core'
import CheckCircleIcon from '@material-ui/icons/CheckCircle';


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
        marginTop: '50px',
        margin: 'auto',
        color: "#4caf50",
    }
}));

const Complete = ({ data }) => {
    console.log(data);
    const classes = useStyles();
    return (
        <Container className={classes.root}>
            <Grid className={classes.gridStyle} container spacing={9}>
                <Grid item xs={12} md={6}>
                    <RequestCard obj={data} />
                </Grid>
                <Grid item xs={12} md={6}>
                    <CheckCircleIcon className={classes.icon} />
                    <br />
                    <Typography align="center" variant="h4" paragraph>
                        거래 완료!
                    </Typography>
                    <br />
                </Grid>
            </Grid>
        </Container>
    )
}

export default Complete
