import React from "react";
import Typography from '@mui/material/Typography';
import { UserDataBlock } from './UserDataBlock';
import styled from "styled-components";

const MainWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  backgroundColor: #ECFBE0;
  height: 100vh;
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
const users = [{name: 'Иван', image: null}, {name: 'Василий', image: null},
               {name: 'Ирина', image: null},{name: 'Леша Автозапчасти', image: null},
               {name: 'Егор', image: null}]
const personalAccount = {name: 'Дмитий', balance: 100, image: null}

const App = () => {
  return (
    <MainWrapper>
      <MainUser>
        <UserDataBlock user={personalAccount} isPersonal={true}/>
      </MainUser>
      <Column>
       <Typography variant="h5" component="div">Выберте пользователя для перевода</Typography>

        {users.map(user => <UserDataBlock user={user} key={user.name} isPersonal={false}/>)}
      </Column>
     </MainWrapper>
  );
}

export default App;
