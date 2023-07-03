import axios from 'axios'
import { BASE_URL, GET_CURRENT_USER_PATH, LOGIN_PROCESS_PATH, USERS_AVAILABLE_FOR_TRANSFER_PATH } from './consts'

export const formatDate = (date) => {
    const dateParts = date.split("/")
    console.log(dateParts)
    return new Date(dateParts[2], dateParts[1] - 1, dateParts[0])
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