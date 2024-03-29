const { BrowserWindow, ipcMain } = require("electron");
const Task = require("./models/Task");

function createWindow() {
  const win = new BrowserWindow({
    width: 700,
    height: 700,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false,
    },
    autoHideMenuBar: true,
  });
  win.loadFile("app/index.html");
}

ipcMain.on("new-task", async (e, arg) => {
  const newTask = new Task(arg);
  const taskSaved = await newTask.save();
  e.reply("new-task-created", JSON.stringify(taskSaved));
});

ipcMain.on("get-tasks", async (e, arg) => {
  const tasks = await Task.find();
  e.reply("get-tasks", JSON.stringify(tasks));
});

ipcMain.on("delete-task", async (e, args) => {
  const taskDeleted = await Task.findByIdAndDelete(args);
  e.reply("delete-task-success", JSON.stringify(taskDeleted));
});

ipcMain.on("update-task", async (e, args) => {
  console.log(args);
  const updatedTask = await Task.findByIdAndUpdate(
    args.idTaskToUpdate,
    { 
      name: args.name, 
      price: args.price,
      storage: args.storage,
      url:args.url,
      date:args.date,
      controller:args.controller,
      compatibility:args.compatibility,
      ui:args.ui,
      audio:args.audio,
      subtitles:args.subtitles,
      xcloud:args.xcloud,
      psn:args.psn,
      bought:args.bought
    },
    { new: true }
  );
  e.reply("update-task-success", JSON.stringify(updatedTask));
});

module.exports = { createWindow };
