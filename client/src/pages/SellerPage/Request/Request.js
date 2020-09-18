import React from 'react';
import { Link } from 'react-router-dom';
import Counter from '../../../components/Counter'
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import {  Paper } from '@material-ui/core';
import Grow from '@material-ui/core/Grow';

const useStyles = makeStyles((theme) => ({
    card: {
        height: '100%',
        display: 'flex',
        transition: 'all 0.1s',
        '&:hover': {
            backgroundColor: '#F2F3F4',
        }
    },
    cardMedia: {
        paddingTop: '56.25%', // 16:9
    },
    cardContent: {
        flexGrow: 1,
    },
    gridStyle: {
        display: 'inline-block',
        margin: 'auto',
    },
    titleStyle: {
        textDecoration: 'none',
        color: 'black'
    },
    tagStyle: {
        display: 'inline-block',
        backgroundColor: 'lightgray',
        borderRadius: '4px',
        padding: '3px 5px',
        marginTop: '5px',
        marginRight: '4px',
        '&:hover': {
            cursor: 'pointer'
        }
    },
    detailStyle: {
        overflow: 'hidden',
        width: '400px',
        maxHeight: '3.1em',
        textOverflow : 'ellipsis',
    },
    tagDivStyle : {
        display : 'block',
        textOverflow : 'ellipsis',
        overflow: 'hidden',
        whiteSpace : 'nowrap',
        width: '400px',
        maxHeight: '1.7em',
    }
}));

function Request({ data, checked, tags, setTags }) {

    const classes = useStyles();

    const addTagList = (obj) => {
        if(tags.includes(obj)) return;
        setTags([...tags, obj])

    }

    const showTagList = data.tags.map((obj, index) => {
        return <small onClick={() => { addTagList(obj) }} className={classes.tagStyle} key={index}>{obj}</small>
    })

    return (
        <Grow in={!checked} timeout={300}>
            <Paper elevation={3}>
                <Card className={classes.card}>
                    <CardContent className={classes.cardContent}>
                        <Link className={classes.titleStyle} to={{ pathname: `/seller/request/${data._id}`, state: { data: data } }}>
                            <small>{new Date( data.upload_at).toLocaleString()}</small><br />
                            <Typography variant="h5" gutterBottom>
                                {data.user_name} 님의 {data.category} 요청
                                    </Typography>
                            <Typography className={classes.detailStyle} variant="subtitle2" paragraph>
                                {data.detail}
                            </Typography>
                        </Link>
                        <div className={classes.tagDivStyle}>
                            {showTagList}
                        </div>
                        <br/>
                        <Counter data={data}></Counter>
                    </CardContent>
                </Card>
            </Paper>
        </Grow>
    )
}

export default Request;

