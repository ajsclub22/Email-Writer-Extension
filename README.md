## Code Description

This project is organized into three main parts: the backend service, the frontend React application, and the extension version. Below is a breakdown of each part:

### 1. **email-writer** (Java Spring Boot Backend)

The backend service is built with **Java Spring Boot** and provides the API endpoint for generating AI-based email replies. The backend is responsible for processing the requests and communicating with the Gemini API to generate email responses based on the provided content and tone.

- **`EmailController.java`**: This file exposes the API endpoint `/api/mail/generate` that accepts POST requests with the email content and tone. It delegates the actual generation process to the service layer.
- **`EmailBody.java`**: This class models the request body, containing two fields: `emailContent` (the original email message) and `tone` (the desired tone of the generated reply).
- **`EmailGeneratorService.java`**: This service constructs a prompt using the provided email content and tone, and then sends the request to the Gemini API. The response is extracted and returned to the client.
- **`application.properties`**: This file contains configuration properties like the API key and the URI for the Gemini API.

### 2. **email-writer-react** (Frontend - React Application)

The frontend is a React-based web application that allows users to input email content and select the desired tone to generate email replies. It communicates with the backend to send requests and display the generated replies.

- **`components/`**: This directory contains React components that handle the user interface for inputting email content and selecting tone, as well as displaying the generated email responses.
- **`public/`**: Contains static files like `index.html` and images used by the React app.
- **`package.json`**: The file contains all dependencies for the frontend React application and scripts to build, run, and test the frontend application.

### 3. **email-writer-ext** (Extension Version)

The extension version provides integration with email platforms, allowing users to generate AI replies directly in their email clients, such as Gmail or Outlook.

- **`src/`**: Contains the core logic and background scripts for the extension. It communicates with the backend service to generate replies based on the email content and selected tone.
- **`manifest.json`**: This file defines the metadata and settings for the extension, such as permissions, background scripts, and browser-specific configurations.
- **`background.js`**: Contains the logic that handles the background tasks for the extension, including communicating with the backend API to fetch generated replies.

---

These three parts work together to allow users to easily generate and send AI-powered email replies based on the tone and content they specify. You can interact with the backend via the API, use the React frontend for a user-friendly interface, or integrate the extension directly into your email client for seamless email generation.

