
// import { Card } from 'primereact/card';
import styled from 'styled-components';
import Typography from '@mui/material/Typography';
import { UserDataBlock } from './UserDataBlock';

const MaiwWrapper = styled.div`
  display:flex;
  justify-content: center;
  width:100%;
`
const Column = styled.div`
  display:flex;
  flex-direction: column;
  width: 500px;
  justify-content: right;
`
const MainUser = styled.div`
  margin-top: 30px;
  display:flex;
  width: 100vh;
  justify-content: center;
`
const users = [{name: 'Иван', image: null}, {name: 'Василий', image: null},
               {name: 'Ирина', image: null},{name: 'Леша Автозапчасти', image: null},
               {name: 'Егор', image: null}]
const personalAccount = {name: 'Дмитий', balance: 100, image: null}

const App = () => {
  return (
    <MaiwWrapper className="App" style={{justifyContent: 'center', width:"100%", backgroundColor:"#ECFBE0", height:"100vh"}}>
      <MainUser>
        <UserDataBlock user={personalAccount} isPersonal={true}/>
      </MainUser>
      <Column>
       <Typography variant="h5" component="div">Выберте пользователя для перевода</Typography>

        {users.map(user => <UserDataBlock user={user} key={user.name} isPersonal={false}/>)}      
      </Column>
    </MaiwWrapper>
  );
}

export default App;
