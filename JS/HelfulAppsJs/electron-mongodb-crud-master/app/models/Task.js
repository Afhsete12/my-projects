const { model, Schema } = require("mongoose");

const newTaskSchema = new Schema({
  name: {
    type: String
  },
  price: {
    type: String
  },
  storage: {
    type: String
  },
  url: {
    type: String
  },
  date: {
    type: String
  },
  controller: {
    type: String
  },
  compatibility:{
    type: String
  },
  ui:{
    type: Boolean
  },
  audio:{
    type: Boolean
  },
  subtitles:{
    type: Boolean
  },
  xcloud:{
    type: Boolean
  },
  psn:{
    type: Boolean
  },
  bought:{
    type: Boolean
  }
});

module.exports = model("Game", newTaskSchema);
