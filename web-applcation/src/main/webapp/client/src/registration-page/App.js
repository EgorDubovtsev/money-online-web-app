import { Box, Button, FormHelperText, InputLabel, MenuItem, Select, Stack, TextField, Typography } from "@mui/material";
import { Formik } from "formik";
import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { BASE_URL, LOGIN_PATH, SUCCESS_CODE } from "../utils/consts";
import InputMask from 'react-input-mask';
import { formatDate, getCurrencies, isDateValid, registration } from "../utils/utils";

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
  window.location = BASE_URL + LOGIN_PATH;
}

const validateForm = (values) => {
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

  if (!isDateValid(values.birthdate)) {
    errors.birthdate = 'Некорректно указана дата';
  }
    
  if (!values.name) {
    errors.name = 'Обязательно';
  }

  const minAdultBirthday = new Date()
  minAdultBirthday.setFullYear(new Date().getFullYear() - ADULT_AGE)
  if (values.birthdate && formatDate(values.birthdate) > minAdultBirthday) {
    errors.birthdate = 'Вам должно быть более '+ ADULT_AGE + ' лет';
  }

  return errors;
}

const createRegistrationFormdata = (values) => {
  const form = {
    username: values.username,
    name: values.name,
    password: values.password,
    currency: values.currency,
    birthdate: new Date(values.birthdate)
  }

  return form
} 



const App = () => {
  const [submitError, setSubmitError] = useState();
  const [currencies, setCurrencies] = useState([]);

  useEffect(() => {
    getCurrencies().then(response =>{
      setCurrencies(response.data)
    })
  }, [])

  const submitHandler = (values, { setSubmitting }) => {
    setSubmitting(true)
    const form = createRegistrationFormdata(values)

    registration(form).catch(res => {
      setSubmitError(res.response.data)

    }).then(res =>{
      setSubmitting(false)

      if (res && res.status === SUCCESS_CODE) {
        loginHandler()
      }

  })

  }
    return(
      <MainWrapper>
        <Formik
              initialValues={{ username: '', name: '', password: '', passwordSec: '', birthdate: '', currency: 'None' }}
              validate={validateForm}
              onSubmit={submitHandler}
              currencies={currencies}
            >
              {({
                values,
                errors,
                touched,
                handleBlur,
                isSubmitting,
                handleSubmit,
                handleChange,
              }) => (
                <Form onSubmit={handleSubmit}>
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
                        type="text"
                        name="name"
                        error={errors.name !== undefined && touched.name}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.name}
                        helperText={touched.name && errors.name}
                        label="Имя"
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
                        {currencies.length!=0 && currencies.map(currency => <MenuItem key={currency} value={currency}>{currency}</MenuItem>)}
                      </Select>
                      
                      <Button variant="contained" disabled={isSubmitting} type="submit">Зарегистрироваться</Button>
                      <FormHelperText filled error>{submitError}</FormHelperText>
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