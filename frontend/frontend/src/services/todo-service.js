import {FrontendConfig} from '../config/frontend-config.js'
import {DateUtil} from '../util/date-formatter';
import $ from 'jquery'

/**
 * Export all to do service CRUD methods via TodoService object.
 *
 * @type {{getTodos: (function(*=, *=, *=): *), updateTodo: (function(*=): *), getTodo: (function(*): *),
 * deleteTodo: (function(*): *), createTodo: (function(*=): *)}}
 */

export const TodoService = {
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

  const requestObject = DateUtil.formatDateInObjectBackend(todoBase);

  const requestProperties = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(requestObject),
    mode: 'cors'
  };

  return fetch(`${FrontendConfig.apiUrl}`, requestProperties)
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

  const fetchUrl = `${FrontendConfig.apiUrl}` + todoId;

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

  const fetchUrl = `${FrontendConfig.apiUrl}` + todoId;

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

  const fetchUrl = `${FrontendConfig.apiUrl}` + "?" +
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

  const requestObject = DateUtil.formatDateInObjectBackend(todoFull);

  const requestOptions = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(requestObject),
    mode: 'cors'
  };

  const fetchUrl = `${FrontendConfig.apiUrl}` + requestObject.id;

  return fetch(fetchUrl, requestOptions)
    .then(handleJsonResponse);
}

/**
 * Handle an API HTTP JSON response.
 *
 * @param response
 * @returns {*}
 */

function handleJsonResponse(response) {

  /* Handle errors */
  if (!response.ok) {
    const error = response.statusText || "";
    return Promise.reject(error);
  }

  /* This could happen */
  if (response.status === 204 || response.text === "") {
    return Promise.resolve();
  }

  /* Finally process JSON response */
  return response.text().then(text => {

    const data = text && JSON.parse(text);

    // Convert to frontend date:
    return DateUtil.formatDateInObjectFrontend(data);
  })
}
