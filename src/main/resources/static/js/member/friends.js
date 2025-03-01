const nicknames = document.querySelectorAll(".nickname");
const svgs = document.querySelectorAll(".svg");
const profiles = document.querySelectorAll(".profile");

window.onload = () => {
  //닉네임 이벤트 핸들러
  for (const label of nicknames) {
    //mouse enter/leave
    label.addEventListener("mouseenter", function () {
      console.log("mouse enter : " + this);
    });
    label.addEventListener("mouseleave", function () {
      console.log("mouse leave : " + this);
    });
  }

  //dm, etc 아이콘 이벤트 핸들러
  for (const svg of svgs) {
    //mouse enter/leave
    svg.addEventListener("mouseenter", function (e) {
      const type = svg.classList.contains("dm") ? "dm" : "etc";
      console.log("mouse enter : " + type);
    });
    svg.addEventListener("mouseleave", function (e) {
      const type = svg.classList.contains("dm") ? "dm" : "etc";
      console.log("mouse leave : " + type);
    });

    //click
    svg.addEventListener("click", function (e) {
      const type = svg.classList.contains("dm") ? "dm" : "etc";
      console.log("click : " + type);
    });
  }

  //프사 이벤트 핸들러
  for (img of profiles) {
    //mouse enter/leave
    img.addEventListener("mouseenter", function (e) {
      console.log("mouse enter : " + this);
    });
    img.addEventListener("mouseleave", function (e) {
      console.log("mouse leave : " + this);
    });

    //click
    img.addEventListener("click", function () {
      console.log("click : " + this);
    });
  }
};
