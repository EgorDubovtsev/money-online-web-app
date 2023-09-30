import { Box, Button, FormHelperText, Stack, TextField, Typography } from "@mui/material";
import { Formik } from "formik";
import React, { useState } from "react";
import styled from "styled-components";
import { BASE_URL, REGISTRATION_PATH, SUCCESS_CODE, TRANSFER_PAGE_PATH } from "../utils/consts";
import { login } from "../utils/utils";

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
  if (!values.username) {
    errors.username = 'Обязательно';
  } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.username)) {
    // errors.username = 'Некорректный email';
  }

  if (!values.password) {
    errors.password = 'Обязательно';
  }
  return errors;
}

const registrationHandler = () => {
  window.location = BASE_URL + REGISTRATION_PATH;
}

const successAuthenicationHandler = () => {
  window.location = BASE_URL + TRANSFER_PAGE_PATH
}

const createLoginFormdata = (values) => {
  const form = new FormData()
  form.append("username", values.username)
  form.append("password", values.password)

  return form
} 


const App = () => {
  const [submitError, setSubmitError] = useState();

  const submitHanler = (values, { setSubmitting }) => {
    setSubmitting(true)

    const formData = createLoginFormdata(values)
    login(formData).catch(res => {
      setSubmitError(res.response.data)

    }).then(res =>{
      setSubmitting(false)

      if (res.status === SUCCESS_CODE) {
        successAuthenicationHandler()
      }

  })
}
    return(
      <MainWrapper>
        <Formik
              initialValues={{ username: '', password: '' }}
              validate={validateForm}
              onSubmit={submitHanler}
            >
              {({
                values,
                errors,
                touched,
                isSubmitting,
                handleBlur,
                handleSubmit,
                handleChange
              }) => (
                <Form onSubmit={handleSubmit}>
                  <Box sx={{width:'30%'}}>
                    <Stack spacing={3}>
                      <NameWrapper>
                        <Typography variant="h5" gutterBottom>Банк "Online-Money"</Typography>
                      </NameWrapper>
                      <TextField
                        type="text"
                        name="username"
                        error={errors.username !== undefined && touched.username}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.username}
                        helperText={touched.username && errors.username}
                        label="Электронная почта"
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
                      <Button variant="contained" type="submit" disabled={isSubmitting}>Войти</Button>
                      <FormHelperText filled error>{submitError}</FormHelperText>
                      <Button onClick={registrationHandler}>Зарегистрироваться</Button>
                    </Stack>
                  </Box>
                </Form> 
              )}
          </Formik>
      </MainWrapper>
    )
}
export default App;