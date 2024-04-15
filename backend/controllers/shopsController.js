const asyncerror = require("../middlewares/catchAsyncError");
const ShopModel = require("../models/shopsModel");
const ErrorHandler = require("../utils/errorHandler")

exports.addShop = asyncerror(async(req, res, next) => {
    const {name, shopName, description, address, village, phoneNumber,imageURL,priority } = req.body;
    // if (!(shopName && description && address && village && phoneNumber && imageURL)) {
    //     return next(new ErrorHandler("missing some fields, cant add new shop", 400));
    // }
    await ShopModel.create({name, shopName, description, address, village, phoneNumber,imageURL,priority })
    .then((newShop)=>{
            res.status(200).json({
                success: true,
                data: newShop
            });
        })
})
exports.getShops = asyncerror(async(req, res, next) => {
    
    const { village="",page=0} = req.query;
    const itemsPerPage = process.env.SEARCH_RESULTS_PER_PAGE || 50;
    let query = {}
    if(village)
    {
        query = {village}
    }
    await ShopModel.find(query).limit(itemsPerPage).skip(page*itemsPerPage)
    .then((shopList)=>{
            res.status(200).json({
                success: true,
                data: shopList
            });
        })
})
exports.deleteShop = asyncerror(async(req, res, next) => {
    const { id } = req.body;
    if (!id) {
        return next(new ErrorHandler("id is required to delete shop", 400));
    }
    await ShopModel.deleteOne({ _id:Object(id) })
        .then(result => {
            if (result.deletedCount === 1) {
                res.status(200).json({
                    success: true,
                    data: "Shop deleted successfully"
                });
            } else {
                res.status(404).json({
                    success: false,
                    data: "id not found"
                });
            }
        })
})
