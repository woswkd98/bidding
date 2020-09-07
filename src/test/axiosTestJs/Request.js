import React from 'react'
import Axios from 'axios'

export default function Request() {

   const insertRequest = () => {
        Axios.put("/requests",{ // 유저 이름 안넣어도 됨
            userId : 4,
            category : "ㅅㅅㅅ",
            detail : "ryery",
            deadline : new Date().getTime(),
            hopeDate : "2017-09-03", // 여긴 날짜에 맞는 스트링값넣어주셈 
            state : "요청 진행중", // 요거 리졸버에있는 state 적으면됨
            tags : ["123", "ㅇㄹㅇ", "ㅇㄹㅇㅎ"]
        }).then(res => {
            console.log(res);
        })
    }

    const deleteRequest = () => {
        Axios.delete("/requests/10").then(res => {
            console.log(res);
        })
    }



    const findByCategory = () => {
        Axios.get("/requests/category/ㅅㅅㅅ").then(res => {
            console.log(res);
        })
    }
    const findByTag = () => {
        Axios.get("/requests/tag/ㅇㄹㅇ").then(res => {
            console.log(res);
        })
    }

    const findById = () => { //4번 6번 똑같은걸로 호출하셈요 
        Axios.get("/requests/id/4").then(res => {
            console.log(res);
        })
    }

    const paging = () => { // 1번 부터 5개  // 3번임 
        Axios.get("/requests/1/5").then(res => {
            console.log(res);
        })
    }

    const requestTimeOver = () => {
        Axios.patch("/requests/2").then(res => {
            console.log(res);
        })
    }


    return (
        <div>
            <button onClick = {insertRequest}>insertRequest</button>
            <button onClick = {deleteRequest}>deleteRequest</button>
            <button onClick = {findByCategory}>findByCategory</button>
            <button onClick = {findByTag}>findByTag</button>
            <button onClick = {findById}>findById</button>
            <button onClick = {paging}>paging</button>
            <button onClick = {requestTimeOver}>requestTimeOver</button>
        </div>
    )
}
