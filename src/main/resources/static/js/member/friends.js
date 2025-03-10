let fLi = document.createElement("li");
fLi.innerHTML = `<div class="friend-row">
              <input type="hidden" />
              <div class="profile-div">
                <div class="svg-container">
                  <img class="profile" src="/image/friend/no-profile.svg" />
                </div>
              </div>
              <div class="nickname-div">
                <label class="nickname"></label>
                <label class="id"></label>
              </div>
              <div class="other-div">
                <div class="dm-div">
                  <div class="svg-container">
                    <img
                      class="icon dm-svg qq"
                      src="/image/friend/message.svg"
                    />
                  </div>
                </div>
                <div class="etc-div">
                  <div class="svg-container">
                    <img class="icon etc-svg qq" src="/image/friend/more.svg" />
                  </div>
                  <div class="etc-menu">
                    <div class="qq">친구 삭제</div>
                    <div class="qq">회원 차단</div>
                  </div>
                </div>
              </div>
            </div>`;
let wLi = document.createElement("li");
wLi.innerHTML = `<div class="friend-row wlist">
              <input type="hidden" />
              <div class="profile-div">
                <div class="svg-container">
                  <img class="profile" src="/image/friend/no-profile.svg" />
                </div>
              </div>
              <div class="nickname-div">
                <label class="nickname"></label>
                <label class="id"></label>
              </div>
              <div class="other-div">
                <div class="dm-div">
                  <div class="svg-container">
                    <img
                      class="icon dm-svg qq"
                      src="/image/friend/message.svg"
                    />
                  </div>
                </div>
                <div class="etc-div">
                  <div class="svg-container">
                    <img
                      class="icon cancle-svg qq"
                      src="/image/friend/cancle.svg"
                    />
                  </div>
                </div>
              </div>
            </div>`;
let rLi = document.createElement("li");
rLi.innerHTML = `<div class="friend-row rlist">
                <input type="hidden" />
              <div class="profile-div">
                <div class="svg-container">
                  <img class="profile" src="/image/friend/no-profile.svg" />
                </div>
              </div>
              <div class="nickname-div">
                <label class="nickname"></label>
                <label class="id"></label>
              </div>
              <div class="other-div">
                <div class="dm-div">
                  <div class="svg-container">
                    <img class="icon check-svg" src="/image/friend/check.svg" />
                  </div>
                </div>
                <div class="etc-div">
                  <div class="svg-container">
                    <img class="icon deny-svg" src="/image/friend/deny.svg" />
                  </div>
                </div>
              </div>
            </div>`;
let sLi = document.createElement("li");

const flist = document.querySelector("#friend-list"); //친구 목록
const wlist = document.querySelector("#wait-list"); //대기중
const rlist = document.querySelector("#request-list"); //친구 요청

document.addEventListener("DOMContentLoaded", function () {
  getFriendList();
});

const getFriendList = () => {
  fetch("/friend")
    .then((response) => response.json())
    .then((data) => {
      if (data != undefined) {
        console.log("data : ", data);
        //친구목록
        for (let f of data.list) {
          const fli = fLi.cloneNode(true);
          fli.querySelector("input").value = f.memberId;
          //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
          fli
            .querySelector(".profile")
            .setAttribute("src", "/image/friend/no-profile.svg");
          fli.querySelector(".nickname").innerText = f.memberNickname;
          fli.querySelector(".id").innerText = f.memberId;
          flist.append(fli);
        }
        for (let w of data.wlist) {
          const wli = wLi.cloneNode(true);
          wli.querySelector("input").value = w.memberId;
          //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
          wli
            .querySelector(".profile")
            .setAttribute("src", "/image/friend/no-profile.svg");
          wli.querySelector(".nickname").innerText = w.memberNickname;
          wli.querySelector(".id").innerText = w.memberId;
          wlist.append(wli);
        }
        for (let r of data.rlist) {
          const rli = rLi.cloneNode(true);
          rli.querySelector("input").value = r.memberId;
          //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
          rli
            .querySelector(".profile")
            .setAttribute("src", "/image/friend/no-profile.svg");
          rli.querySelector(".nickname").innerText = r.memberNickname;
          rli.querySelector(".id").innerText = r.memberId;
          rlist.append(rli);
        }

        setupEventHandlers();
      }
    });
};

function setupEventHandlers() {
  //====================필터 버튼
  const filterDiv = document.querySelector("#filter-div");
  filterDiv.querySelectorAll("button").forEach((filter) => {
    filter.addEventListener("click", function () {
      // 다른 버튼 선택되어 있다면 해제시키기
      if (filterDiv.querySelector(".selected")) {
        filterDiv.querySelector(".selected").classList.remove("selected");
      }
      this.classList.toggle("selected");

      //선택에 따라 리스트 보여주기
      switch (this.innerText) {
        case "모두":
          wlist.style.display = "none";
          rlist.style.display = "none";
          flist.style.display = "flex";
          break;
        case "대기중":
          rlist.style.display = "none";
          flist.style.display = "none";
          wlist.style.display = "flex";
          break;
        case "친구 요청":
          wlist.style.display = "none";
          flist.style.display = "none";
          rlist.style.display = "flex";
          break;
        case "친구 추가":
          wlist.style.display = "none";
          flist.style.display = "none";
          rlist.style.display = "none";
          slist.style.display = "flex";
          break;
      }
    });
  });

  //==================== 검색창
  const searchButton = document.querySelector("#search-button");
  const searchInput = searchButton.previousElementSibling;
  searchInput.addEventListener("focus", function () {
    const filterChecked = document.querySelector("#filter-div > .selected");
    if (filterChecked.innerText == "친구 추가") {
      searchInput.classList.add("full-search");
    } else {
      searchInput.classList.remove("full-search");
    }
  });

  //==================== 친구 목록 리스트
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

  //==================친구 요청 리스트
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

  //==================대기중 리스트
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
}
