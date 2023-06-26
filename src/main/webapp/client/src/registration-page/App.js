import { Box, Button, MenuItem, Select, Stack, TextField, Typography } from "@mui/material";
import { Formik } from "formik";
import React from "react";
import styled from "styled-components";

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
  padding-top: 30vh;
  height: 100vh;
  background-color: white;
`

const validateForm = (values) => {
  const errors = {};
  if (!values.email) {
    errors.email = 'Обязательно';
  } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)) {
    errors.email = 'Некорректный email';
  }

  if (!values.password) {
    errors.password = 'Обязательно';
  }
  if (!values.currency) {
    errors.password = 'Обязательно';
  }
  if (values.password!==values.passwordSec) {
    errors.passwordSec = 'Пароли не совпадают';
  }
  if (!values.birthdate) {
    errors.birthdate = 'Обязательно';
  }
  const minAdultBirtday = new Date()
  minAdultBirtday.setFullYear(new Date().getFullYear() - ADULT_AGE)
  if (values.birthdate && new Date(values.birthdate) < minAdultBirtday) {
    errors.birthdate = 'Вам должно быть более '+ADULT_AGE+' лет';
  }
  return errors;
}

const App= () => {
    return(
      <MainWrapper>
        <Formik
              initialValues={{ email: '', password: '' }}
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
                handleChange,
                handleSubmit,
                isSubmitting,
              }) => (
                <Form onSubmit={handleSubmit} >
                  <Box sx={{width:'30%'}}>
                    <Stack spacing={3}>
                      <NameWrapper>
                        <Typography variant="h5" gutterBottom>Регистрация</Typography>
                      </NameWrapper>
                      <TextField
                        id="outlined-required"
                        type="text"
                        name="email"
                        error={errors.email !== undefined && touched.email}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.email}
                        helperText={touched.email && errors.email}
                        label="email"
                      />
                      <TextField
                        id="outlined-required"
                        type="text"
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
                        id="outlined-required"
                        type="text"
                        name="passwordSec"
                        width="50px"
                        error={errors.passwordSec !== undefined && touched.passwordSec}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.passwordSec}
                        helperText={touched.passwordSec && errors.passwordSec}
                        label="Повторите пароль"
                      />
                      <TextField
                        id="outlined-required"
                        type="date"
                        name="birthdate"
                        width="50px"
                        error={errors.birthdate !== undefined && touched.birthdate}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.birthdate}
                        helperText={errors.birthdate ? errors.birthdate : "Дата рождения"}
                      />
                      <Select
                        id="outlined-required"
                        type="select"
                        name="currency"
                        width="50px"
                        error={errors.currency !== undefined && touched.currency}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        // value={values.currency}
                        defaultValue={"RUB"}
                      >
                        <MenuItem value="RUB">RUB</MenuItem>
                        <MenuItem value="USD">USD</MenuItem>
                      </Select>
                      
                      <Button disabled={isSubmitting} variant="contained" type="submit">Зарегистрироваться</Button>
                      <Button>Уже есть аккаунт?</Button>
                    </Stack>
                  </Box>
                </Form> 
              )}
          </Formik>
      </MainWrapper>
    )
}
export default App;