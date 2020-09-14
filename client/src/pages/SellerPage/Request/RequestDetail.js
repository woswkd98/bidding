import React, { useState ,useEffect} from 'react';
import Counter from '../../../components/Counter'
import { Container, Divider, Typography, ListItemText, List, ListItem } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Bidding from '../Bidding';
import RequestCard from '../../../components/RequestCard';
import CheckIcon from '@material-ui/icons/Check';
import Fade from '@material-ui/core/Fade';

const useStyles = makeStyles((theme) => ({
    root: {
        padding: theme.spacing(8, 0, 6),
    },
    gridStyle: {
        margin:'auto',
        width: "90%",
        color: 'rgb(104,104,106)'
    },
    loadingStyle: {
        display: 'block',
        margin: '4% auto',
    },
}));

function RequestDetail(props) {

    const classes = useStyles();
    const [data] = useState(props.location.state.data);
    const [open, setOpen] = useState(false);
    const [checked, setChecked] = useState(false);

    useEffect(()=>{
        setChecked(true);
        return () => {
            setChecked(false);
        }
    },[])

    return (
        <Fade in={checked}>
            <Container className={classes.root}>
                <Grid className={classes.gridStyle} container spacing={9}>
                    <Grid item xs={12} md={6}>
                        <RequestCard obj={data} />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        {(data.deadline < new Date().getTime())
                            ?
                            <>
                                <Typography variant="h5" gutterBottom>요청 마감</Typography>
                                요청이 마감되었습니다.
                            </>
                            :
                            <>
                                {[
                                    '구매자는 여러개의 견적을 받아 한가지를 선택하게 됩니다.',
                                    '견적을 전송한 후에 구매자와 채팅을 할 수 있습니다.',
                                    '나의 견적이 선택이 되면 마이페이지/진행중인 거래 에서 확인하실 수 있습니다.',
                                    '선택이 된 이후에도 합의 하에 세부 사항을 조정할 수 있습니다.',
                                ].map((obj, index) => {
                                    return (
                                        <List dense={true} key={index}>
                                            <ListItem>
                                                <CheckIcon />&nbsp;&nbsp;
                                                <ListItemText primary={obj} primaryTypographyProps={{variant:"subtitle1"}} />
                                            </ListItem>
                                        </List>
                                    )
                                })
                                }

                                <Typography variant="h5" gutterBottom style={{ textAlign: 'center' }}><Counter data={data} /></Typography>
                                <br/>
                                <Button style={{ width: "100%" }} variant="outlined" onClick={() => { setOpen(true) }}>
                                    입찰하기
                                </Button>
                            </>
                        }
                    </Grid>
                </Grid>
                <Divider />
                <Bidding data={data} open={open} setOpen={setOpen} />
            </Container>
        </Fade>
    )
}

export default RequestDetail;