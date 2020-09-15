import React, { useState } from 'react'
import Canceled from '../../TradeState/Canceled';
import Complete from '../../TradeState/Complete';
import Failed from '../../TradeState/Failed';
import Progress from '../../TradeState/Progress';
import Waitting from '../../TradeState/Waitting';




const BidDetail = (props) => {
    console.log("props.location.state");  
    console.log(props.location.state);   

    const [propsData] = useState(props.location.state);
    console.log(propsData);
    switch (propsData.state) {
        case '거래 진행중':
            console.log("진행중");
            return <Progress data={propsData} />

        case '거래 완료':
            console.log("완료");
            return <Complete data={propsData}/>

        case '취소된 거래':
            console.log("거래");
            return <Canceled data={propsData}/>

        case '유찰':
            console.log("유찰");
            return  <Failed data={propsData}/>

        default:
            console.log("d");
            return <Waitting data={propsData} />
    }


}

export default BidDetail;