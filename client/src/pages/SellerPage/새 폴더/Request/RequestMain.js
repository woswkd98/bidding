import React,{useState} from 'react';
import Grid from '@material-ui/core/Grid';
import { Container, makeStyles } from '@material-ui/core';
import RequestList from './RequestList';
import CategoryMenu from './CategoryMenu';

const useStyle = makeStyles((theme)=>({
    heroContent: {
        padding: theme.spacing(8, 0, 6),
        color : 'rgb(104,104,106)',
    },
}))


function RequestMain() {

    const classes = useStyle();
    const [category,setCategory] = useState('모든 요청');

    return (
        <Container className={classes.heroContent}>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={3}>
                    <CategoryMenu setCategory={setCategory}/>
                </Grid>
                <Grid item xs={12} sm={9}>
                    <RequestList category={category}/>
                </Grid>
            </Grid>
        </Container>
    )
}


export default RequestMain;