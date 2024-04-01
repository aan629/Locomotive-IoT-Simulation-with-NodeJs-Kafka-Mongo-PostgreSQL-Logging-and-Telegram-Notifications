//Set up database connections to  MongoDB
const mongoose = require('mongoose');
const URL_MONGODB = "mongodb://127.0.0.1:27017/LocomotiveDB";

mongoose.connect(URL_MONGODB)
    .then(() => {
        console.log('Connect to MongoDB successfully');
    })
    .catch((err) => {
        console.error('Failed to connect MongoDB:', err);
    });