import moment from "moment";

export const util = {
  formatDate,
  formatDateShort
};

function formatDate(date) {
  return moment(date).format('MMMM Do YYYY, h:mm:ss A');
}

function formatDateShort(date) {
  return moment(date).format('MMM Do YY, h:mm A');
}
