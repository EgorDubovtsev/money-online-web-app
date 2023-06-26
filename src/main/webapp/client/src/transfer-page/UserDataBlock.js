import React from "react";
import styled from 'styled-components';
import { Balance } from './Balance';
import Button from '@mui/material/Button';
// import image from './images.png';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';

let backgroundColor= '#73B446'
const hoverColor =  'red'

const BalanceWrapper = styled.div`
  flex-wrap: wrap;
  display:block;
  flex-direction: row;
  height:140px;
  justify-content:  space-between;
  width:500px;
  background-color:${backgroundColor}; 
  border: 1px solid;
  border-radius: 4px;
  padding-top:4px;
  padding-bottom:4px;
  padding-right:20px;
  padding-left:20px;
  &:hover {
    background-color:${hoverColor};
  }

`

const UserWrapper = styled.div`{
  flex-wrap: nowrap;
  width: 25%;

}`
const Name = styled.div`{
  display:block;
  text-align: center;
  width: 75%;
  heigth: 100%;
  text-align:center;
  margin-bottom: 5px;
}`

const ProfilePicture = styled.img`
  border-radius: 10px;
  height: 80px;
  width: 80px;
`
const Transition = React.forwardRef(function Transition(props,ref) {
  console.log('class')
  return <Slide direction="up" ref={ref} {...props} />;
});

export const UserDataBlock = ({user, isPersonal}) => {

  const [isOpen, setIsOpen] = React.useState(false);

  const handleClickOpen = () => {
    setIsOpen(true);
  };

  const handleClose = () => {
    setIsOpen(false)
  };
  if (!user) {
    return <div>{'Ошибка'}</div>
  }

    return(
      <>
        <BalanceWrapper onClick={handleClickOpen}>
            <Name>{user.name}</Name>
            <UserWrapper>
                {/* <ProfilePicture  src={user.image==null ? image : user.image} alt='Profile'/> */}
                {isPersonal && <Balance/>}
                
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
                  <DialogContent>
                    <TextField
                      autoFocus
                      margin="dense"
                      id="name"
                      label="Сумма в рублях"
                      type="TEXT"
                      fullWidth
                      variant="standard"
                    />
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={()=>handleClose()}>Отмена</Button>
                    <Button onClick={()=>handleClose()}>Перевести</Button>
                  </DialogActions>
                </Dialog>
    </>
    )
}