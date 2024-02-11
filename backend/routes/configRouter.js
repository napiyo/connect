const { getConfig } = require("../controllers/configController");

const configRouter = require("express").Router();


configRouter.get('/getconfig',getConfig);

module.exports = configRouter;