const nicknames = document.querySelectorAll(".nickname");
const dmsvgs = document.querySelectorAll(".dm-svg");
const etcsvgs = document.querySelectorAll(".etc-svg");
const profiles = document.querySelectorAll(".profile");

window.onload = () => {
  //닉네임 이벤트 핸들러
  for (const label of nicknames) {
    //mouse enter/leave
    label.addEventListener("mouseenter", function () {
      this.parentElement.querySelector(".id").classList.add("id-show");
    });
    label.addEventListener("mouseleave", function () {
      this.parentElement.querySelector(".id").classList.remove("id-show");
    });
  }

  //dm 아이콘 이벤트 핸들러
  for (const svg of dmsvgs) {
    //mouse enter/leave
    svg.addEventListener("mouseenter", function () {
      this.style = "cursor : pointer;";
    });
    svg.addEventListener("mouseleave", function () {

    });

    //click
    svg.addEventListener("click", function () {
      // window.location = "";
    });
  }

  //etc 아이콘 이벤트 핸들러
  for (const svg of etcsvgs) {
    //mouse enter/leave
    svg.addEventListener("mouseenter", function () {
      this.style = "cursor : pointer;";
    });
    svg.addEventListener("mouseleave", function () {
      this.parentElement.parentElement.querySelector(".etc-menu").classList.remove("menu-show");
      this.parentElement.parentElement.querySelector(".etc-menu").addEventListener(
        "mouseleave", function () {
          this.parentElement.parentElement.querySelector(".etc-menu").classList.remove("menu-show");
        }
      )
    });

    //click
    svg.addEventListener("click", function () {
      this.parentElement.parentElement.querySelector(".etc-menu").classList.toggle("menu-show");
    });
  }

  //프사 이벤트 핸들러
  for (img of profiles) {
    //mouse enter/leave
    img.addEventListener("mouseenter", function () {
      this.style = "cursor : pointer;";
    });
    img.addEventListener("mouseleave", function () {

    });

    //click
    img.addEventListener("click", function () {

    });
  }

  //클리하면 나오는 메뉴들 해제하는 이벤트 핸들러
  document.querySelector("body").addEventListener("click", function (e) {
    console.log("e.target : ", e.target);
    if (!e.target.classList.contains("qq")) {
      console.log("여기 누르면 메뉴 닫음");
    }
  });


}
