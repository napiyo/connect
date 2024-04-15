const { getShops, addShop, deleteShop } = require("../controllers/shopsController");

const shopRouter = require("express").Router();


shopRouter.get('/shop',getShops );
shopRouter.post('/shop', addShop);
shopRouter.delete('/shop',deleteShop);

module.exports = shopRouter;