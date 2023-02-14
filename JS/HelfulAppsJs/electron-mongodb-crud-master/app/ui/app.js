const { ipcRenderer } = require("electron");
const taskForm = document.querySelector("#taskForm");
const gameName = document.querySelector("#name");
const gamePrice = document.querySelector("#price");
const gameStorage = document.querySelector("#storage");
const gameUrl = document.querySelector("#url");
const gameDate = document.querySelector("#date");
const gameController = document.querySelector("#controller");
const gameCompatibility = document.querySelector("#compatibility");
const gameUi = document.querySelector("#cbUi");
const gameAudio = document.querySelector("#cbTranslated");
const gameSubtitles = document.querySelector("#cbSubtitles");
const gameInCloud = document.querySelector("#cbCloud");
const gameInPsn = document.querySelector("#cbPsn");
const gameBought = document.querySelector("#cbBought");
const taskList = document.querySelector("#taskList");

let updateStatus = false;
let idTaskToUpdate = "";

function deleteTask(id) {
  const response = confirm("are you sure you want to delete it?");
  if (response) {
    ipcRenderer.send("delete-task", id);
  }
  return;
}

function editTask(id) {
  updateStatus = true;
  idTaskToUpdate = id;
  const task = tasks.find((task) => task._id === id);
  gameName.value = task.name;
  gamePrice.value = task.price;
  gameStorage.value = task.storage;
  gameUrl.value = task.url;
  gameDate.value = task.date;
  gameController.value = task.controller;
  gameCompatibility.value = task.compatibility;
  gameUi.checked = task.ui;
  gameAudio.checked = task.audio;
  gameSubtitles.checked = task.subtitles;
  gameInCloud.checked = task.xcloud;
  gameInPsn.checked = task.psn;
  gameBought.checked = task.bought;
}

function renderTasks(tasks) {
  taskList.innerHTML = "";
  tasks.map((t) => {
    taskList.innerHTML += `
          <li class="card">
            <p>
              Name: ${t.name}
            </p>
            <p>
              <img src='${t.url}' width="300" alt="">
            </p>
            <p>
              Price: ${t.price} €
            </p>
            <p>
              Game Storage: ${t.storage} GB
            </p>
            <p>
              Date Released : ${t.date}
            </p>
            <p>
            ${t.controller}
            </p>
            <p>
            ${t.compatibility}
            </p>
            <details>
            <summary>Language (SP)</summary>
              UI: ${t.ui ? "Yes" : "No"}</br>
              Audio: ${t.audio ? "Yes" : "No"}</br>
              Subtitles: ${t.subtitles ? "Yes" : "No"}</br>
            </details>
            </br>
            <details>
            <summary>Platform</summary>
              PSN: ${t.psn ? "Yes" : "No"}</br>
              Xcloud: ${t.xcloud ? "Yes" : "No"}</br>
            </details>
            <p>
              Bought?: ${t.bought ? "Yes" : "No"}
            </p>
            
            
            <button class="btn btn-danger" onclick="deleteTask('${t._id}')">
              🗑 Delete
            </button>
            <button class="btn btn-secondary" onclick="editTask('${t._id}')">
              ✎ Edit
            </button>
          </li>
        `;
  });
}

let tasks = [];

ipcRenderer.send("get-tasks");

taskForm.addEventListener("submit", async (e) => {
  e.preventDefault();

  const task = {
    name: gameName.value,
    price: gamePrice.value,
    storage: gameStorage.value,
    url: gameUrl.value,
    date: gameDate.value,
    controller: gameController.value,
    compatibility: gameCompatibility.value,
    ui: gameUi.checked,
    audio: gameAudio.checked,
    subtitles: gameSubtitles.checked,
    xcloud: gameInCloud.checked,
    psn: gameInPsn.checked,
    bought: gameBought.checked,
  };

  if (!updateStatus) {
    ipcRenderer.send("new-task", task);
  } else {
    ipcRenderer.send("update-task", { ...task, idTaskToUpdate });
  }

  taskForm.reset();
});

ipcRenderer.on("new-task-created", (e, arg) => {
  console.log(arg);
  const taskSaved = JSON.parse(arg);
  tasks.push(taskSaved);
  console.log(tasks);
  renderTasks(tasks);
  alert("Task Created Successfully");
  taskName.focus();
});

ipcRenderer.on("get-tasks", (e, args) => {
  const receivedTasks = JSON.parse(args);
  tasks = receivedTasks;
  renderTasks(tasks);
});

ipcRenderer.on("delete-task-success", (e, args) => {
  const deletedTask = JSON.parse(args);
  const newTasks = tasks.filter((t) => {
    return t._id !== deletedTask._id;
  });
  tasks = newTasks;
  renderTasks(tasks);
});

ipcRenderer.on("update-task-success", (e, args) => {
  updateStatus = false;
  const updatedTask = JSON.parse(args);
  tasks = tasks.map((t, i) => {
    if (t._id === updatedTask._id) {
      t.name = updatedTask.name;
      t.price = updatedTask.price;
      t.storage = updatedTask.storage;
      t.url = updatedTask.url;
      t.date = updatedTask.date;
      t.controller = updatedTask.controller;
      t.compatibility = updatedTask.compatibility;
      t.ui = updatedTask.ui;
      t.audio = updatedTask.audio;
      t.subtitles = updatedTask.subtitles;
      t.xcloud = updatedTask.xcloud;
      t.psn = updatedTask.psn;
      t.bought = updatedTask.bought;
    }
    return t;
  });
  renderTasks(tasks);
});
