import React, { useState, useEffect,useCallback } from 'react';
import { useSelector } from 'react-redux';
import { CircularProgress, Typography, Container } from '@material-ui/core';
import Chat from '../../../../components/chat';
import BidHistory from './BidHistory';
import ChosenList from './ChoesnList';
import Axios from 'axios';



const BidList = () => {

    const user_id = useSelector(state => state.userAction.user_id);

    const loadingStyle = {
        display: 'block',
        margin: '18% auto',
    }


    const [chatOpen, setChatOpen] = useState(false);

    const [request_id, setRequest_id] = useState('');

    const [data, setData] = useState([]);

    const [loading, setLoading] = useState(true);

    const handleChatClose = () => {
        setChatOpen(false);
    }

    const handleChatOpen = (request_id) => {
        setRequest_id(request_id)
        setChatOpen(true);
    }

    const getMyBids = useCallback(() => {
        Axios.get('/biddings/seller/' + localStorage.getItem("is_seller"))
            .then(res => {
                console.log("    const getMyBids = useCallback(() => {");
                console.log(res.data);
                setData(res.data);
                setLoading(false);
            }).then()
            .catch(err => {
                console.log(err);
            })
    },[user_id])

 

    useEffect(()=>{
        getMyBids();


        return () => {
            setLoading(true);
        }
    },[getMyBids])

    if (loading) {
        return (
            <CircularProgress style={loadingStyle} />
        )
    }
    console.log(data);
    return (
        <>
        
            <Container>
                <Typography variant="h5" gutterBottom>진행중인 거래</Typography>
                <br />
                <ChosenList handleChatOpen={handleChatOpen} data={data} />
            </Container>
            <br /><br />
            <Container>
                <Typography variant="h5" gutterBottom>거래내역</Typography>
                <br />
                <BidHistory data={data} />
            </Container>
            <Chat open={chatOpen} onClose={handleChatClose} request={request_id} seller={user_id} />
        </>

    )


}

export default BidList;