const contactRouter = require("express").Router();
const {addContact,getContact,updateContact, searchContact} = require('../controllers/contactsController');
const { authorizedRoles, isAuthenticated } = require("../utils/roles");



// contactRouter.get('/get-all-contacts', getAllContacts);
contactRouter.get('/contact', getContact);
contactRouter.post('/contact',isAuthenticated,authorizedRoles("admin"),addContact);
contactRouter.put('/contact',isAuthenticated,authorizedRoles("admin"),updateContact);
contactRouter.get('/search-contacts',isAuthenticated,searchContact);

module.exports = contactRouter;