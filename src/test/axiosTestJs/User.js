import React from 'react'
import axios from 'axios'
export default function User() {
    
    const insertUser = () => {
        axios.put("/users", {
            userPassword : "435734!!57t",
            userEmail : "12ggy@naver.com",
            userName : "fgh",
        }).then(res => {
            console.log(res);
        })

    }

    const getUserByEmail = () => { //2 번
        axios.post("/users/email", {
            userEmail : "woswkd98@naver.com"
        }).then(res => console.log(res));
    }

    const insertImage = () => {
        const formData = new FormData();
        formData.append("userId", 2);
        //formData.append("file", file);
        axios.post("/users", formData, {
        headers: {
            'Content-Type' : 'multipart/form-data'
        }}
        ).then(res => {
            console.log(res);
        })

    }
    
    const login = () => {
        let data = {
            email : "woswkd98@naver.com",
            pwd : "asgas!yg24y1"
        }

        axios.post("/login", data).then(res => console.log(res));
    }

    const logout = () => {
        axios.head("/logout").then(res => console.log(res));
    }

    const getAll = () => { //1 번임
        axios.get("/users").then(res => console.log(res));
    }

    const getById = () => {
        axios.get("/users/1").then(res => console.log(res));
    }

    const verify = () => {
        let data = {
            email : "woswkd98@naver.com"
        }
        axios.post("/verify", data).then(res => console.log(res));
    }

    const emailSender = () => {
        axios.get("/email/userEmail").then(res => console.log(res));
    }

    const emailVerifier = () => {
        axios.get("/email/userEmail/verifyNum").then(res => console.log(res));
    }

    const updatePassword = () => {
        let data = {
            userId : "1",
            pwd : "asgas!yg24y1"
        }


        axios.patch("/users", data).then(res => console.log(res));
    }
    
    
    return (
        <div>
             <button onClick = {insertUser}>insertUser</button>
             <button onClick = {insertImage}>insertImage</button>
             <button onClick = {login}>login</button>
             <button onClick = {logout}>logout</button>
             <button onClick = {getAll}>getAll</button>
             <button onClick = {verify}>verify</button>
             <button onClick = {updatePassword}>updatePassword</button>
             <button onClick = {emailSender}>emailSender</button>
             <button onClick = {emailVerifier}>emailVerifier</button>
             <button onClick = {getUserByEmail}>getUserByEmail</button>
        </div>
    )
}
