const user = document.querySelector(".name-user");
const exit = document.querySelector(".exit");
const tema = document.querySelector(".fa-circle-half-stroke");
const fondo = document.querySelector(".main-content");
const header = document.querySelector(".wrapper-header");

const res = localStorage.getItem("username");
console.log(res);
console.log(localStorage.getItem("token"));
user.textContent = res;

let isThemaDark = false;
tema.addEventListener("click", () => {
  if (!isThemaDark) {
    fondo.style.background = "black";
    header.style.color = "white";
    isThemaDark = true;
    return;
  }
  fondo.style.background = "white";
  header.style.color = "rgb(33, 33, 33)";
  isThemaDark = false;
});

exit.addEventListener("click", () => {
  logOut();
});

function logOut() {
  localStorage.removeItem("token");
  localStorage.removeItem("username");
  window.location.href = "./index.html";
}
