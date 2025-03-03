const nicknames = document.querySelectorAll(".nickname");
const dmsvgs = document.querySelectorAll(".dm-svg");
const etcsvgs = document.querySelectorAll(".etc-svg");
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

  //dm 아이콘 이벤트 핸들러
  for (const svg of dmsvgs) {
    //mouse enter/leave
    svg.addEventListener("mouseenter", function (e) {
      console.log("mouse enter : " + this);
    });
    svg.addEventListener("mouseleave", function (e) {
      console.log("mouse leave : " + this);
    });

    //click
    svg.addEventListener("click", function (e) {
      console.log("click : " + this);
    });
  }

  //etc 아이콘 이벤트 핸들러
  for (const svg of etcsvgs) {
    //mouse enter/leave
    svg.addEventListener("mouseenter", function (e) {
      console.log("mouse enter : " + this);
    });
    svg.addEventListener("mouseleave", function (e) {
      console.log("mouse leave : " + this);
    });

    //click
    svg.addEventListener("click", function (e) {
      console.log("click : " + this);
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
