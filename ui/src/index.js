import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'
import {createStore} from 'redux'
import combinedReducer from './reducers'

import 'semantic-ui-css/semantic.min.css'
import './index.css'


import AppContainer from './containers/AppContainer'
import registerServiceWorker from './registerServiceWorker'

const appStore = createStore(
  combinedReducer,
  undefined,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
)

ReactDOM.render(
  <Provider store={appStore}>
    <AppContainer />
  </Provider>,
  document.getElementById('root')
)

registerServiceWorker()