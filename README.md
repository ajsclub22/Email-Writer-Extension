📬 API Usage
Endpoint
bash
Copy
Edit
POST /api/mail/generate
Request Body
json
Copy
Edit
{
  "emailContent": "Hi, can we move our meeting to tomorrow afternoon?",
  "tone": "formal"
}
Sample Response
json
Copy
Edit
"Certainly, we can reschedule the meeting to tomorrow afternoon. Please let me know a suitable time."
🔄 How It Works
Request: A client sends a JSON request to the /api/mail/generate endpoint containing:

The original email message (emailContent)

The desired tone (tone) — e.g., formal, casual, etc.

Processing:

EmailController handles the request and delegates it to the EmailGeneratorService.

The service dynamically constructs a prompt including:

The original email content

The desired tone

Instructions to return only the reply content (no subject line, headers, or additional metadata).

Response:

The constructed prompt is sent to the Gemini API via a non-blocking WebClient request.

The API responds with a generated reply, which the service extracts and returns to the client in a clean format.

💡 Example Prompt Sent to Gemini
text
Copy
Edit
You are a professional email assistant. Generate only the email content as a reply to the message below. 
Do not include a subject line, headers, or any explanations. Only output the reply content.

Use a formal tone.

Original message:
"Hi, can we move our meeting to tomorrow afternoon?"

Your reply:
📁 Project Structure
graphql
Copy
Edit
src/
├── controller/
│   └── EmailController.java         # Exposes the /generate endpoint
├── model/
│   └── EmailBody.java               # Holds emailContent and tone
├── service/
│   └── EmailGeneratorService.java   # Builds the prompt, calls Gemini API
└── resources/
    └── application.properties       # Config for API keys, URI
🧪 Testing the API
Using cURL
bash
Copy
Edit
curl -X POST http://localhost:8080/api/mail/generate \
     -H "Content-Type: application/json" \
     -d '{
           "emailContent": "Can you share the project updates today?",
           "tone": "professional"
         }'
Using Postman
Method: POST

URL: http://localhost:8080/api/mail/generate

Headers: Content-Type: application/json

Body:

json
Copy
Edit
{
  "emailContent": "Please send me the final draft by EOD.",
  "tone": "formal"
}
🧰 Troubleshooting & Logs
API Key: Ensure your Gemini API key is valid and active.

Response Parsing Issues: If the response isn't returned as expected, check the logs for the following error message:
Failed to extract response from Gemini API.

Logging: SLF4J is used for logging and error tracking in the application.

📌 Notes
Response format: The response contains only the reply content (no subject line, headers, or additional explanations).

Prompt Formatting: The system ensures that the Gemini API returns a clean, usable reply based on the provided content and tone.

Future Enhancements: You can easily extend this project to include:

Multilingual support

Emotion/tone switching

Integration with popular email services (Gmail API, Outlook, etc.)

🤝 Contributing
Contributions are welcome! Feel free to fork the repository, create a branch, and submit a pull request.

For bugs, issues, or feature requests, please open an issue.

Key Elements in this README:
Headings: Clearly defined sections using ## for major sections.

Code Blocks: Using triple backticks for code sections (e.g., configuration properties, cURL commands).

Lists: Bullet points for features, dependencies, and configuration instructions.

Links: Used Markdown links for Google AI Studio (API setup).

Formatting: Code, commands, and file paths are highlighted with proper Markdown syntax.
