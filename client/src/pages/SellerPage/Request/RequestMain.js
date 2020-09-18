import React,{useState} from 'react';
import Grid from '@material-ui/core/Grid';
import { Container, makeStyles,Typography } from '@material-ui/core';
import RequestList from './RequestList';
import CategoryMenu from './CategoryMenu';
import SortButton from './SortButton';

const useStyle = makeStyles((theme)=>({
    heroContent: {
        padding: theme.spacing(8, 0, 6),
        color : 'rgb(104,104,106)',
    },
    container: {
        paddingBottom: theme.spacing(4),
        margin: 'auto',
    },
    category : {
        display: 'inline-block',
        margin: '0px'
    }
}))


function RequestMain() {

    const classes = useStyle();
    const [category,setCategory] = useState('모든 요청');
    const [sortValue,setSortValue] = useState('uploadAt');

    return (
        <Container className={classes.heroContent}>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={3}>
                    <CategoryMenu setCategory={setCategory}/>
                </Grid>
                <Grid item xs={12} sm={9}>
                    <Container className={classes.container} maxWidth="md">
                        <Typography variant="h5" className={classes.category} gutterBottom>{category}</Typography>
                        <br />
                        <br />
                        <SortButton sortValue={sortValue} setSortValue={setSortValue} />
                        <RequestList category={category} sortValue={sortValue} />
                    </Container>
                </Grid>
            </Grid>
        </Container>
    )
}


export default RequestMain;