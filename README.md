# Rumorify
 
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)

## Overview
**Rumorify** is a full-stack web application built with Spring Boot on the backend and ReactJS on the frontend. It aims to provide a localized social media platform for residents of a neighborhood or small town. With Rumorify, users can connect with their neighbors, share local news, events, and announcements, and engage in community discussions. The platform enhances neighborhood communication and fosters a stronger sense of community.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features
- **User Authentication**: Users can register, log in, and manage their accounts.
- **Real-time Notifications**: Users receive instant notifications about important events.
- **Multilingual Support**: The application supports multiple languages and users can change their language preference.
- **Responsive Design**: The application works seamlessly on different devices (mobile, tablet, desktop).
- **File Upload**: Users can upload and manage files through the application.
<!-- - **Search Functionality**: Users can quickly find specific information within the application. -->
- **Opaque Token Authentication**: Secure and encrypted tokens are used for authentication processes.
- **Email Verification**: Users can complete their registration by verifying their account with a token sent via email.


## Technologies
### Frontend
- **ReactJS**: A JavaScript library for building user interfaces.
- **Vite**: A build tool that provides a faster and leaner development experience for modern web projects.
- **Redux Toolkit**: A predictable state container for JavaScript apps.
- **Axios**: A promise-based HTTP client for the browser and Node.js.
- **Bootstrap**: A CSS framework for developing responsive and mobile-first websites.
- **React-i18next**: Internationalization for React done right.
- **React-Redux**: Official React bindings for Redux.
- **React-Toastify**: Allows you to add notifications to your app with ease.
- **React-Dom**: Provides DOM-specific methods that can be used at the top level of a web app.
- **React-Router-Dom**: DOM bindings for React Router.

### Backend
- **Spring Boot**: A framework for building production-ready applications quickly.
- **Spring Data JPA**: A part of the larger Spring Data family, makes it easy to implement JPA-based repositories.
- **Spring Security**: A powerful and highly customizable authentication and access-control framework.
- **Spring Boot Actuator**: Adds production-ready features to help you monitor and manage your application.
- **Spring Boot DevTools**: Provides fast application restarts, LiveReload, and configurations for enhanced development experience.
- **H2 Database**: An in-memory database, useful for development and testing.
- **ModelMapper**: An intelligent, convention-based object mapper.
- **Tika**: A content analysis toolkit.
- **Log4j2**: A logging framework.
- **Thymeleaf**: A modern server-side Java template engine for web and standalone environments.


## Installation
### Prerequisites
- Java 17
- Node.js & npm

### Backend Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/tokayOzgur/Rumorify.git
    cd Rumorify/ws
    ```

2. Build the project:
    ```sh
    ./mvnw clean install
    ```

3. Run the application:
    ```sh
    ./mvnw spring-boot:run
    ```

### Frontend Setup
1. Navigate to the frontend directory:
    ```sh
    cd ../frontend
    ```

2. Install dependencies:
    ```sh
    npm install
    ```

3. Start the development server:
    ```sh
    npm run dev
    ```

## Usage
1. Open your browser and navigate to `http://localhost:5173` to see the frontend application.
2. The backend API is running on `http://localhost:8080`.
3. The H2 Database Console is running on `http://localhost:8080/h2-console/login.jsp`

## Contributing
We welcome contributions from the community! To contribute to this project, please follow these steps:

1. **Fork the repository**:
   - Click the "Fork" button at the top right corner of the repository page.

2. **Create your feature branch**:
   - Clone your forked repository to your local machine:
     ```sh
     git clone https://github.com/your-username/your-repo.git
     cd your-repo
     ```
   - Create a new branch for your feature or bugfix:
     ```sh
     git checkout -b feature/AmazingFeature
     ```

3. **Commit your changes**:
   - Make your changes and commit them with a descriptive commit message:
     ```sh
     git add .
     git commit -m 'Add some AmazingFeature'
     ```

4. **Push to the branch**:
   - Push your changes to your forked repository:
     ```sh
     git push origin feature/AmazingFeature
     ```

5. **Open a Pull Request**:
   - Navigate to the original repository on GitHub and click the "New Pull Request" button.
   - Select the branch you just pushed to and submit the pull request for review.

Thank you for your contribution!

## License
Distributed under the MIT License. See `LICENSE` for more information.

## Contact
Özgür Tokay - [ozytky@gmail.com](mailto:ozytky@gmail.com)

Project Link: [https://github.com/tokayOzgur/Rumorify](https://github.com/tokayOzgur/Rumorify)
