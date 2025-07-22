### 🔐 Complete OAuth2 Security Flow with Spring Boot

This project demonstrates the complete implementation of an OAuth2 authentication flow in a **Spring Boot** application, using best security practices, Docker containerization, and automated deployment with **GitHub Actions**.
  
[Link to the full step-by-step guide on LinkedIn](https://www.linkedin.com/pulse/o-que-%C3%A9-login-oauth2-tutorial-e-implementa%C3%A7%C3%A3o-com-spring-rodrigues-d1pvf/?trackingId=8wlGrX6Soe9RiJcThVNQzg%3D%3D)

#### 🧱 Technologies Used

- Spring Boot 3.x  
- Spring Security + OAuth2  
- Docker  
- GitHub Actions  
- Java 17  
- Maven  

---

#### 🚀 How to Run the Project

#### 1. Clone the Repository

```bash
git clone https://github.com/seu-usuario/oauth2-security-flow.git
cd oauth2-security-flow
```

#### 2. Build the Application

```bash
./mvnw clean install
```

#### 3. Run with Docker

Make sure Docker is installed and running.

```bash
docker build -t oauth2-security-app .
docker run -p 8080:8080 oauth2-security-app
```

The application will be available at: [http://localhost:8080](http://localhost:8080)

#### 4. Deployment with GitHub Actions

This project includes a CI/CD pipeline using **GitHub Actions**, defined in:

```
.github/workflows/deploy.yml
```

The pipeline performs the following steps:

1. On every `push` to the `main` branch, the pipeline is triggered.  
2. The Google Cloud SDK is configured using secrets.  
3. The Docker image is built from the project's `Dockerfile`.  
4. The image is pushed to the **Artifact Registry** (GCP Docker repository).  
5. The app is automatically deployed to **Cloud Run** using the pushed image.

#### 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
