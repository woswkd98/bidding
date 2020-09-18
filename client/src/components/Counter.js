import React, { useEffect, useState, useCallback } from 'react';
import Axios from 'axios';

const Counter = ({data}) => {

    const [start, setStart] = useState(parseInt(new Date().getTime() / 1000));
    const [end] = useState(parseInt(new Date(data.deadline) / 1000));
    
    const timeOver = useCallback(() => {
        
        Axios.patch('/requests/' + data.request_id).then(res => {
            
        })
        .catch(err=>{
            console.log(err);
        })
    },[data])

    useEffect(() => {
        let interval;
        if (parseInt(end - start) <= 0) {
            timeOver();
            clearInterval(interval);
            console.log('stop');
        } else {
            interval = setInterval(() => {
                countDown();
            }, 1000);
        }
        return () => clearInterval(interval);
    }, [end, start,timeOver]);


    function countDown() {
        setStart(parseInt(new Date().getTime() / 1000));
    }
    let hour = parseInt((end - start) / 60 / 60);
    let minutes = parseInt(((end - start) / 60) % 60);
    let seconds = parseInt((end - start) % 60);
    return (
        <>
        {
        (parseInt(end - start) <= 0)
            ?
            <span>요청 마감</span>
            :
        <>
            {hour > 9 ? <span>요청 마감까지 : {hour}:</span> : <span>요청 마감까지 : 0{hour}:</span>}
            {minutes > 9 ? <span>{minutes}:</span> : <span>0{minutes}:</span>}
            {seconds > 9 ? <span>{seconds}</span> : <span>0{seconds}</span>}
        </>
        }
        </>
    )
}

export default Counter;