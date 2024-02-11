const asyncerror = require("./catchAsyncError");

const sendOTP = async(phoneNumber, otp)=>{

    console.log(`[SMS] sent OTP: ${otp} to ${phoneNumber}`);
    return new Promise((resolve, reject) => {
        // Simulate API call to send OTP
        setTimeout(() => {
          if (true) {
            resolve(otp);
          } else {
            reject(new Error('Failed to send OTP. Please try again.'));
          }
        }, 2000); // Simulate a 2-second delay for the API call
      });
    }
module.exports = sendOTP;