import react from 'react'
import ReactDOM from 'react-dom'
import { App } from './App'

document.addEventListener('DOMContentLoaded', () => {
  const root = document.getElementById('indexPage')

  ReactDOM.render(<App/>, root)
})
