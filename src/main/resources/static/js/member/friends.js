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
sLi.innerHTML = `<div class="friend-row slist">
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
                      class="icon add-friend-svg qq"
                      src="/image/friend/add-friend.svg"
                    />
                  </div>
                </div>
                <div class="etc-div">
                  <div class="svg-container">
                    <img class="icon etc-svg qq" src="/image/friend/more.svg" />
                  </div>
                  <div class="etc-menu">
                    <div class="qq">회원 차단</div>
                  </div>
                </div>
              </div>
            </div>`;

const flist = document.querySelector("#friend-list"); //친구 목록
const wlist = document.querySelector("#wait-list"); //대기중
const rlist = document.querySelector("#request-list"); //친구 요청
const slist = document.querySelector("#search-list");

document.addEventListener("DOMContentLoaded", function () {
  getFriendList();
});

//===================================================================
//친구 목록 가져오기
//===================================================================

const getFriendList = () => {
  fetch("/friend")
    .then((response) => response.json())
    .then((data) => {
      if (data != undefined) {
        console.log("data : ", data);

        //친구목록
        if (data.list != null) {
          for (let f of data.list) {
            const fli = fLi.cloneNode(true);
            fli.querySelector("input").value = f.memberNo;
            //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
            fli
              .querySelector(".profile")
              .setAttribute("src", "/image/friend/no-profile.svg");
            fli.querySelector(".nickname").innerText = f.memberNickname;
            fli.querySelector(".id").innerText = f.memberId;
            flist.append(fli);
          }
        } else {
          flist.innerText = "비어있음";
        }
        if (data.wlist != null) {
          for (let w of data.wlist) {
            const wli = wLi.cloneNode(true);
            wli.querySelector("input").value = w.memberNo;
            //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
            wli
              .querySelector(".profile")
              .setAttribute("src", "/image/friend/no-profile.svg");
            wli.querySelector(".nickname").innerText = w.memberNickname;
            wli.querySelector(".id").innerText = w.memberId;
            wlist.append(wli);
          }
        } else {
          wlist.innerText = "비어있음";
        }
        if (data.rlist != null) {
          for (let r of data.rlist) {
            const rli = rLi.cloneNode(true);
            rli.querySelector("input").value = r.memberNo;
            //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
            rli
              .querySelector(".profile")
              .setAttribute("src", "/image/friend/no-profile.svg");
            rli.querySelector(".nickname").innerText = r.memberNickname;
            rli.querySelector(".id").innerText = r.memberId;
            rlist.append(rli);
          }
        } else {
          rlist.innerText = "비어있음";
        }

        setupEventHandlers();
      }
    });
};

//===================================================================
//이벤트 리스너들
//===================================================================

function setupEventHandlers() {
  //======================필터 버튼======================

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
          wlist.classList.remove("ul-show");
          rlist.classList.remove("ul-show");
          slist.classList.remove("ul-show");
          searchRefreshButton.style.visibility = "visible";
          flist.classList.add("ul-show");
          break;
        case "대기중":
          rlist.classList.remove("ul-show");
          flist.classList.remove("ul-show");
          slist.classList.remove("ul-show");
          searchRefreshButton.style.visibility = "visible";
          wlist.classList.add("ul-show");
          break;
        case "친구 요청":
          wlist.classList.remove("ul-show");
          flist.classList.remove("ul-show");
          slist.classList.remove("ul-show");
          searchRefreshButton.style.visibility = "visible";
          rlist.classList.add("ul-show");
          break;
        case "친구 추가":
          wlist.classList.remove("ul-show");
          flist.classList.remove("ul-show");
          rlist.classList.remove("ul-show");
          searchRefreshButton.style.visibility = "hidden";
          slist.classList.add("ul-show");
          break;
      }
    });
  });

  //======================검색======================

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

  //"친구 추가" 선택하고 검색시에 전체 회원 목록에서 찾고,
  //아니면 검색 결과는 내 목록 안에서만 나오게.
  searchButton.addEventListener("click", function () {
    let searchStr = searchInput.value;
    if (searchInput.classList.contains("full-search")) {
      //회원 검색 fetch
      if (searchStr != "") {
        fetch("/member/find?search=" + searchStr)
          .then((response) => response.json())
          .then((data) => {
            console.log("data", data);
            slist.innerHTML = "";
            for (let s of data) {
              const sli = sLi.cloneNode(true);
              sli.querySelector("input").value = s.memberNo;
              //----(프사 없다면 기본 프사 넣는 로직 넣어야함)
              sli
                .querySelector(".profile")
                .setAttribute("src", "/image/friend/no-profile.svg");
              sli.querySelector(".nickname").innerText = s.memberNickname;
              sli.querySelector(".id").innerText = s.memberId;
              slist.append(sli);
            }
            //slist는 나중에 불러오는거라서 이벤트 리스너들 다시 적용
            setupEventHandlers();
          });
      } else {
        alert("검색어를 입력해주세요.");
        searchInput.focus();
      }
    } else {
      //기존 목록 안에서 검색
      let lists = [flist, wlist, rlist];
      lists.forEach((list) => {
        list.querySelectorAll("li").forEach((li) => {
          console.log(li);
          let idCheck = li.querySelector(".id").innerText.includes(searchStr);
          let nicknameCheck = li
            .querySelector(".nickname")
            .innerText.includes(searchStr);
          console.log(idCheck);
          console.log(nicknameCheck);
          if (!idCheck && !nicknameCheck) {
            li.style.display = "none";
          } else {
            li.style.display = "flex";
          }
        });
      });
    }
  });

  //전체보기
  const searchRefreshButton = document.querySelector("#search-refresh");
  searchRefreshButton.addEventListener("click", function () {
    searchInput.value = "";
    searchButton.click();
  });

  //======================모든 리스트 공통 ul======================
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

  //======================친구 목록 리스트 flist======================

  //dm 아이콘
  const dmsvgs = document.querySelectorAll(".dm-svg");
  for (const svg of dmsvgs) {
    svg.addEventListener("click", function () {
      // window.location = "";
    });
  }

  //****flist + slist 공통
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
      const friendMemberNo =
        friendrow.querySelector("input[type=hidden]").value;
      const [menu1, menu2] = etcMenu.querySelectorAll("div");
      //flist 에서 :
      if (friendrow.parentElement.id == "friend-list") {
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

        //slist 에서는 :
      } else {
        menu1.onclick = function () {
          if (confirm("정말로 회원을 차단하시겠습니까?")) {
            console.log(friendMemberNo, "친구 삭제 + 회원 차단");
          }
        };
      }
    });
  }

  //======================친구 요청 리스트 rlist======================
  //친구 수락
  const checkSvgs = document.querySelectorAll(".check-svg");
  for (svg of checkSvgs) {
    svg.addEventListener("click", function () {
      const thisRow =
        this.parentElement.parentElement.parentElement.parentElement;
      const friendMemberNo = thisRow.querySelector("input[type=hidden]").value;
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

  //======================대기중 리스트 wlist======================
  //요청 취소
  const cancleSvgs = document.querySelectorAll(".cancle-svg");
  for (svg of cancleSvgs) {
    svg.addEventListener("click", function () {
      const thisRow =
        this.parentElement.parentElement.parentElement.parentElement;
      const friendMemberNo = thisRow.querySelector("input[type=hidden]").value;
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

  //======================검색 결과 리스트 slist======================

  //요청 보내기
  const addFriendSvgs = document.querySelectorAll(".add-friend-svg");
  for (let svg of addFriendSvgs) {
    svg.addEventListener("click", function () {
      const thisRow =
        this.parentElement.parentElement.parentElement.parentElement;
      const friendMemberNo = thisRow.querySelector("input[type=hidden]").value;
      if (confirm("해당 회원에게 친구 요청을 보내시겠습니까?")) {
        fetch("/friend", {
          method: "post",
          headers: { "content-type": "application/json;charset=UTF-8" },
          body: JSON.stringify({
            fnm: friendMemberNo,
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            console.log("data", data);
            if (data == 1) {
              alert("친구 요청이 완료되었습니다.");
              location.reload();
            }
          });
      }
    });
  }
}
