import React, { useState, useEffect, useCallback } from 'react';
import Request from './Request';
import Grid from '@material-ui/core/Grid';
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import CircularProgress from '@material-ui/core/CircularProgress';
import SortButton from './SortButton';
import { Typography, Chip } from '@material-ui/core';
import Axios from 'axios';
import Pagination from '@material-ui/lab/Pagination';

const useStyles = makeStyles((theme) => ({
    cardGrid: {
        paddingBottom: theme.spacing(4),
        margin: 'auto',
    },
    loadingStyle: {
        display: 'block',
        margin: '10% auto',
    },
    tagStyle: {
        marginRight: '4px',
    }

}));


const RequestList = ({ category }) => {
    const classes = useStyles();

    const [totalPage, setTotalPage] = useState(0);

    const [page, setPage] = useState(1);

    const handleChangePage = (event, value) => {
        setPage(value);
    };

    const [data, setData] = useState([]);

    const [tags, setTags] = useState([]);

    const [loading, setLoading] = useState(true);

    const getAllRequests = useCallback(() => {
        if (category === "모든 요청") {
            Axios.get('/requests/' + page, {
                tags: tags
            })
                .then(res => {
                    let requests = res.data.requestList;
                    const tags = res.data.tags;
                    requests = requests.map((element, index) => {
                        element.tags = tags[index];
                    });
                    setData(requests);
                    setTotalPage(res.data.count);
                    setLoading(false);
                })

        }
        else {
            Axios.get('/requests/category/' + page, {
                tags: tags
            })
                .then(res => {
                    let requests = res.data.requestList;
                    const tags = res.data.tags;
                    requests = requests.map((element, index) => {
                        element.tags = tags[index];
                    });
                    setData(requests);
                    setTotalPage(res.data.count);
                    setLoading(false);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }, [category, page,tags])


    useEffect(() => {
        getAllRequests();
        return () => {
            setLoading(true);
            setTags([]);
        }
    }, [getAllRequests])


    const tagSort = tags.map((obj, index) => {
        return <Chip className={classes.tagStyle} key={index} label={obj} variant="default" size="small" onDelete={() => { onClickRemove(index) }} />
    })

    const onClickRemove = (index) => {
        const list = tags.filter((obj, x) => {
            return x !== index;
        })
        setTags(list)
    }


    if (loading) {
        return (
            <CircularProgress className={classes.loadingStyle} />
        )
    }

    const requestList = data.map((obj) => {


        return (
            <Grid key={obj.request_id} item xs={12} sm={12} md={6}>
                <Request tags={tags} setTags={setTags} data={obj} checked={loading}></Request>
            </Grid>
        )
    })


    return (
        <Container className={classes.cardGrid} maxWidth="md">
            <Typography variant="h5" style={{ display: 'inline-block', margin: '0px' }} gutterBottom>{category}</Typography>
            <br />
            <br />
            {tagSort}
            <SortButton category={category} />
            {totalPage
                ?
                <>
                    <Grid container spacing={3}>
                        {requestList}
                    </Grid>
                    <Pagination className={classes.pagination} count={totalPage} page={page} onChange={handleChangePage} defaultPage={1} />
                </>
                :
                <Typography variant="h5" style={{ marginTop: '40px' }}>진행중인 요청이 없습니다.</Typography>
            }
        </Container>
    )


}


export default RequestList;