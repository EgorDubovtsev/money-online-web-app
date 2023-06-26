import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import { Formik } from "formik";
import React from "react";
import styled from "styled-components";

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
                        <Typography variant="h5" gutterBottom>Банк "Online-Money"</Typography>
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
                        label="password"
                      />
                      
                      <Button disabled={isSubmitting} variant="contained" type="submit">Войти</Button>
                      <Button>Зарегистрироваться</Button>
                    </Stack>
                  </Box>
                </Form> 
              )}
          </Formik>
      </MainWrapper>
    )
}
export default App;