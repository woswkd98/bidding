import React from 'react';
import Stepper from './EnrollStepper';
import { makeStyles } from '@material-ui/core/styles';
import { Container } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
    heroContent: {
        padding: theme.spacing(8, 0, 6),
        color : 'rgb(104,104,106)',
    },
}));

function Enroll(){

    const classes = useStyles();

    return(
        <Container className={classes.heroContent}>
            <Stepper/>
        </Container>
    )
}

export default Enroll;
