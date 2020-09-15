import React, { useEffect, useState,useCallback } from 'react'
import { useHistory } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import CircularProgress from '@material-ui/core/CircularProgress';
import ReceiveList from './ReceiveList';
import Progress from '../../TradeState/Progress';
import Complete from '../../TradeState/Complete';
import Canceled from '../../TradeState/Canceled';
import Axios from 'axios';

const useStyles = makeStyles((theme) => ({
    card: {
        height: '100%',
        display: 'flex',
        textDecoration: 'none',
        maxWidth: '430px',
        maxHeight: '135px',
    },
    cardHead: {
        fontFamily: 'Montserrat, sans-serif',
        fontWeight: 'bold',
        fontSize: '20px',
    },
    cardMedia: {
        paddingTop: '56.25%', // 16:9
    },
    cardContent: {
        flexGrow: 1,
    },
    tagStyle: {
        display: 'inline-block',
        backgroundColor: '#F0F0F0',
        borderRadius: '3px',
        padding: '2px 5px',
        margin: '5px',
        fontWeight: 'bold',
    },
    loadingStyle: {
        display: 'block',
        margin: '18% auto',
    },
    large: {
        width: '100px',
        height: '100px',
    },
}));

const RequestDetail = (props) => {

    const classes = useStyles();

    const history = useHistory();

    const [requestData] = useState(props.location.state);

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);

    const getBidsInRequest = useCallback(() => {
        console.log("get");
        Axios.get('/biddings/request/' + requestData.request_id)
            .then(res => {
                setData(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.log(err);
            })
    },[requestData])
   
    const choiceOneBid = (bid_id) => {
        Axios.post('/biddings/chioce', {
            biddingId: bid_id,
            requestId: requestData.request_id
        })
            .then(() => {
                alert('선택 완료');
                history.replace('/user/mypage');
            })
            .catch(err => {
                console.log(err);
            })
    }

    useEffect(() => {
        getBidsInRequest();
        return () => {
            setLoading(true);
        }
    }, [getBidsInRequest])

    if (loading) {
        return (
            <CircularProgress className={classes.loadingStyle} />
        )
    }


    switch (requestData.state) {
        case '거래 진행중':
            const data1 = data.filter((obj) => {
                return obj.state === '거래 진행중';
            })[0]
            return <Progress data={data1} requestData={requestData} />

        case '거래 완료':
            const data2 = data.filter((obj) => {
                return obj.state === '거래 완료';
            })[0]
            return <Complete data={data2} requestData={requestData} />

        case '취소된 거래':
            return <Canceled requestData={requestData} />

        default:
            const data3 = data.filter((obj) => {
                return obj.state !== '입찰 취소';
            })
            return <ReceiveList onClickChoice={choiceOneBid} bidData={data3} requestData={requestData} />
    }


}

export default RequestDetail;