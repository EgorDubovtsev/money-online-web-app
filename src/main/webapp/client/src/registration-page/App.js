import { Box, Button, InputLabel, MenuItem, Select, Stack, TextField, Typography } from "@mui/material";
import { Formik } from "formik";
import React from "react";
import styled from "styled-components";
import { baseUrl, loginPath } from "../utils/consts";
import InputMask from 'react-input-mask';
import { formatDate } from "../utils/utils";

const ADULT_AGE = 18;
const Form = styled.form`
  width: 100%;
  justify-content: center;
  display: flex;
`
const NameWrapper = styled.div`
  display: flex;
  justify-content: center;
`

const MainWrapper = styled.div`
  width: 100%;
  padding-top: 10vh;
  height: 100vh;
  background-color: white;
`

const loginHandler = () => {
  window.location = baseUrl + loginPath;
}

const validateForm = (values) => {
  console.log(values)
  const errors = {};
  if (!values.username) {
    errors.username = 'Обязательно';
  } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.username)) {
    errors.username = 'Некорректный email';
  }

  if (!values.password) {
    errors.password = 'Обязательно';
  }
  
  if (!values.currency || values.currency === 'None') {
    errors.currency = 'Обязательно';
  }
  
  if (values.password !== values.passwordSec || !values.passwordSec) {
    errors.passwordSec = 'Пароли не совпадают';
  }
  
  if (!values.birthdate) {
    errors.birthdate = 'Обязательно';
  }

  const minAdultBirthday = new Date()
  minAdultBirthday.setFullYear(new Date().getFullYear() - ADULT_AGE)
  if (values.birthdate && formatDate(values.birthdate) > minAdultBirthday) {
    errors.birthdate = 'Вам должно быть более '+ ADULT_AGE + ' лет';
  }

  return errors;
}

const App= () => {
    return(
      <MainWrapper>
        <Formik
              initialValues={{ username: '', password: '', passwordSec: '', birthdate: '', currency: 'None' }}
              validate={validateForm}
              onSubmit={(values, { setSubmitting }) => {
                console.log('submitting')
                //todo: отправлять рест
              }}
            >
              {({
                values,
                errors,
                touched,
                handleBlur,
                handleSubmit,
                handleChange,
              }) => (
                <Form onSubmit={handleSubmit} action="registration/process" method="post">
                  <Box sx={{width:'30%'}}>
                    <Stack spacing={3}>
                      <NameWrapper>
                        <Typography variant="h5" gutterBottom>Регистрация</Typography>
                      </NameWrapper>
                      <TextField  
                        type="text"
                        name="username"
                        error={errors.username !== undefined && touched.username}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.username}
                        helperText={touched.username && errors.username}
                        label="Email"
                      />
                      <TextField
                        type="password"
                        name="password"
                        width="50px"
                        error={errors.password !== undefined && touched.password}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.password}
                        helperText={touched.password && errors.password}
                        label="Пароль"
                      />
                      <TextField
                        type="password"
                        name="passwordSec"
                        width="50px"
                        error={errors.passwordSec !== undefined && touched.passwordSec}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.passwordSec}
                        helperText={touched.passwordSec && errors.passwordSec}
                        label="Повторите пароль"
                      />
                      <InputMask
                        mask="99/99/9999"
                        maskPlaceholder="dd/mm/yyyy" 
                        onBlur={handleBlur} 
                        value={values.birthdate} 
                        onChange={handleChange} >
                          <TextField
                            type="text"
                            label="Дата рождения"
                            name="birthdate"
                            width="50px"
                            helperText={touched.birthdate && errors.birthdate}
                            error={errors.birthdate !== undefined && touched.birthdate}
                          />
                      </InputMask>
                      <Select
                        type="select"
                        name="currency"
                        width="50px"
                        error={errors.currency !== undefined && touched.currency}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.currency}
                      >
                        <MenuItem value="None" disabled>Валюта счета</MenuItem>
                        <MenuItem value="RUB">RUB</MenuItem>
                        <MenuItem value="USD">USD</MenuItem>
                      </Select>
                      
                      <Button variant="contained" type="submit">Зарегистрироваться</Button>
                      <Button onClick={loginHandler}>Уже есть аккаунт?</Button>
                    </Stack>
                  </Box>
                </Form> 
              )}
          </Formik>
      </MainWrapper>
    )
}
export default App;