import axios from 'axios'
import { BASE_URL, CREATE_TRANSFER_PATH, GET_CURRENT_USER_PATH, LOGIN_PROCESS_PATH, REGISTRATION_PROCESS_PATH, USERS_AVAILABLE_FOR_TRANSFER_PATH, LOGOUT_PATH } from './consts'

export const formatDate = (date) => {
    const dateParts = date.split("/")
    return new Date(dateParts[2], dateParts[1] - 1, dateParts[0])
}

export const isDateValid = (date) => {
    const dateParts = date.split("/")
    return dateParts[0] < 32
        && dateParts[0] > 0
        && dateParts[1] < 13
        && dateParts[1] > 0 
        && dateParts[2] > 1950 
}

export async function getUsersAvailableForTransfer() {
    const url = BASE_URL + USERS_AVAILABLE_FOR_TRANSFER_PATH
    return await axios.get(url)
}

export async function getCurrentUserFullInfo() {
    const url = BASE_URL + GET_CURRENT_USER_PATH
    return await axios.get(url)
}

export function login(form) {
    const url = BASE_URL + LOGIN_PROCESS_PATH
    return axios.post(url, form)

}

export function registration(values) {
    const url = BASE_URL + REGISTRATION_PROCESS_PATH

    return axios.post(url, values)

}

export function createTransfer(userFrom, userTo, amount) {
    const url = BASE_URL + CREATE_TRANSFER_PATH;
    const data = {
        userLoginFrom: userFrom,
        userLoginTo: userTo,
        amount: amount
    }
    return axios.post(url, data)
}

export function logout() {
    const url = BASE_URL + LOGOUT_PATH;
    window.location.replace(url);
}