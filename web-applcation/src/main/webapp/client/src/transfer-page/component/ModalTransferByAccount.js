import React, { useEffect } from "react";
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { Alert, CircularProgress } from "@mui/material";


const Transition = React.forwardRef(function Transition(props,ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export const ModalTransferByAccount = ({isOpen, isSubmitting, handleClose, transferError,transferAmount, amountChangeHandler,
                                 setTransferAmount, handleTransfer}) => {


    return (<Dialog
                  open={isOpen}
                  TransitionComponent={Transition}
                  keepMounted
                  onClose={()=>handleClose()}
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
                      onFocus={()=>setTransferAmount("")}
                      margin="dense"
                      id="amount"
                      label="Сумма перевода в валюте счета:"
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
                </Dialog>);
}

