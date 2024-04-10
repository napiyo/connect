const contactRouter = require("express").Router();
const {addContact,getContact,updateContact, searchContact} = require('../controllers/contactsController');



// contactRouter.get('/get-all-contacts', getAllContacts);
contactRouter.get('/contact', getContact);
contactRouter.post('/contact',addContact);
contactRouter.put('/contact',updateContact);
contactRouter.get('/search-contacts', searchContact);

module.exports = contactRouter;