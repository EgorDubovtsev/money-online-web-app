import React from "react";
import styled from 'styled-components';
import { Balance } from './Balance';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';

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
  console.log('class')
  return <Slide direction="up" ref={ref} {...props} />;
});

export const UserDataBlock = ({user, isPersonal}) => {

  const [isOpen, setIsOpen] = React.useState(false);

  const handleClickOpen = () => {
    setIsOpen(!isPersonal && true);
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
          <ProfilePictureWrapper>
            <div>
              <ProfilePicture  src={user.image == null ? defaultImage : user.image} alt='Profile'/>
            </div>
          </ProfilePictureWrapper>
          <UserWrapper>
            <Name>{user.name}</Name>
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