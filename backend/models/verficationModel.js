const mongoose = require('mongoose');
const jwtoken = require("jsonwebtoken")
const verificationSchema = new mongoose.Schema({
    phoneNumber:{
        type: String,
        required :  [true, 'PhoneNumber is required.'],
        unique:true,
    },
    otp:{
        type: Number,
        required:  [true, 'OTP is required.'],
    },
    otpRetryCntInAday:{
        type:Number,
        min:0,
        max:5,
        default:0
    },
},{ timestamps: true });

verificationSchema.methods.getJWToken = function(){
    return  jwtoken.sign({phoneNumber:this.phoneNumber},process.env.JWT_SECRET,{
          expiresIn:process.env.JWT_EXPIRES_IN
      })
  }

const verificationModel = mongoose.model('Verfications',verificationSchema);
// verificationModel.createIndexes();

module.exports = verificationModel;