const mongoose = require('mongoose');
const { allowedVillages } = require('../controllers/contactsController');


const shopsSchema = new mongoose.Schema({
    shopName: {
        type: String,
        required:  [true, 'Shop Name is required'],
        maxLength:20
    },
    name: {
        type: String,
        required:  [true, 'Name is required'],
    },
    description:{
        type:String,
        required:  [true, 'description is required.'],
    },
    address:{
        type: String,
        required:  [true, 'Address is required.'],
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
    imageURL:{
        type:String,
        required: [true,'Image is required, give image link'],
    },
    priority:{
        type:Number,
        default:0,
    }

});

const ShopModel = mongoose.model('Shops', shopsSchema);

module.exports = ShopModel;