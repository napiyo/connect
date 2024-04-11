const contactModel = require("../models/contactsModel");
const asyncerror = require("../middlewares/catchAsyncError");
const ErrorHandler = require("../utils/errorHandler")

exports.allowedVillages = ["kailash","amli","sanchore"]
exports.addContact = asyncerror(async(req, res, next) => {
    const { name, fatherName, vans, village, phoneNumber } = req.body;
    if (!(name && fatherName && vans && village && phoneNumber)) {
        return next(new ErrorHandler("missing some fields, cant add new contact", 400));
    }
    await contactModel.create({ name, fatherName, vans, village, phoneNumber })
    .then((newContact)=>{
            res.status(200).json({
                success: true,
                data: newContact
            });
        })
})


// exports.getAllContacts = asyncerror(async(req, res,next) => {

//     await contactModel.find({},(err,contacts)=>{
//         if(err){
//             return next(new ErrorHandler(err));
//         }
//         else{
//             res.status(200).json({
//                 success: true,
//                 obj:{contacts,length:contacts.length},
//             });
//         }
//     });
// })

exports.getContact = asyncerror(async(req, res, next) => {
    const {phoneNumber} = req.body;
    if(!phoneNumber)
    {
        return next(new ErrorHandler("Phone number is required",400))
    }
    if(isNaN(phoneNumber) || phoneNumber.length != 10){
        
        return next(new ErrorHandler("Phone number is not valid",400))
    }
    await contactModel.findOne({ phoneNumber }).then((contactDetails)=>{

        
        if (contactDetails) {
            res.status(200).json({
                success: true,
                data: contactDetails
            });
        }
        else{
            return next(new ErrorHandler("No contact found", 404))
        }
    })     
});

// exports.getContactByVillage = asyncerror(async(req, res, next) => {
//     const village = req.body.village;
//     if (!village) {
//         return next(new ErrorHandler("village feild is required", 400));
//     }
//     await contactModel.find({ village }).sort({name:1}).exec((err,contactDetails)=>{
//         if(err){
//             return next(new ErrorHandler(err));
//         }
//         if (contactDetails) {
//             res.status(200).json({
//                 success: true,
//                 obj:contactDetails
//             });
//         }
//         else{
//             return next(new ErrorHandler("No contact found", 404))
//         }
        
//     });
// })

exports.searchContact = asyncerror(async(req, res, next) => {
    const {name,village,page=0,startsWith,reverse} = req.query;
    let query = {};
    if(name && name.length != 0){
        query.name =  { $regex: name, $options: 'i' };
    }

    if (village && village.length != 0) {
        query.village = village;
    }
    if (startsWith && startsWith.length != 0) {
        query.name = { $regex: `^${startsWith}`, $options: 'i' }; // Find names starting with specified letter
    }
    // first page is page 0.
    const itemsPerPage = process.env.SEARCH_RESULTS_PER_PAGE || 50;

    
    let contacts = await contactModel.find(query).sort({name:(reverse)?-1:1}).limit(itemsPerPage).skip(page*itemsPerPage);
    res.status(200).json({
        success: true,
        data:contacts
    });
        
})


exports.updateContact = asyncerror(async(req, res, next) => {
    const { name, fatherName, vans, village, phoneNumber } = req.body;
    if (!(name && fatherName && vans && village && phoneNumber)) {
        return next(new ErrorHandler("missing some fields, cant add new contact", 400));
    }
    
    let contact = await contactModel.findOneAndUpdate({ phoneNumber },
        { $set: { name, fatherName, vans, village, phoneNumber, appUser: true } },
        { new: true, upsert: true, runValidators: true });
    
    res.status(200).json({
        success: true,
        data:contact
    });
});