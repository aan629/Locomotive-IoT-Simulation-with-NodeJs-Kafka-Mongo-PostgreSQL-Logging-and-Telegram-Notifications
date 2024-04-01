# Locomotive-IoT-Simulation-with-NodeJs-Kafka-Mongo-PostgreSQL-Logging-and-Telegram-Notifications

Hello everyone, this time I want to present my project entitled "IoT Locomotive Simulation with NodeJs, Kafka, Mongo, MYSQL, Logging and Telegram Notifications". The main feature of this system will be using a microservices architecture, Microservices are an architectural and organizational approach to software development where software is composed of small independent services that communicate over well-defined APIs.

Application Simulation Diagram
 First of all, the data used is random dummy data which will be created using the Springboot Scheduler on a scheduled basis every 10 seconds and sent to NodeJS. Next, the data that has been obtained will be sent directly to Kafka. The data will be saved into MongoDB as big data before finally being processed again by the Scheduler Report using Springboot as a data summary report and saved to PostgreSQL every 1 hour to be sent as a Telegram notification and API for the Web Dashboard.
 In this case, i use React x Vite as a Front End and Java Spring and Node JS (Express) as a Back End to implement this project. The next decription in link below: https://www.linkedin.com/posts/aan-adrian-khothibulumam-76b868195_springboot-reactjs-nodejs-activity-7180474081501233152-3lCS?utm_source=share&utm_medium=member_desktop
