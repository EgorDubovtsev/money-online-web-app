import React, { useEffect, useState } from "react";
import Typography from '@mui/material/Typography';
import { UserDataBlock } from './UserDataBlock';
import styled from "styled-components";
import CircularProgress from '@mui/material/CircularProgress';
import { getCurrentUserFullInfo, getUsersAvailableForTransfer, logout } from "../utils/utils";
import { Button } from "@mui/material";

const MainWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  backgroundColor: #ECFBE0;
  height: 70vh;
`
const Column = styled.div`
  display: flex;
  flex-direction: column;
  width: 500px;
  justify-content: right;
`
const MainUser = styled.div`
  margin-top: 30px;
  display: flex;
  width: 100vh;
  height: 80px;
  justify-content: center;
`
const ButtonWrapper = styled.div`
  display: flex;
  width: 100vh;
  justify-content: center;
`


const App = () => {
const [usersForTransfer, setUsersForTransfer] = useState([])
const [currentUser, setCurrentUser] = useState()

  useEffect(() => {
    setCurrentUserInfo()
    setUsersForTransferInfo()
  }, [])

  const setCurrentUserInfo = () => {
    getCurrentUserFullInfo().then(response => {
        setCurrentUser(response.data)
    })
  }

  const setUsersForTransferInfo = () => {
    getUsersAvailableForTransfer().then(response => {
        setUsersForTransfer(response.data)
    })
  }

  if (currentUser == undefined) {
    return <CircularProgress />
  }

  const logoutHandler = () => {
    logout();
  }

  return (
    <>
      <MainWrapper>
        <MainUser>
          {currentUser != undefined && <UserDataBlock user={currentUser} isPersonal={true} updateCurrentUserBalance={setCurrentUserInfo}/>}
        </MainUser>
        <Column>
        <Typography variant="h5" component="div">Выберте пользователя для перевода</Typography>

          {usersForTransfer.length === 0 ? <CircularProgress /> : usersForTransfer.map(user => <UserDataBlock user={user} key={user.id} isPersonal={false} currentUser={currentUser} updateCurrentUserBalance={setCurrentUserInfo}/>)}
        </Column>
       </MainWrapper>
       <ButtonWrapper>
         <Button variant="outlined" onClick={logoutHandler}>Выход</Button>
       </ButtonWrapper>
    </>

  );
}

export default App;
