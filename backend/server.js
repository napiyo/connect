const app = require('./app.js')

process.on("uncaughtException",(err) =>{
    console.log(`Error : ${err.message}`);
    console.log("shutting server down due to uncaughtException Error");
   
        process.exit(1);
   
});
const port = process.env.PORT;
app.listen(port, () => {
    console.log(`App is running... at http://localhost:${port}`);
});
process.on("unhandledRejection",(err) =>{
    console.log(`Error : ${err.message}`);
    console.log("shutting server down due to unhandledRejection Error");
    // app.close(()=>{
        process.exit(1);
    // });
});