/**
 * Main frontend config object holding imprint data and the API URL.
 *
 * @type {{imprintEmail: string, apiUrl: string, imprintName: string, imprintPostcode: string,
 * imprintTown: string, imprintPhone: string, imprintStreet: string}}
 */

export const FrontendConfig = {
  apiUrl: "http://localhost:9080/todos/",
  imprintName: "Jan Wiegmann",
  imprintStreet: "Werrastraße 12",
  imprintTown: "Duisburg",
  imprintPostcode: "47051",
  imprintPhone: "015164506373",
  imprintEmail: "jw@jwiegmann.de"
};
