const express = require('express');
const router = express.Router();
const authRouter = require('./authRouter.js');
const contactRouter = require("./contactsRouter.js");
const configRouter = require('./configRouter.js');
const { isAuthenticated } = require('../utils/roles.js');
const eventRouter = require('./eventsRouter.js');
const shopRouter = require('./shopsRouter.js');

router.use('/auth',authRouter);
// router.use('/contacts',contactRouter);
router.use('/contacts',contactRouter);
router.use('/configs',configRouter);
router.use('/events',isAuthenticated,eventRouter);
router.use('/shops',isAuthenticated,shopRouter);
module.exports = router;