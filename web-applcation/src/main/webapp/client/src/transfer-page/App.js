import React, { useEffect, useState } from "react";
import { UserDataBlock } from './UserDataBlock';
import styled from "styled-components";
import CircularProgress from '@mui/material/CircularProgress';
import { getCurrencies, getCurrentUserFullInfo, getUsersAvailableForTransfer, logout } from "../utils/utils";
import { Button } from "@mui/material";
import { PaymentBlock } from "./component/PaymentBlock";

const MainWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  background-color: #ECFBE0;
  height: 70vh;
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
const CurrenciesWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  heigth: 600px;
  padding-left: 10%;
  background-color: #ECFBE0;
`

const Currency = styled.div`
  display: flex;
  width: 250px;
  background-color: #348DE5;
  margin-bottom: 5px;
  border-radius: 4px;
  white-space: nowrap;
  justify-content: center;
`
const CurrencyHeader = styled.div`
  width: 100%;
  font-size: 20px;
  text-align: left;
`

const IndexWrapper = styled.div`

`

const App = () => {
const [usersForTransfer, setUsersForTransfer] = useState([])
const [currentUser, setCurrentUser] = useState()
const [currencies, setCurrencies] = useState()

document.body.style.backgroundColor="#ECFBE0";
document.body.style.margin=0;
document.body.style.scroll

  useEffect(() => {
    setCurrentUserInfo()
    setUsersForTransferInfo()
    fetchCurrenciesRates()
  }, [])

  const fetchCurrenciesRates = () => {
    getCurrencies().then(response =>{
      setCurrencies(response.data)
    })
  }

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
    <IndexWrapper>
      <CurrenciesWrapper>
        <CurrencyHeader>Курсы валют</CurrencyHeader>
        {currencies && currencies.map(currency=> <Currency key={currency}><h3>{currency.code} - {currency.rate}руб.</h3></Currency>)}
      </CurrenciesWrapper>
      <MainWrapper>
        <MainUser>
          {currentUser != undefined && <UserDataBlock isOpen={false}  user={currentUser} isPersonal={true} updateCurrentUserBalance={setCurrentUserInfo}/>}
        </MainUser>
        <PaymentBlock usersForTransfer={usersForTransfer} currentUser={currentUser}  setCurrentUserInfo={setCurrentUserInfo} user={currentUser}/>
       </MainWrapper>
       <ButtonWrapper>
         <Button variant="outlined" onClick={logoutHandler}>Выход</Button>
       </ButtonWrapper>
    </IndexWrapper>

  );
}

export default App;
