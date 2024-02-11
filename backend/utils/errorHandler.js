class ErrorHandler extends Error{
    constructor(message,statusCode){
        super(message);
        this.statusCode = statusCode
        this.name = "INTERNAL_CUSTOM"
        Error.captureStackTrace(this,this.constructor)
       
    }
}
module.exports = ErrorHandler