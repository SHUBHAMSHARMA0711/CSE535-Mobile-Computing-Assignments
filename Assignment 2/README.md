# Weather History App

## Description

This App is designed to retrieve historical weather data for a specific date using a free weather API. The user can input a date, and the app will display the maximum and minimum temperature recorded on that date. If the date is in the future, the app will calculate the average of the maximum and minimum temperatures from the last 10 available years.

## Implementation

### 1. Weather Data Retrieval:
- The app utilizes a free weather API `Visual Crossing Weather API` to fetch historical weather data in JSON format.
- The user inputs the date for which they want to retrieve weather data.
- The app sends a request to the API with the provided date.
- The API responds with a JSON file containing weather data for the specified date.
- The loacation is hardcoded to Delhi, India for all weather retrieval requests.

### 2. Database Integration:
- The app is integrated with a Room database to store retrieved weather data for offline access.
- Upon initialization, the app checks if the database exists. If not, it creates the necessary database and schema.
- The schema includes fields for date, maximum temperature, and minimum temperature.
- When weather data is retrieved from the API, it is inserted into the database.
- On each Get Weather request from user, App also fetches the Minimum and Maximum temperature of past ten years from current year and store it in the database for offline usage.

### 3. Data Handling:
- If the requested date is in the future, the app calculates the average of the maximum and minimum temperatures from the last 10 available years.
- If weather data for the requested date is available in the database, it is retrieved from the database instead of making an API call.

### 4. Errors Handling:
- If any user clicks on Get Weather Button without selecting any date then they will get "Please Select a Date First" error message.
- If the user is offline and data in not available in database then app shows the "No Data Available for Selected Date" error message..
