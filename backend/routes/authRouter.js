const { phoneVerification, verifyOTP, login, logout } = require("../controllers/authController");

const authRouter = require("express").Router();

authRouter.post('/send-otp',phoneVerification);
authRouter.post('/verify-otp',verifyOTP);
authRouter.post('/login',login);
authRouter.post('/logout',logout);



module.exports = authRouter;