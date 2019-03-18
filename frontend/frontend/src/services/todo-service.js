import {frontendConfig} from '../config/frontend-config.js'
import {authHeader} from '../helpers'

/**
 * Export all user service CRUD methods via todoService object.
 *
 * @type {{logout: logout, getAll: *, getById: (getById|*), update: *, login: *, delete: *, register: _register}}
 */

export const todoService = {
  login,
  logout,
  register,
  getAll,
  getById,
  update,
  delete: _delete
}

/**
 * Authenticate a user with it's username and password.
 *
 * @param username
 * @param password
 * @returns {Promise<Response | never>}
 */

function login (username, password) {
  const requestProperties = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({username, password})
  }

  return fetch(`${frontendConfig.apiUrl}/users/authenticate`, requestProperties)
    .then(handleResponse)
    .then(user => {
      // Login if JWT is present:
      if (user.token) {
        localStorage.setItem('user', JSON.stringify(user))
      }

      return user
    })
}

/**
 * Logout the active user.
 */

function logout () {
  localStorage.removeItem('user')
}

/**
 * Register a new user.
 *
 * @param user
 * @returns {Promise<Response | never>}
 */

function register (user) {
  const requestOptions = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(user)
  }

  return fetch(`${frontendConfig.apiUrl}/users/register`, requestOptions).then(handleResponse)
}

/**
 * Get all users.
 *
 * @returns {Promise<Response | never>}
 */

function getAll () {
  const requestOptions = {
    method: 'GET',
    headers: authHeader()
  }

  return fetch(`${frontendConfig.apiUrl}/users`, requestOptions).then(handleResponse)
}

/**
 * Get a user by it's ID.
 *
 * @param id
 * @returns {Promise<Response | never>}
 */

function getById (id) {
  const requestOptions = {
    method: 'GET',
    headers: authHeader()
  }

  return fetch(`${frontendConfig.apiUrl}/users/${id}`, requestOptions).then(handleResponse)
}

/**
 * Update an existing user.
 *
 * @param user
 * @returns {Promise<Response | never>}
 */

function update (user) {
  const requestOptions = {
    method: 'PUT',
    headers: { ...authHeader(), 'Content-Type': 'application/json' },
    body: JSON.stringify(user)
  }

  return fetch(`${frontendConfig.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse)
}

/**
 * Delete an existing user.
 *
 * Method naming with underscore because delete is a reserved word in JS.
 *
 * @param id
 * @returns {Promise<Response | never>}
 * @private
 */

function _delete (id) {
  const requestOptions = {
    method: 'DELETE',
    headers: authHeader()
  }

  return fetch(`${frontendConfig.apiUrl}/users/${id}`, requestOptions).then(handleResponse)
}

/**
 * Handle an API HTTP response.
 *
 * @param response
 * @returns {*}
 */

function handleResponse (response) {
  return response.text().then(text => {
    const data = text && JSON.parse(text)

    // Handle errors:
    if (!response.ok) {
      if (response.status === 401) {
        // Auto login for API 401 responses:
        logout()
        location.reload(true)
      }

      const error = (data && data.message) || response.statusText
      return Promise.reject(error)
    }

    return data
  })
}
