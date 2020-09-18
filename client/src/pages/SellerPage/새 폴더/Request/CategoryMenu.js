import React from 'react'
import { List, ListItem, ListItemText } from '@material-ui/core';
import SearchBar from './SearchBar';

const CategoryMenu = ({setCategory}) => {

    const categories = [
        {
            category : '모든 요청'
        },
        {
            category : '웹 개발'
        },
        {
            category : '앱 개발'
        },
        {
            category : '소프트웨어 개발'
        },
        {
            category : '유지보수'
        },
    ]

    const onClickCategory = (value) => {
        setCategory(value)
    }

    const categoryList = categories.map((obj,index)=>{
        return(
            <ListItem key={index} button onClick={() => { onClickCategory(obj.category) }}>
                <ListItemText primary={obj.category} />
            </ListItem>
        )
    })

    return (
        <>
        <SearchBar/>
        <List>
            {categoryList}
        </List>
        </>
    )
}

export default CategoryMenu;
