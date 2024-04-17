const mongoose = require('mongoose');
const { allowedVillages } = require('../controllers/contactsController');


const contactSchema = new mongoose.Schema({
    name: {
        type: String,
        required:  [true, 'Full Name is required'],
    },
    fatherName:{
        type:String,
        required:  [true, 'father Name is required.'],
    },
    vans:{
        type: String,
        required:  [true, 'Vans is required.'],
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
        max:9999999999,
        unique:true
    },
    proMember:{
        type:Boolean,
        default:false,
    },
    appUser:{
        type:Boolean,
        default:false,
    },
    role:{
        type:String,
        default:"user"
    }

});

const ContactModel = mongoose.model('Contacts', contactSchema);
// ContactModel.createIndexes();

module.exports = ContactModel;