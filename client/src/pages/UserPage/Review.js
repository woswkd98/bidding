

import React, { useState } from 'react';
import Axios from 'axios';
import Rating from '@material-ui/lab/Rating';
import {makeStyles} from '@material-ui/core/styles';
import TextareaAutosize from '@material-ui/core/TextareaAutosize';
import Button from '@material-ui/core/Button';
import Box from '@material-ui/core/Box';


//별점별 코멘트
const labels ={
  0.5: '별로예요',
  1: '별로예요',
  1.5: '별로예요',
  2: '그저그래요',
  2.5: '그저그래요',
  3: '괜찮아요',
  3.5: '괜찮아요',
  4: '좋아요',
  4.5: '좋아요',
  5: '최고예요',
}

const useStyles = makeStyles((theme)=>({ //css 
  //theme
  //제작하는 어플리케이션의 전체적인 테마 


    root : {
        display: 'flex',
        flexDirection: 'column',
        '& > * + *': {
            marginTop: theme.spacing(1),
          },
    
    }
}))

//pros로 받아온 데이터(판매자,유저 정보)를 axios로 이용해서 서버로 보내기
const Review = ({user_id,seller_id,userName}) => { //판매자,구매자(리뷰 작성자) props

    const [value, setValue] = React.useState(2);
    const [hover, setHover] = React.useState(-1);

    const[text, setText] = useState('');
    const[rating, setRating] = useState('');

    // 테스트(props 안 받았을 때)
    // const user_id = '123123123';
    // const seller_id = '12314345';
    // const userName = '김동제'
   
    const classes = useStyles();

    //텍스트, 별점 값 
    const textChangeHandelr = (e) =>{
        setText(e.currentTarget.value)
    }
    const ratingChangeHandler = (e) =>{
        setRating(e.currentTarget.value)
        hoverCahngeHandler();
    }

    const hoverCahngeHandler = (e,newValue) =>{
           setValue(newValue);
    }

    console.log(seller_id);
    const submitHandler = (e) =>{
        e.preventDefault(); //클릭해서 넘어가는 것을 방지함
        Axios.put('/reviews', {
            // user_id : userInfo.id, //두 번째 파라미터에 등록하고자 하는 정보
            // seller_id : sellerInfo.id, //id prop를 읽을 수 없는 타입에러 
            // name : userInfo.id,
            context: text,
            rating: rating,
            userId: Number(localStorage.getItem("user_id")),//props
            sellerId: seller_id,//props
            //username 없앰
            
        })
        .then(function(response){
            console.log(response);
        })
        .catch(err=>{
            console.log(err);
        })
    }


    

    // 프런트 -> 서버로 접근할 수 있는 axios 추가
    // Axios.post('http://localhost:4000/api/review',{ //데이터 
    //     user_id : userInfo.id, //id가 정의되어 있지 않음
    //     seller_id : sellerInfo.id,
    //     name : userInfo.name,
    //     text : 'text',
    //     rating : 'rating',
    // }).then(function(response){
    //     console.log(response);
    // }).catch(function(error){
    //     console.log(error);
    // })

 //post 메서드로 userInfo.id, seller_id를 json 데이터로 전달받음


    return (
      <div
        className={classes.root}
        style={{ maxWidth: "700px", margin: "2rem auto" , textAlign: "center", marginBotton: "2rem" }}
      >
        <div>
        <label><h2><b>판매자 만족도는 어떠신가요?</b></h2></label>
        <Rating
          name="half-rating"
          defaultValue={2.5}
          precision={0.5}
          size="large"
          align="center"
          onChange={ratingChangeHandler}
          // onChange={(event,newValue)=>{
          //   setValue(newValue);
          // }}
          onChangeActive={(event, newHover)=>{
            setHover(newHover);
          }}
        />
        {value !== null && <Box ml={2}>{labels[hover !== -1 ? hover: value]}</Box>}
       </div>
        
        <br/>
        <br/>

        <hr width="700px" color="whitesmoke"/>

        <br/>
        <br/>
        <h2><b>이용 후기는 어떠신가요?</b></h2>
        <TextareaAutosize 
        rowsMin={2} //3줄 간격
        placeholder="만족도에 대한 후기를 남겨주세요"
        style={{backgroundColor:"whitesmoke", borderColor:"lightgray"}}
        onChange={textChangeHandelr}
        />
        <br/>
        <br/>



        <div>
        <Button
          onClick={submitHandler}
          type="submit"
          style={{ maxWidth: "100px" }}
          variant="contained"
          color="primary"
        >
          등록
        </Button>
        </div>
      
      </div>
    );
}



export default Review;











/*

1.함수형 컴포넌트에 첫 번째 매개변수는 컴포넌트에 들어온 props 객체
구조 분해를 사용해 props 내부의 값을 추출한 후, name과 age에 할당함

2.레이아웃-네이버 쇼핑과 비슷하게
부모 컴포넌트(거래 완료 페이지) - 리뷰쓰기 버튼 ->리뷰페이지가 팝업으로 열림

3.
axios.post('url', {
    //데이터
           user_id : userInfo.id,
           seller_id : sellerInfo.id,
           name : userInfo.name,
           text : 'text',
           rating : 'rating',
})

4. 후기 작성 후 판매자에게 정보를 보냄

5.postman으로 api 테스트

6.value 값 넣으면 별점 클릭이 안됨!


const Person = () =>{
    const [data, setData] = useState({
        age:0,
        name: '',

    })
}

7. 취소를 누르면 원래 화면으로 넘어갈 수 있게함 
-router,history push 이용

        {seller.name} 판매자에게 보냄 
            text,
            rating 

        -리뷰 입력 화면 




9. 


*/

