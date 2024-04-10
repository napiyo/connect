const express = require('express');
const router = express.Router();
const authRouter = require('./authRouter.js');
const contactRouter = require("./contactsRouter.js");
const configRouter = require('./configRouter.js');
const { isAuthenticated } = require('../utils/roles.js');

router.use('/auth',authRouter);
router.use('/contacts',contactRouter);
// router.use('/contacts',isAuthenticated,contactRouter);
router.use('/configs',configRouter);
module.exports = router;