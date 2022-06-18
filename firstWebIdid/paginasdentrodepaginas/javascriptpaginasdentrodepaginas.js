window.onload = function () {
    document.querySelector("body").onkeypress = changeBackgroundColor;
};

function changeBackgroundColor() {
    let color1, color2, color3;
    color1 = Math.floor((Math.random() * 256));
    color2 = Math.floor((Math.random() * 256));
    color3 = Math.floor((Math.random() * 256));
    document.body.style.backgroundColor = "rgb(" + color1 + "," + color2 + "," + color3 + ")";
}            
            
            
            
            
            
            
            