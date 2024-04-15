const mongoose = require('mongoose');
const { allowedVillages } = require('../controllers/contactsController');


const eventSchema = new mongoose.Schema({
    type: {
        type: String,
        required:  [true, 'Type of event is required']
    },
    name: {
        type: String,
        required:  [true, 'Name is required'],
    },
    heading:{
        type: String,
        required:  [true, 'heading is required.'],
    },
    description:{
        type:String,
        required:  [true, 'description is required.'],
    },
    village:{
        type: String,
        required:  [true, 'Village is required.'],
        enum: allowedVillages
    },
    phoneNumber: {
        type: Number,
        required:  [true, 'Phone Number is required.'],
        min :4444444444,
        max:9999999999
    },
    date:{
        type:Number,
        required: [true,'Date is required'],
        min :1,
        max:31,
    },
    month:{
        type:String,
        required: [true,'Month is required'],
    },
    priority:{
        type:Number,
        default:0,
    },
    brochure:{
        type:String,
        default:""
    },
    insta:{
        type:String,
        default:""
    },

});

const EventsModel = mongoose.model('Events', eventSchema);

module.exports = EventsModel;