import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import rootReducer from './modules';


const store = createStore(rootReducer);

document.querySelector('body').style.backgroundColor = '#F2F3F4'
document.querySelector('body').style.margin = '0px'

ReactDOM.render(
    <Provider store={store}>
      <App />
    </Provider>,
  document.getElementById('root')
);

