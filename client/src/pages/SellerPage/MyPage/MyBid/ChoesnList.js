import React, { useState,useEffect} from 'react'
import Collapse from '@material-ui/core/Collapse';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ExpandLessIcon from '@material-ui/icons/ExpandLess';
import { Container, Grid, CardHeader, Card, CardContent, Typography, Button } from '@material-ui/core';
import Counter from '../../../../components/Counter';
import { Link, useHistory } from 'react-router-dom';
import Axios from 'axios'
const ChoesnList = ({ data, handleChatOpen }) => {

    const [checked, setChecked] = useState([]);
    const [requestObj, setRequestObj] = useState(null);
    const history = useHistory();

    useEffect(() => {
        console.log(data)
    }, [])

    const onClickChecked = (id) => {
        if (checked.includes(id)) {
            setChecked(
                checked.filter((obj) => {
                    return obj !== id
                })
            )
        } else {
            setChecked([
                ...checked,
                id
            ])
        }
    }
    console.log(data);
    const ChosenList = data.filter((obj) => {
        console.log(obj);
        return obj.bid_state === '거래 진행중' || obj.bid_state === '거래 대기중';
    })
    
    const getRequestBySellerId =  (obj) => {

        return  Axios.get("/requests/requestId/" + obj.request_id).then(res => {
            let temp2;
            console.log(res.data);
            res.data.requestList.tags = res.data.tags;
            res.data.requestList.price = obj.price;
 
 
            res.data.tags = null;
            history.push({pathname: '/seller/detail', state : res.data.requestList})
            return temp2;
    });
        
       
    }
    console.log("ChosenList");
    console.log(ChosenList);

    const MyChosenList = ChosenList.map((obj) => {
        console.log("MyChosenList = ChosenList.map((obj) => {")
        console.log(obj);
        return (
            <Grid key={obj.request_id} style={{ margin: 'auto' }} item xs={12} sm={6} md={4}>
                <Collapse in={checked.includes(obj.request_id)} collapsedHeight={88}>
                    <Card elevation={3}>
                        <CardHeader onClick={() => { onClickChecked(obj.request_id) }} action={checked.includes(obj.user_id) ? <ExpandLessIcon /> : <ExpandMoreIcon />} style={{ textAlign: 'center', paddingBottom: 0, cursor: 'pointer' }} title={`${obj.user_name}님의 요청서`} subheader={new Date(obj.upload_at).toLocaleString()} />
                        <CardContent>
                            <Typography align="center" variant="body1" paragraph>
                                {obj.state === '거래 진행중'
                                    ?
                                    `거래 진행중`
                                    :
                                    <>
                                        낙찰 대기중<br />
                                        <Counter data={obj} />
                                    </>
                           
                                }
                            </Typography>
                            <Grid container spacing={3}>
                                <Grid item xs={12} md={6}>
                                    
                                    <Button  style={{ width: '100%' }} variant="outlined" onClick = {() => {
                                        getRequestBySellerId(obj);
                             
                                    }} >
                                            자세히
                                    </Button>
                                </Grid>
                                <Grid item xs={12} md={6}>
                                    <Button onClick={() => { handleChatOpen(obj.request_id) }} style={{ width: '100%' }} variant="outlined">
                                        1:1 채팅
                                    </Button>
                                </Grid>
                            </Grid>
                        </CardContent>
                    </Card>
                </Collapse>
            </Grid>
        )
    })

    return (
        <Container>
            {ChosenList.length === 0
                ?
                <Typography variant="h5" gutterBottom style={{ textAlign: 'center' }}>현재 진행중인 거래가 없습니다.</Typography>
                :
                <Grid container spacing={3}>
                    {MyChosenList}
                </Grid>
            }
        </Container>
    )
}

export default ChoesnList;
