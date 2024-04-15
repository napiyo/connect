const { addEvent, deleteEvent, getEvents } = require("../controllers/eventsController");

const eventRouter = require("express").Router();


eventRouter.get('/events',getEvents );
eventRouter.post('/events', addEvent);
eventRouter.delete('/events',deleteEvent);

module.exports = eventRouter;