import {frontendConfig} from '../config/frontend-config.js'
import $ from 'jquery'
import moment from "moment";

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

  const requestOptions = {
    method: 'PUT',
    headers:{ 'Content-Type': 'application/json' },
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

  // This could happen:
  if (response.status === 204 || response.text === "") {
    return Promise.reject();
  }

  // Finally process JSON response:
  return response.text().then(text => {

    const data = text && JSON.parse(text);

    // Convert to frontend date:
    if (data.hasOwnProperty("dueDate")) {
      const date = data.dueDate;
      delete data.dueDate;
      data.dueDate = moment(date).format('MMMM Do YYYY, h:mm:ss A');
    }

    // Handle errors:
    if (!response.ok) {
      const error = (data && data.message) || response.statusText;
      return Promise.reject(error);
    }

    return data;
  })
}
