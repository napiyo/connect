// const ErrorHandler = require('../utils/errorHandler');
const jwtoken = require('jsonwebtoken');
const catchAsyncError = require('../middlewares/catchAsyncError');
const ErrorHandler = require('./errorHandler');
const ContactModel = require('../models/contactsModel');

exports.isAuthenticated = catchAsyncError(async(req,res,next)=>{
     //get token from cookie
     const {token} = req.cookies;
     if(!token){
         return next(new ErrorHandler("[token err] please login to use this resource",401));
     }

     // token found store user data in req

     // decode id from token
     let phoneNumber
     jwtoken.verify(token, process.env.JWT_SECRET, (err, decoded) => {
        if (err) {
            if (err.name === 'JsonWebTokenError') {
                return next(new ErrorHandler("Token Invalid",401))
            } else if (err.name === 'TokenExpiredError') {
                return next(new ErrorHandler("Token Expired",401))
            } else {
                return next(new ErrorHandler("Token verification failed",500))
            }
        }
        phoneNumber = decoded.phoneNumber;
    });
    //  const {phoneNumber} = jwtoken.verify(token,process.env.JWT_SECRET); 
     const user = await ContactModel.findOne({phoneNumber});
     if(!user){
         return next(new ErrorHandler("[token err] invalid token , please login again",401));
     }
     req.user = user;
     next()
})

exports.authorizedRoles = (...roles)=>{
    return (req,res,next)=>{
        if(!roles.includes(req.user.role)){
           return next(new ErrorHandler(`your Role ${req.user.role} is not authorized to access this resource`,403)
           )}
          
            next();
         
    };
};