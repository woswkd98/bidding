import React, { useState } from 'react'
import Collapse from '@material-ui/core/Collapse';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ExpandLessIcon from '@material-ui/icons/ExpandLess';
import { Container, Grid, CardHeader, Card, CardContent, Typography, Button } from '@material-ui/core';
import Counter from '../../../../components/Counter';
import { Link } from 'react-router-dom';

const ChoesnList = ({ data, handleChatOpen }) => {

    const [checked, setChecked] = useState([]);

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

    const ChosenList = data.filter((obj) => {
        return obj.state === '거래 진행중' || obj.state === '거래 대기중';
    })


    const MyChosenList = ChosenList.map((obj) => {

        return (
            <Grid key={obj.request._id} style={{ margin: 'auto' }} item xs={12} sm={6} md={4}>
                <Collapse in={checked.includes(obj.request._id)} collapsedHeight={88}>
                    <Card elevation={3}>
                        <CardHeader onClick={() => { onClickChecked(obj.request._id) }} action={checked.includes(obj.request._id) ? <ExpandLessIcon /> : <ExpandMoreIcon />} style={{ textAlign: 'center', paddingBottom: 0, cursor: 'pointer' }} title={`${obj.request.user_name}님의 요청서`} subheader={obj.request.requestedAt} />
                        <CardContent>
                            <Typography align="center" variant="body1" paragraph>
                                {obj.state === '거래 진행중'
                                    ?
                                    `거래 진행중`
                                    :
                                    <>
                                        낙찰 대기중<br />
                                        <Counter data={obj.request} />
                                    </>
                                }
                            </Typography>
                            <Grid container spacing={3}>
                                <Grid item xs={12} md={6}>
                                    <Link style={{textDecoration:'none'}} to={{ pathname: '/seller/detail', state: obj }}>
                                    <Button style={{ width: '100%' }} variant="outlined">
                                            자세히
                                    </Button>
                                    </Link>
                                </Grid>
                                <Grid item xs={12} md={6}>
                                    <Button onClick={() => { handleChatOpen(obj.request._id) }} style={{ width: '100%' }} variant="outlined">
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

export default ChoesnList
