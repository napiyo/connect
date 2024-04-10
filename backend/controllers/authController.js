const verificationModel = require("../models/verficationModel");
const ErrorHandler = require("../utils/errorHandler");
const asyncerror = require("../middlewares/catchAsyncError");
const sendOTP = require("../middlewares/otp");
const { updatePhoneNumber } = require("./contactsController");
const sendToken = require("../utils/jswtoken");
const jwt = require('jsonwebtoken')

// phone  number verify - send otp
exports.phoneVerification = asyncerror(async(req, res, next) => {
    // check last otp sent time. wait 60 sec for next otp, max 5 otp in a day.
    const { phoneNumber } = req.body;
    if (!phoneNumber) {
        return next(new ErrorHandler("Phone Number is required"), 400);
    }
    if (isNaN(phoneNumber) || phoneNumber.length != 10) {
        return next(new ErrorHandler("phone Number not valid", 400))
    }
    let retryCnt = 0;
    let lastOTPsentOn = 0;
    await verificationModel.findOne({ phoneNumber }).then((contactDetails) => {
        if (contactDetails) {
            retryCnt = contactDetails.otpRetryCntInAday;
            lastOTPsentOn = contactDetails.updatedAt;
        }
    })
    const currentDate = new Date();
    if (retryCnt > 4 && currentDate - lastOTPsentOn < 24 * 60 * 60 * 1000) {
        // sent 5 otp within 24hr
        let timeLeftTemp = 24 * 60 * 60 * 1000 - (currentDate - lastOTPsentOn);
        let timeLeft = { "hrs": Math.floor(timeLeftTemp / (60 * 60 * 1000)), "min": Math.floor((timeLeftTemp % (60 * 60 * 1000)) / (60 * 1000)), "sec": Math.floor((timeLeftTemp % (60 * 1000)) / (1000)) }
        let timeLeftStr = (timeLeft.hrs > 0) ? timeLeft.hrs + " hours" : (timeLeft.min > 0) ? timeLeft.min + " min" : timeLeft.sec + " seconds"
        return next(new ErrorHandler(`Max otp limit is reached, please wait ${timeLeftStr}, before sending new OTP`, 429));
    }
    // wait 60sec before sending new OTP
    if (currentDate - lastOTPsentOn < 60 * 1000) {
        let secondsLeft = 60 - Math.floor((currentDate - lastOTPsentOn) / 1000)
        return next(new ErrorHandler(`Please wait ${secondsLeft} sec. before sending new OTP`))
    }
    // reset retryCnt if lastOTP sent is over 24hrs
    if (currentDate - lastOTPsentOn > 24 * 60 * 60 * 1000) {
        retryCnt == 0;
    }
    // generate otp
    const otp = Math.floor(100000 + Math.random() * 900000);
    // incresae retry cnt
    retryCnt++;

    // update otp in db
    const data = await verificationModel.findOneAndUpdate({ phoneNumber }, {
        otp,
        otpRetryCntInAday: retryCnt
    }, {
        upsert: true,
        new: true
    });

    // send OTP sms
    await sendOTP(phoneNumber, otp)
    res.status(200).json({
        success: true,
        data: "OTP sent"
    });

});


exports.verifyOTP = asyncerror(async(req, res, next) => {
    const { phoneNumber, userOTP } = req.body;
    if (!(phoneNumber && userOTP)) {
        return next(new ErrorHandler("phoneNumber and userOTP is required", 400));
    }
    await verificationModel.findOne({ phoneNumber }).then((contact) => {
        if (contact) {
            const currentDate = new Date();
            // OTP valid till 10 min
            if (currentDate - contact.updatedAt > 10 * 60 * 1000) {
                return next(new ErrorHandler("OTP is expired, please try sending new OTP"));
            }
            if (contact.otp != userOTP) {
                return next(new ErrorHandler("Invalid OTP", 403));
            }
            sendToken(contact, 200, res);

        } else {
            return next(new ErrorHandler("OTP verification Failed, no OTP saved"))
        }
    })
});


exports.login = asyncerror(async(req, res, next) => {
    const { phoneNumber, name, fatherName, vans, village } = req.body;
    if (!(phoneNumber && name && fatherName && vans && village)) {
        return next(new ErrorHandler("All feilds are required", 400));
    }

    //get token from cookie
    const { token } = req.cookies;
    if (!token) {
        return next(new ErrorHandler("no token found", 401));
    }
    const tokenDetails = jwt.verify(token, process.env.JWT_SECRET);
    // console.log(cookiePhoneNumber);
    if (tokenDetails.phoneNumber != phoneNumber) {
        return next(new ErrorHandler("Tamppered  Cookie", 401));
    }
    // update contact details
    updatePhoneNumber(req, res, next);
});

exports.logout = asyncerror(async(req, res, next) => {
    const { token } = req.cookies;

    if (!token) {
        return next(new ErrorHandler("no user logged in to log out", 401));
    }

    res.cookie("token", null, {
        expires: new Date(Date.now()),
        httpOnly: true
    })
    res.status(200).json({
        success: true,
        data: "logged out successfully"
    })
})