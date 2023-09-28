import React, { useEffect } from "react";
import styled from 'styled-components';
import { Balance } from './Balance';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { createTransfer } from "../utils/utils";
import { Alert, CircularProgress } from "@mui/material";
import { SUCCESS_CODE } from "../utils/consts";

let backgroundColor= '#348DE5'
const hoverColor =  '#1976D2'
const defaultImage = './img/images.png'

const BalanceWrapper = styled.div`
  flex-wrap: wrap;
  display: flex;
  flex-direction: row;
  justify-content:  space-between;
  width: 320px;
  heigth: 100px;
  background-color: ${backgroundColor}; 
  border: 1px solid;
  border-radius: 4px;
  padding-top: 4px;
  padding-bottom: 4px;
  padding-right: 20px;
  padding-left: 20px;
  &:hover {
    background-color:${hoverColor};
  }

`

const UserWrapper = styled.div`{
  width: 80%;
  flex-wrap: nowrap;

}`
const Name = styled.div`{
  font-size: 25px;
  display: block;
  text-align: center;
  width: 100%;
  text-align:center;
  margin-bottom: 5px;
}`

const ProfilePicture = styled.img`
  border-radius: 10px;
  height: 40px;
  width: 40px;
`

const ProfilePictureWrapper = styled.div`
  width: 20%;
  display: flex;
  justify-conten: center;
`
const Transition = React.forwardRef(function Transition(props,ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export const UserDataBlock = ({user, isPersonal, currentUser, updateCurrentUserBalance}) => {
  const [balance, setBalance] = React.useState(0);
  const [currency, setCurrency] = React.useState('RUB');
  const [transferAmount, setTransferAmount] = React.useState(0);
  const [isOpen, setIsOpen] = React.useState(false);
  const [transferError, setTransferError] = React.useState(null);
  const [isSubmitting, setSubmitting] = React.useState(false);

  useEffect(()=> {
    if (isPersonal) {
      setBalance(user.balance);
      setCurrency(user.currency);
    }
  }, [])

  useEffect(()=> {
    setBalance(user.balance);
  }, [user])

  const handleClickOpen = () => {
    setTransferError(null)
    setIsOpen(!isPersonal && true);
  };

  const createTransition = (accountLoginTo, amount) => {
    createTransfer(currentUser.username, accountLoginTo, amount).catch(res=>{
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
    setIsOpen(false);
  };

  if (!user) {
    return <div>{'Ошибка'}</div>;
  }

    return(
      <>
        <BalanceWrapper onClick={handleClickOpen}>
          <ProfilePictureWrapper>
            <div>
              <ProfilePicture  src={user.image == undefined ? defaultImage : user.image} alt='Profile'/>
            </div>
          </ProfilePictureWrapper>
          <UserWrapper>
            <Name>{user.name}</Name>
            {isPersonal && <Balance balance={balance} currency={currency} />}                
          </UserWrapper> 
        </BalanceWrapper>
         <Dialog
                  open={isOpen}
                  TransitionComponent={Transition}
                  keepMounted
                  onClose={handleClose}
                  closeAfterTransition
                  aria-describedby="alert-dialog-slide-description"
                >
                  <DialogTitle>{"Укажите сумму перевода"}</DialogTitle>
                   {transferError !=null && <Alert severity="error">{transferError}</Alert>}
                  <DialogContent>
                    <TextField
                      autoFocus
                      value={transferAmount}
                      onChange={amountChangeHandler}
                      margin="dense"
                      id="amount"
                      label="Сумма в рублях"
                      type="text"
                      fullWidth
                      variant="standard"
                    />
                  </DialogContent>
                  <DialogActions>
                    {isSubmitting ? <CircularProgress /> :
                     <>
                      <Button onClick={()=>handleClose()}>Отмена</Button>
                      <Button onClick={()=>handleTransfer()} disabled={transferError !== null}>Перевести</Button>
                    </>}
                    
                  </DialogActions>
                </Dialog>
    </>
    )
}