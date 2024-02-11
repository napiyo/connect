const { phoneVerification, verfiyOTP, loggin, logout } = require("../controllers/authController");
const { isAuthenticated } = require("../utils/roles");

const authRouter = require("express").Router();

authRouter.post('/send-otp',phoneVerification);
authRouter.post('/verify-otp',verfiyOTP);
authRouter.post('/loggin',loggin);
authRouter.post('/logout',logout);



module.exports = authRouter;