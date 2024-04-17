const ConfigModel = require("../models/configModel");
const asyncerror = require("../middlewares/catchAsyncError");
const ErrorHandler = require("../utils/errorHandler");
const { allowedVillages } = require("./contactsController");

exports.getConfig = asyncerror(async(req,res,next)  => {
    await ConfigModel.findOne({}).then((configData) =>{
        if(configData) {
            res.status(200).json({
                success:true,
                data:configData
            })
        }
        else{
            return next(new ErrorHandler("No config found",404));
        }

    }).catch((err)=>{
        return next(new ErrorHandler(err));
    })
})

exports.getVillages = asyncerror(async(req,res,next)  => {
    res.status(200).json({
        success:true,
        data:allowedVillages
    })
})