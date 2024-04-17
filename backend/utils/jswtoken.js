const jwtoken = require('jsonwebtoken')
// create token on auth and save in cookies
const sendToken = (user,statusCode,res)=>{
    const token = user.getJWToken();
    const options = {
        expires:new Date(Date.now() + (process.env.COOKIE_EXPIRE*24*60*60*1000)),
        httpOnly:true
    }
    // res.status(statusCode).cookie("token",token,options).json({
    //     success:true,
    //     data:"token sent as cookie"
    // });
    res.status(statusCode).json({
        success:true,
        data:token
    });
}

module.exports = sendToken;