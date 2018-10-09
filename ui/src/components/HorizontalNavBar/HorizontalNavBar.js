import React, {Component} from 'react'
import {Container, Dropdown, Icon, Menu} from 'semantic-ui-react'
import PropTypes from 'prop-types'
import './HorizontalNavBar.css'

class HorizontalNavBar extends Component {
	constructor(props) {
		super(props)

		this.state = {
			userName: ''
		}
	}

	componentDidMound() {
		const getUserUri = 'https://reqres.in/api/users/2'
		fetch(getUserUri).then((response) => {
			if (response.ok !== true) {
				throw new Error(`HTTP response code is '${response.status}'.`)
			}

			return response.json()
		}).then((object) => {
			this.setState({
				userName: `${object.data.first_name} ${object.data.last_name}`
			})
		}).catch((error) => {
			console.log('Failed to fetch user data. ' + error.message)
		})
	}

	render() {
		return (
			<Menu inverted size='large' className='horizontal-nav-bar'>
				<Container>
          <Menu.Item as='a' icon='sidebar' onClick={this.props.toggleSidebar}
          className='hidden-on-small-monitor hidden-on-large-monitor' />
          <Menu.Item as='a' header>
            <Icon name='sun' color='yellow' /> My Statistic System
          </Menu.Item>
          <Menu.Item as='a' className='hidden-on-tablet hidden-on-phone' active>Overview</Menu.Item>
          <Menu.Item as='a' className='hidden-on-tablet hidden-on-phone'>Panels</Menu.Item>
          <Menu.Item as='a' className='hidden-on-tablet hidden-on-phone'>Inverters</Menu.Item>
          <Menu.Item as='a' className='hidden-on-tablet hidden-on-phone'>Batteries</Menu.Item>
          <Menu.Item position='right' className='hidden-on-tablet hidden-on-phone'>
            <Dropdown item icon='dropdown' text={this.state.userName}>
              <Dropdown.Menu>
                <Dropdown.Item icon='user' text='Profile' />
                <Dropdown.Item icon='setting' text='Settings' />
                <Dropdown.Item icon='help circle' text='Help' />
                <Dropdown.Divider />
                <Dropdown.Item icon='log out' text='Sign out' />
              </Dropdown.Menu>
            </Dropdown>
          </Menu.Item>
        </Container>
			</Menu>
		)
	}

}

HorizontalNavBar.propTypes = {
  toggleSidebar: PropTypes.func.isRequired
}

export default HorizontalNavBar