const express = require('express');
const routes = require('./routes/mainRoutes.js')
const error = require('./middlewares/error.js')
const mongoose = require('mongoose');
const verificationModel = require('./models/verficationModel.js');
const ContactModel = require('./models/contactsModel.js');
const ConfigModel = require('./models/configModel.js');
const cors = require('cors')
require('dotenv').config({ path: './config/.env' });
const cookieParser = require('cookie-parser')
const app = express();
app.use(cors())
mongoose.connect(process.env.MONGODB_URI, {
        useNewUrlParser: true,
        serverSelectionTimeoutMS: 5000
    })
    .then(() => {
        verificationModel.createIndexes();
        ContactModel.createIndexes();
        // ConfigModel.createIndexes();
        console.log('Connected to MongoDB');
    })
    .catch(err => {
        console.error('[Error] connecting to MongoDB: ', err);
});

app.use(express.json());
app.use(cookieParser());
app.use('/api', routes);
app.use((req, res, next) => {
    res.status(404).json({ success:false,error: 'Route not found' });
});
app.use(error);

module.exports = app;