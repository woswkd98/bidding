import React, { useState, useEffect, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { CircularProgress, Divider, Typography } from '@material-ui/core';
import MyRequests from './MyRequests';
import RequestHistory from './RequestHistory';
import Axios from 'axios';



const Main = () => {

    const user_id = useSelector(state => state.userAction.user_id);

    const loadingStyle = {
        display: 'block',
        margin: '18% auto',
    }
    console.log(user_id);
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    
    const getMyRequests = useCallback(() => {
        
        Axios.get('/requests/userId/' + localStorage.getItem("user_id"))
            .then(res => {
                console.log("111111111");
                console.log(res.data);
                const requests = res.data.requestList;
                const tags = res.data.tags;
                for(let i =0; i < requests.length; ++i) {
                    requests[i].tags = tags[i];
                }
                console.log(requests);

                setData(requests);
                setLoading(false);
            })
            .catch(err => {
                console.log(err);
            })
    },[user_id])

    useEffect(() => {
        getMyRequests();
        return () => {
            setLoading(true);
        }
    }, [getMyRequests])

    if (loading) {
        return (
            <CircularProgress style={loadingStyle} />
        )
    }

    return (
        <div style={{ padding: '10px' }}>
            <Typography variant="h5" gutterBottom>나의 요청</Typography>
            <MyRequests data={data} />
            <Divider style={{ marginTop: '30px' }} />
            <br />
            <Typography variant="h5" gutterBottom>요청 내역</Typography>
            <RequestHistory data={data} />
        </div>
    )

}


export default Main;