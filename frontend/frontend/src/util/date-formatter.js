import moment from "moment";

export const DateUtil = {
  formatDateShort,
  toDefaultDate,
  formatDateInObjectFrontend,
  formatDateInObjectBackend
};

function formatDateShort(date) {

  return moment(date).format('MMM Do YYYY, h:mm A');
}

function toDefaultDate(date) {

  return moment(date, 'MMM Do YYYY, h:mm A').toDate();
}

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

function formatDateInObjectBackend(todo) {

  const formattedObject = deepCopy(todo);

  if (formattedObject.hasOwnProperty("dueDate") && formattedObject.dueDate) {
    const date = toDefaultDate(formattedObject.dueDate);
    delete formattedObject.dueDate;
    formattedObject.dueDate = date.toISOString();
  }

  return formattedObject;
}

function deepCopy(objectSource) {

  let objectTarget = {};

  for (let prop in objectSource) {
    if (objectSource.hasOwnProperty(prop)) {
      objectTarget[prop] = objectSource[prop];
    }
  }

  return objectTarget;
}
