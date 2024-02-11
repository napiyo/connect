const mongoose = require('mongoose');

const configSchema = new mongoose.Schema({
    latestAppVer: {
        type:Number
    },
    requiredVer:{
        type:Number
    },
    mode:{
        type:String,
    }
});

const ConfigModel = mongoose.model('Configs',configSchema);
// ConfigModel.createIndexes();

module.exports = ConfigModel;