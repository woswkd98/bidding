import React from 'react'
import axios from 'axios'
export default function Bidding() {
    const insertBidding = () => {
        axios.post("/biddings", {
            context : "1235",
            price : 13251,
            sellerId : 2,
            requestId : 22
        }).then(res => console.log(res))
    }

    const getBiddingByRequestId = () => { // 7번
        axios.get("/biddings/request/22")
        .then(res => console.log(res))
    }
    const getBiddingBySellerId = () => { 
        axios.get("/biddings/seller/2")
        .then(res => console.log(res))
    }
    const deleteBidding = () => {
        axios.delete("/biddings/2")
        .then(res => console.log(res))
    }
    const tradeCancel = () => {
        axios.post("/biddings/tradeCancel", {
            requestId : 22
        })
        .then(res => console.log(res))
    }
    const biddingCancel = () => {
        axios.post("/biddings/biddingCancel", {
            biddingId : 2,
        })
        .then(res => console.log(res))
    }

    const chioce = () => {
        axios.post("/biddings/chioce", {
            requestId : 22,
            biddingId : 2,
        })
        .then(res => console.log(res))
    }

    const complete = () => {
        axios.post("/biddings/complete", {
            biddingId : 2,
        })
        .then(res => console.log(res))
    }
    return (
        <div>
             <button onClick = {insertBidding}>insertBidding</button>
            <button onClick = {getBiddingByRequestId}>getBiddingByRequestId</button>
            <button onClick = {getBiddingBySellerId}>getBiddingBySellerId</button>
            <button onClick = {tradeCancel}>tradeCancel</button>
            <button onClick = {biddingCancel}>biddingCancel</button>
            <button onClick = {chioce}>chioce</button>
            <button onClick = {complete}>complete</button>

        </div>
    )
}
