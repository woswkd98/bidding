import React from 'react'
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import BidCard from '../../../../components/BidCard';

const useStyles = makeStyles((theme) => ({
    scorllStyle : {
        width: '100%',
        height: '445px',
        overflowY: 'auto',
        overflowX: 'hidden',
        margin: 'auto 0px',
        padding: '0px',
    },
}));

const Bids = ({data,requestData,onClickChoice}) => {

    const classes = useStyles();
    console.log("1111111111111111111");
    console.log(data);
    console.log(requestData);
    const bids = data.map((obj) => {
        return (
            <BidCard key={obj.bidding_id} data={obj} requestData={requestData} onClickChoice={onClickChoice}/>
        )
    })

    return (
        <div className={classes.scorllStyle}>
            {data.length > 0 ? <>{bids}</> : <Typography variant="h6">현재까지 도착한 견적이 없습니다.</Typography>}
        </div>
    )
}

export default Bids;
