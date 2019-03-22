import moment from "moment";

/**
 * Object holding all date formatter functions.
 *
 * @type {{formatDateInObjectBackend: (function(*=): ({dueDate}|*)), formatDateShort: (function(*=): string),
 * toDefaultDate: (function(*=): Date), formatDateInObjectFrontend: formatDateInObjectFrontend}}
 */

export const DateUtil = {
  formatDateShort,
  toDefaultDate,
  formatDateInObjectFrontend,
  formatDateInObjectBackend
};

/**
 * Short(er) date format to be displayed in the frontend.
 *
 * @param date
 * @returns {string}
 */

function formatDateShort(date) {

  return moment(date).format('MMM Do YYYY');
}

/**
 * Default date -> Can be handled by the datepicker.
 *
 * @param date
 * @returns {Date}
 */

function toDefaultDate(date) {

  return moment(date, 'MMM Do YYYY').toDate();
}

/**
 * Format the date in a single to do item or an array of to do items.
 *
 * @param todo
 * @returns {*}
 */

function formatDateInObjectFrontend(todo) {

  // 1. Check if we have to format a full array of objects:
  if (Array.isArray(todo)) {
    const formatted = [];
    for (let i = 0; i < todo.length; i++) {
      formatted.push(formatDateInObjectFrontend(todo[i]));
    }
    return formatted;
  }

  // 2. Or just format a single object:
  if (todo.hasOwnProperty("dueDate") && todo.dueDate) {
    const date = todo.dueDate;
    delete todo.dueDate;
    todo.dueDate = formatDateShort(date);
  }

  return todo;
}

/**
 * Format the frontend date back to an ISO string -> Can be handled by the backend. Also deep copies
 * the object so that the local scope is not changed.
 *
 * @param todo
 * @returns {{dueDate}|*}
 */

function formatDateInObjectBackend(todo) {

  const formattedObject = deepCopy(todo);

  if (formattedObject.hasOwnProperty("dueDate") && formattedObject.dueDate) {
    const date = toDefaultDate(formattedObject.dueDate);
    delete formattedObject.dueDate;
    formattedObject.dueDate = date.toISOString();
  }

  return formattedObject;
}

/**
 * Deep copy an object and return a new object holding the contents of the input object.
 *
 * @param objectSource
 */

function deepCopy(objectSource) {

  let objectTarget = {};

  for (let prop in objectSource) {
    if (objectSource.hasOwnProperty(prop)) {
      objectTarget[prop] = objectSource[prop];
    }
  }

  return objectTarget;
}
