# Inventarizace - Gypri Dílna

An inventory management system for the Gypri Dílna, built with modern Android development practices.

## Features

*   **Barcode Scanner:** Instantly check inventory details by scanning barcodes or QR codes using the device's camera.
*   **Item Search:** A persistent search bar to quickly find items by name, rack, or position.
*   **Add New Items:** A seamless WebView integration to add new items through a dedicated Google Form.
*   **Custom Branding:** A dark-themed UI built from the ground up, featuring the workshop's custom color palette and typography.

## Tech Stack & Architecture

*   **Language:** 100% [Kotlin](https://kotlinlang.org/)
*   **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3 design components.
*   **Architecture:** Model-View-ViewModel (MVVM)
*   **Navigation:** [Jetpack Navigation for Compose](https://developer.android.com/jetpack/compose/navigation)
*   **Asynchronous Programming:** Kotlin Coroutines & Flow
*   **Networking:** [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) for fetching data from the Google Sheets API.
*   **Image Loading:** [Coil](https://coil-kt.github.io/coil/) for loading and displaying images.
*   **Camera & ML:** [CameraX](https://developer.android.com/training/camerax) and [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning) for the scanner functionality.

## Branding

The app adheres to a strict visual identity:

*   **Typography:** Montserrat
*   **Color Palette (Dark Theme):**
    *   **Primary / Accent:** "Circuit Mint" - `#3AA69A`
    *   **Backgrounds:** "Graphite Core" - `#2F353E` & `#3A4049`
    *   **Text / Icons:** "Cloud Paper" - `#FAFDFF`

## Setup & Configuration

This project uses a Google Sheet as a backend. To get the project running, you need to set up your own Google Sheet and deploy a Google Apps Script.

### 1. Google Sheet Setup

Your spreadsheet **must** have a sheet named `inv` with the following columns (the names must be exact):
`NÁZEV`, `RACK`, `POZICE`, `BOX`, `NUMBER`, `ID`, `BARCODE`, `OBRÁZEK`

### 2. Google Apps Script Deployment

1.  Open your Google Sheet and go to **Extensions > Apps Script**.
2.  Delete any boilerplate code and paste the following script:

    ```javascript
    function doGet(e) {
      // Explicitly get the sheet by its name, "inv"
      const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName("inv");

      // If a sheet named "inv" isn't found, return an error.
      if (!sheet) {
        return ContentService
          .createTextOutput(JSON.stringify({error: "Sheet 'inv' not found."}))
          .setMimeType(ContentService.MimeType.JSON);
      }

      const data = sheet.getDataRange().getValues();
      const headers = data.shift(); // Get the first row as headers

      const json = data.map(row => {
        let obj = {};
        headers.forEach((header, index) => {
          obj[header] = row[index];
        });
        return obj;
      });

      return ContentService.createTextOutput(JSON.stringify(json))
        .setMimeType(ContentService.MimeType.JSON);
    }
    ```

3.  Click **Deploy > New deployment**.
4.  Set the type to **Web app**.
5.  Under "Who has access", select **Anyone**.
6.  Click **Deploy**, authorize the script, and **copy the generated Web app URL**.

### 3. Android Project Configuration

1.  **API URL:** Open the file `app/src/main/java/cz/gypridilna/inventarizace/data/network/RetrofitClient.kt` and replace the placeholder `BASE_URL` with the Web app URL you copied.

2.  **Google Form URL:** Open `app/src/main/java/cz/gypridilna/inventarizace/ui/screens/AddItemScreen.kt` and replace the placeholder with the URL to your Google Form for adding new items.

3.  **Fonts:** Make sure you have the `montserrat_regular.ttf` and `montserrat_bold.ttf` font files in the `app/src/main/res/font` directory.

## Download

* Download can be found under the Release page.
