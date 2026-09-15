# Emergency Resource Network - Render Deployment

This version is prepared to run the complete application on Render as **one Spring Boot web service** with a managed **PostgreSQL** database.

## Architecture
- Spring Boot backend
- HTML/CSS/JavaScript frontend served by Spring Boot
- PostgreSQL database on Render
- No Vercel or Netlify required

## Deploy
1. Upload/push the `Emergency-Resource-Network` folder to GitHub.
2. In Render, choose **New -> Blueprint** and select the GitHub repository.
3. Render reads `render.yaml` and creates the web service and PostgreSQL database.
4. Wait for the Docker build and deployment to finish.
5. Open the generated `https://...onrender.com` URL.
6. Health check: `https://...onrender.com/api/health`

The frontend calls `/api/...`, so frontend and backend remain on the same Render service.

## Local PostgreSQL
Set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD` before running locally.
