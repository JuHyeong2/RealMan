window.onload = () => {
  //닉네임 이벤트 핸들러
  const nicknames = document.querySelectorAll(".nickname");
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
  const dmsvgs = document.querySelectorAll(".dm-svg");
  for (const svg of dmsvgs) {
    //click
    svg.addEventListener("click", function () {
      // window.location = "";
    });
  }

  //etc 아이콘 이벤트 핸들러
  const etcsvgs = document.querySelectorAll(".etc-svg");
  let friendrow = null;
  for (const svg of etcsvgs) {
    //click
    svg.addEventListener("click", function () {
      const etcMenu =
        this.parentElement.parentElement.querySelector(".etc-menu");
      //다른 etc메뉴 닫음 + 해당 etc 메뉴 엶
      if (
        !etcMenu.classList.contains("menu-show") &&
        document.querySelector(".menu-show")
      ) {
        document.querySelector(".menu-show").classList.remove("menu-show");
      }
      etcMenu.classList.toggle("menu-show");

      //etc메뉴 이벤트 핸들러
      friendrow = etcMenu.parentElement.parentElement.parentElement;
      const friendMemberNo = friendrow.querySelector(
        "form>input[type=hidden]"
      ).value;
      const [menu1, menu2] = etcMenu.querySelectorAll("div");
      menu1.onclick = function () {
        if (confirm("정말로 친구 삭제를 진행하시겠습니까?")) {
          console.log(friendMemberNo, "친구 삭제");
          fetch("/friend", {
            method: "delete",
            headers: { "content-type": "application/json; charset=UTF-8" },
            body: JSON.stringify({
              fnm: friendMemberNo,
            }),
          })
            .then((response) => response.json())
            .then((data) => {
              if (data == 1) {
                alert("친구삭제가 완료되었습니다.");
                location.reload();
              }
            });
        }
      };
      menu2.onclick = function () {
        if (confirm("정말로 회원을 차단하시겠습니까?")) {
          console.log(friendMemberNo, "친구 삭제 + 회원 차단");
        }
      };
    });
  }

  //친구 수락
  const checkSvgs = document.querySelectorAll(".check-svg");
  for (svg of checkSvgs) {
    svg.addEventListener("click", function () {
      const thisRow =
        this.parentElement.parentElement.parentElement.parentElement;
      const friendMemberNo = thisRow.querySelector(
        "form>input[type=hidden]"
      ).value;
      if (confirm("친구 요청을 수락하시겠습니까?")) {
        fetch("/friend", {
          method: "put",
          headers: { "content-type": "application/json; charset=UTF-8" },
          body: JSON.stringify({
            fnm: friendMemberNo,
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            if (data == 1) {
              alert("친구 요청을 수락하셨습니다.");
              location.reload();
            }
          });
      }
    });
  }

  //친구 거절
  const denySvgs = document.querySelectorAll(".deny-svg");
  for (svg of denySvgs) {
    svg.addEventListener("click", function () {});
  }

  //요청 취소
  const cancleSvgs = document.querySelectorAll(".cancle-svg");
  for (svg of cancleSvgs) {
    svg.addEventListener("click", function () {
      const thisRow =
        this.parentElement.parentElement.parentElement.parentElement;
      const friendMemberNo = thisRow.querySelector(
        "form>input[type=hidden]"
      ).value;
      if (confirm("보낸 요청을 취소하시겠습니까?")) {
        fetch("/friend", {
          method: "delete",
          headers: { "content-type": "application/json; charset=UTF-8" },
          body: JSON.stringify({
            fnm: friendMemberNo,
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            if (data == 1) {
              alert("친구 요청이 취소되었습니다.");
              location.reload();
            }
          });
      }
    });
  }

  //프사 이벤트 핸들러
  const profiles = document.querySelectorAll(".profile");
  for (img of profiles) {
    //click
    img.addEventListener("click", function () {});
  }

  //etc메뉴 안보이게하는 이벤트 핸들러
  document.querySelector("body").addEventListener("click", function (e) {
    if (
      !e.target.classList.contains("qq") &&
      document.querySelector(".menu-show")
    ) {
      document.querySelector(".menu-show").classList.remove("menu-show");
    }
  });
};
