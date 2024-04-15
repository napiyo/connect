const asyncerror = require("../middlewares/catchAsyncError");
const EventsModel = require("../models/EventsModel");
const ErrorHandler = require("../utils/errorHandler")

exports.addEvent = asyncerror(async(req, res, next) => {
    
    const { name,type, heading, description, village, phoneNumber,date,month,priority=0, brochure="",insta=""} = req.body;
    // if (!(type && heading && description && village && phoneNumber && date && month )) {
    //     return next(new ErrorHandler("missing some fields, cant add new event", 400));
    // }
    await EventsModel.create({ name,type, heading, description, village, phoneNumber,date,month,priority, brochure,insta })
    .then((newEvent)=>{
            res.status(200).json({
                success: true,
                data: newEvent
            });
        })
})
exports.getEvents = asyncerror(async(req, res, next) => {
    
    const { village="",page=0} = req.query;
    const itemsPerPage = process.env.SEARCH_RESULTS_PER_PAGE || 50;
    let query = {}
    if(village)
    {
        query = {village}
    }
    await EventsModel.find(query).limit(itemsPerPage).skip(page*itemsPerPage)
    .then((eventList)=>{
            res.status(200).json({
                success: true,
                data: eventList
            });
        })
})

exports.deleteEvent = asyncerror(async(req, res, next) => {
    const { id } = req.body;
    if (!id) {
        return next(new ErrorHandler("id is required to delete Event", 400));
    }
    await EventsModel.deleteOne({ _id:Object(id) })
        .then(result => {
            if (result.deletedCount === 1) {
                res.status(200).json({
                    success: true,
                    data: "Event deleted successfully"
                });
            } else {
                res.status(404).json({
                    success: false,
                    data: "id not found"
                });
            }
        })
})
