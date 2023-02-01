let inputPhrase;
let btnConvert;
let content;
let options;
window.addEventListener("load", () => {
  initVars();
  btnConvert.addEventListener("click", () => {
    for (i = 0; i < options.length; i++) {
      if (options[i].checked) {
        switch (options[i].value) {
          case options[0].value:
            firstMethod();
            break;
          case options[1].value:
            secondMethod();
            break;
        }
      }
    }
  });
});

function initVars() {
  inputPhrase = document.getElementById("phrase");
  btnConvert = document.getElementById("btnConvert");
  content = document.getElementsByTagName("p")[0];
  options = document.querySelectorAll('input[type="radio"]');
}

function firstMethod() {
  let phrase = inputPhrase.value;

  let splitItem = phrase.split(" ");
  let concatNewPhrase = "";
  for (let i = 0; i < splitItem.length; i++) {
    if (i == 0) {
      concatNewPhrase += splitItem[i].charAt(0);
    }
    for (let x = 0; x < splitItem[i].length; x++) {
      if (!(i == 0 && x == 0)) {
        concatNewPhrase += splitItem[i].charAt(x).toLowerCase();
      }
    }
    concatNewPhrase += " ";
  }
  content.innerHTML = concatNewPhrase;
}
function secondMethod() {
  let phrase = inputPhrase.value;

  let splitItem = phrase.split(" ");
  let concatNewPhrase = "";
  for (let i = 0; i < splitItem.length; i++) {
    concatNewPhrase += splitItem[i].charAt(0);
    for (let x = 1; x < splitItem[i].length; x++) {
      concatNewPhrase += splitItem[i].charAt(x).toLowerCase();
    }
    concatNewPhrase += " ";
  }
  content.innerHTML = concatNewPhrase;
}
