import moment from "moment";

export const util = {
  formatDate
};

function formatDate(date) {
  return moment(date).format('MMMM Do YYYY, h:mm:ss A');
}
