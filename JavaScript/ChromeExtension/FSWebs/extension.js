let elements = []
//let label = document.getElementById("input-el")
//Buttons
//let btn = document.getElementById("input-btn")
let btnDel = document.getElementById("delete-btn")
let btnStav = document.getElementById("tab-btn")

let showElements = document.getElementById("ul-el")
let leadsFromLocalStorage = JSON.parse(localStorage.getItem("MyLeads"))

if (leadsFromLocalStorage) {
    elements = leadsFromLocalStorage
    renderElements()
}

/*btn.addEventListener("click", () => {
    elements.push(label.value)
    localStorage.setItem("MyLeads", JSON.stringify(elements))
    
    checkThings(elements)
    
    renderElements()
    label.value = ""
})*/

function checkThings(elements){
    if (confirm("Press a button!")) {
        elements[0] = prompt("Put your shit")
      }
    }

btnDel.addEventListener("click", () => {
    localStorage.clear()
    elements = []
    renderElements()
})
btnStav.addEventListener("click", () => {
    chrome.tabs.query({
        active: true,
        currentWindow: true
    }, function (tabs) {
        elements.push(tabs[0].url)
        localStorage.setItem("MyLeads", JSON.stringify(elements))
        renderElements()
    });
})



function renderElements() {
    let concatUrls = ""

    for (let i = 0; i < elements.length; i++) {
        let parts = elements[i].split("/")
        
          concatUrls += `
            <p><li>"${parts[5].replaceAll("_"," ")}", ${parts[4]}</li></p>
            `
    }
    showElements.innerHTML = concatUrls
}