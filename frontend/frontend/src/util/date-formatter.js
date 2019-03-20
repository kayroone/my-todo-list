import moment from "moment";

export const util = {
  formatDateShort,
  formatDateInObjectFrontend,
  formatDateInObjectBackend
};

function formatDateShort(date) {

  return moment(date).format('MMM Do YYYY, h:mm A');
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

  if (todo.hasOwnProperty("dueDate") && todo.dueDate) {
    const date = moment(todo.dueDate, 'MMM Do YYYY, h:mm A').toDate();
    delete todo.dueDate;
    todo.dueDate = date.toISOString();
  }

  return todo;
}
