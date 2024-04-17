const { getConfig, getVillages } = require("../controllers/configController");

const configRouter = require("express").Router();


configRouter.get('/getconfig',getConfig);
configRouter.get('/getVillages',getVillages);

module.exports = configRouter;