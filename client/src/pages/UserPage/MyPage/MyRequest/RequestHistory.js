import React from 'react'
import { Link  } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const RequestHistory = ({data}) => {

    const requestHistory = data.map((obj) => {
        return (
            <TableRow key={obj._id}>
                <TableCell component="th" scope="row">
                    {obj.category}
                </TableCell>
                <TableCell align="center">{obj.requestedAt}</TableCell>
                <TableCell align="center">{obj.state}</TableCell>
                <TableCell align="center"><Link to={{ pathname: `/user/detail`, state: obj }}>상세보기</Link></TableCell>
            </TableRow>
        )
    })

    return (
        <Paper elevation={3}>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>카테고리</TableCell>
                            <TableCell align="center">요청일</TableCell>
                            <TableCell align="center">상태</TableCell>
                            <TableCell align="center"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {requestHistory}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
    )
}

export default RequestHistory;
