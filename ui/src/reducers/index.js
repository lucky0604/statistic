import {combineReducers} from 'redux'
import batteriesReducer from './batteriesReducer'
import panelCollectionReducer from './panelCollectionReducer'
import sidebarVisibilityReducer from './sidebarVisibilityReducer'

const combineReducer = combineReducers({
	batteries: batteriesReducer,
	panelCollection: panelCollectionReducer,
	sidebarVisible: sidebarVisibilityReducer
})

export default combineReducer