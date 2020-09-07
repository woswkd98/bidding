import React, {useState} from 'react'
import axios from 'axios'
export default function TestImg() {

    const [file, setFile] = useState(undefined);
    const [formData, setFormData] = useState(new FormData());
    const onChangeFile = (e) => {
        console.log(e.target.files[0]);
        setFile({
            file : e.target.files[0]
        })

        formData.append('file', e.target.files[0]);
        
        for(var pair of formData) {
            console.log(pair[0]+ ', '+ pair[1]); 
         }
    }
    /*
        private String userPassword;
    private String userEmail;
    private String userName;
    private String phone;
    private MultipartFile file;
    */
    const getAllImageBySellerId = () => {
        axios.get("/sellers/2").then(res => {
            console.log(res);
        })
    }

    const submit = () => { // seller Image
        formData.append('userId', 3);
        formData.append('portfolio', "2365236");
        formData.append('file', file.file) //  여기에 여러개 저장해도 됨
        /*
        let data = {
            userPassword : "asd!!asdg1235",
            userEmail : "woswkd98@naver.com",
            userPhone : "010321154",
            file : formData
        }*/

        axios.post("/sellers", formData, {
            headers: {
                'Content-Type' : 'multipart/form-data'
            }
        }).then(res => {
            console.log(res);
        })

        setFormData({
            formData : new FormData()
        })
    }
    const submit2 = () => { // userImage
        formData.append('userId',2);
        formData.append('file', file.file)
        axios.post("/users", formData, {
            headers: {
                'Content-Type' : 'multipart/form-data'
            }
        }).then(res => {
            console.log(res);
        })

        setFormData({
            formData : new FormData()
        })
    }

    const test = () => {
        axios.get("/test").then(res =>  console.log(res))
    }

    return (
        <div>
            <input type = "file" onChange = {e => onChangeFile(e)}/>
            <button onClick = {submit}>submit</button>
            <button onClick = {submit2}>submit2</button>
            <button onClick = {test}>test</button>
            <button onClick = {getAllImageBySellerId}>test</button>
        </div>
    )
}
