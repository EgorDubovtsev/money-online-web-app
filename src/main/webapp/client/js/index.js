import react from 'react'
import ReactDOM from 'react-dom'
import { App } from '../src/transfer-page/App'

document.addEventListener('DOMContentLoaded', () => {
  const root = document.getElementById('indexPage')

  ReactDOM.render(<App/>, root)//todo: добавить в путь разделение по страничкам
})
