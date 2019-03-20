import {frontendConfig} from '../config/frontend-config.js'
import {util} from '../util/date-formatter';
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

  todoBase = util.formatDateInObjectBackend(todoBase);

  const requestProperties = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(todoBase),
    mode: 'cors'
  };

  return fetch(`${frontendConfig.apiUrl}`, requestProperties)
    .then(handleJsonResponse);
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
    mode: 'cors'
  };

  const fetchUrl = `${frontendConfig.apiUrl}` + todoId;

  return fetch(fetchUrl, requestProperties)
    .then(response => {
      return response.status;
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
    mode: 'cors'
  };

  const fetchUrl = `${frontendConfig.apiUrl}` + todoId;

  return fetch(fetchUrl, requestOptions)
    .then(handleJsonResponse);
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
    .then(handleJsonResponse);
}

/**
 * Update an exsting To Do item.
 *
 * @param todoFull Object holding the To Do data.
 * @returns {*}
 */

function updateTodo(todoFull) {

  todoFull = util.formatDateInObjectBackend(todoFull);

  const requestOptions = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(todoFull),
    mode: 'cors'
  };

  const fetchUrl = `${frontendConfig.apiUrl}` + todoFull.id;

  return fetch(fetchUrl, requestOptions)
    .then(handleJsonResponse);
}

/**
 * Handle an API HTTP response.
 *
 * @param response
 * @returns {*}
 */

function handleJsonResponse(response) {

  // Handle errors:
  if (!response.ok) {
    const error = response.statusText;
    return Promise.reject(error);
  }

  // This could happen:
  if (response.status === 204 || response.text === "") {
    return Promise.resolve();
  }

  // Finally process JSON response:
  return response.text().then(text => {

    // This could return an array or a single object:
    const data = text && JSON.parse(text);

    // Convert to frontend date:
    return util.formatDateInObjectFrontend(data);
  })
}
