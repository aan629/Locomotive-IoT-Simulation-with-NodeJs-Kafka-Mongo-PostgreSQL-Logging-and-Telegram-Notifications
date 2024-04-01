const { mongoose } = require('mongoose');

const schema = new mongoose.Schema({
    Code: String,
    Name: String,
    Dimension: String,
    Status: String,
    DateAndTime: String,
});

module.exports= schema