const ErrorHandler = require("../utils/errorHandler")


module.exports = (err,req,res,next) =>{
    err.statusCode = err.statusCode || 500;
    err.message = err.message || "Internal Server Error"
    if(err.name ==  "MongooseError" || err.name == "MongoServerSelectionError")
    {
        console.log("[MongooseErr]",err.message);
        err.message = "OPPS.. Server down, try after some time"
    }
    res.status(err.statusCode).json({
        success:false,
        data:err.message,
    })
}