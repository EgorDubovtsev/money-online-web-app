import axios from 'axios'

export const formatDate = (date) => {
    const dateParts = date.split("/")
    console.log(dateParts)
    return new Date(dateParts[2], dateParts[1] - 1, dateParts[0])
}