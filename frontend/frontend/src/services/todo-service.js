import {frontendConfig} from '../config/frontend-config.js'
import $ from 'jquery'

/**
 * Export all To Do service CRUD methods via todoService object.
 *
 * @type {{getTodos: (function(*=, *=, *=): *), updateTodo: (function(*=): *), getTodo: (function(*): *), deleteTodo: (function(*): *), createTodo: (function(*=): *)}}
 */

export const todoService = {
  createTodo,
  deleteTodo,
  getTodo,
  getTodos,
  updateTodo,
};

/**
 * Create a new To Do item.
 *
 * @param todoBase Object holding the To Do data.
 * @returns {Promise<Response | never>}
 */

function createTodo(todoBase) {

  const requestProperties = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(todoBase),
    mode: 'cors'
  };

  return fetch(`${frontendConfig.apiUrl}`, requestProperties)
    .then(handleJsonResponse)
    .then(data => {
      return data
    })
}

/**
 * Delete an existing To Do item.
 *
 * @param todoId The To Do item ID.
 * @returns {*}
 */

function deleteTodo(todoId) {

  const requestProperties = {
    method: 'DELETE',
  };

  const fetchUrl = `${frontendConfig.apiUrl}` + todoId;

  return fetch(fetchUrl, requestProperties)
    .then(response => {
      return response.status
    });
}

/**
 * Get a To Do item by it's ID.
 *
 * @param todoId The To Do item ID.
 * @returns {*}
 */

function getTodo(todoId) {

  const requestOptions = {
    method: 'GET',
  };

  const fetchUrl = `${frontendConfig.apiUrl}` + todoId;

  return fetch(fetchUrl, requestOptions)
    .then(handleJsonResponse)
    .then(data => {
      return data
    })
}

/**
 * Get To Do items.
 *
 * @returns {*}
 */

function getTodos(state, limit, offset) {

  const requestOptions = {
    method: 'GET',
    mode: 'cors'
  };

  const fetchUrl = `${frontendConfig.apiUrl}` + "?" +
    $.param({state: state, limit: limit, offset: offset});

  return fetch(fetchUrl, requestOptions)
    .then(handleJsonResponse)
    .then(data => {
      return data
    })
}

/**
 * Update an exsting To Do item.
 *
 * @param todoBase Object holding the To Do data.
 * @returns {*}
 */

function updateTodo(todoBase) {

  const requestOptions = {
    method: 'PUT',
    body: JSON.stringify(todoBase)
  };

  return fetch(`${frontendConfig.apiUrl}`, requestOptions)
    .then(response => {
      return response.status
    });
}

/**
 * Handle an API HTTP response.
 *
 * @param response
 * @returns {*}
 */

function handleJsonResponse(response) {
  return response.text().then(text => {
    const data = text && JSON.parse(text);

    // Handle errors:
    if (!response.ok) {
      if (response.status === 401) {
        location.reload(true)
      }

      const error = (data && data.message) || response.statusText;
      return Promise.reject(error)
    }

    return data
  })
}
