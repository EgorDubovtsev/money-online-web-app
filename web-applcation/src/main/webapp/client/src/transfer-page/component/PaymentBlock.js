import React, { useEffect, useState } from "react";
import Typography from '@mui/material/Typography';
import { UserDataBlock } from '../UserDataBlock';
import styled from "styled-components";
import CircularProgress from '@mui/material/CircularProgress';
import { ModalTransferByAccount } from "./ModalTransferByAccount";

const OperationButton = styled.div`
  flex-wrap: wrap;
  display: flex;
  flex-direction: row;
  justify-content:  space-between;
  width: 320px;
  text-align: center;
  heigth: 100px;
  background-color: #348DE5; 
  border: 1px solid;
  border-radius: 4px;
  padding-top: 4px;
  padding-bottom: 4px;
  padding-right: 20px;
  padding-left: 20px;
  &:hover {
    background-color:#1976D2;
  }
`

const Column = styled.div`
  display: flex;
  flex-direction: column;
  width: 500px;
  justify-content: right;
`
  
export const PaymentBlock = ({usersForTransfer, user, setCurrentUserInfo, currentUser}) => {
    const [transferAmount, setTransferAmount] = React.useState(0);
    const [isOpen, setIsOpen] = React.useState(false);
    const [transferError, setTransferError] = React.useState(null);
    const [isSubmitting, setSubmitting] = React.useState(false);


  const createTransition = (accountLoginTo, amount) => {
    createTransfer(user.username, accountLoginTo, amount).catch(res=>{
      setSubmitting(false);  
      if (res.response) {
        setTransferError(res.response.data);
      } 

    }).then(response => {
        setSubmitting(false)
        if (response && response.status === SUCCESS_CODE) {

          updateCurrentUserBalance();
          setIsOpen(false);
        }
      setTransferAmount(0)
    })
    
  } 

  const amountChangeHandler = (e) => {
    setTransferAmount(e.target.value.replace(/\D/,''));
  } 

  const handleTransfer = () => {
    setSubmitting(true)
    createTransition(user.username, transferAmount);
  };

  const handleClose = () => {
    setTransferAmount(0)
    setIsOpen(false);
  };

    const transferModalProps = {
        isOpen:isOpen,
        user: user,
        isSubmitting:isSubmitting,
        handleClose: handleClose,
        transferError:transferError,
        transferAmount:transferAmount,
        amountChangeHandler:amountChangeHandler,
        setTransferAmount:setTransferAmount,
        handleTransfer:handleTransfer,
        //handleClickOpen: {handleClickOpen}
    }

    
    return (
        <Column>
            <Typography variant="h5" component="div">Выберте пользователя для перевода</Typography>
            <OperationButton>Выполнить перевод по номеру счета</OperationButton>
            <ModalTransferByAccount {...transferModalProps} />
            {usersForTransfer.length === 0 ? <CircularProgress /> : usersForTransfer.map(user => <UserDataBlock {...transferModalProps}  user={user} key={user.id} isPersonal={false} currentUser={currentUser} updateCurrentUserBalance={setCurrentUserInfo}/>)}
        </Column>
    );
} 