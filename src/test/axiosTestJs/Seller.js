import React from 'react'
import axios from 'axios'
export default function Seller() {
    const insertSeller =() => {
        const formData = new FormData();

        formData.append('portfolio', "125tr132t");
        formData.append("userId", 3)
  
        axios.post("/sellers", formData, {
            headers: {
                'Content-Type' : 'multipart/form-data'
            }
        }).then(res => {
            console.log(res);
        })
    }

    const updateSeller = () => {


    }

    const getSeller = () => {
        axios.get("/sellers").then(res => console.log(res))
    }

    const getSellerById = () => {
        axios.get("sellers/4").then(res => console.log(res))
    }

    return (
        <div>
            <button onClick = {insertSeller}>insertSeller</button>
            <button onClick = {getSeller}>getSeller</button>
            <button onClick = {getSellerById}>getSellerById</button>
        </div>
    )
}

