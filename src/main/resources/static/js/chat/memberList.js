const manageModal = document.querySelector(".manage-modal");
const memberNoList = [];
document.querySelectorAll(".memberList-li").forEach((li) => {
  const n = li.querySelector("input[type=hidden]").value;
  memberNoList.push(n);
});

//모달 열기
const manageModalBtn = document.querySelector(".manage-modal-button");
const modalUl = document.querySelector(".modal-ul");
manageModalBtn.addEventListener("click", function () {
  document.querySelector(".modal-container").style.display = "flex";
  manageModal.style.display = "flex";

  const invite = manageModal.querySelectorAll("h1")[0];
  const eject = manageModal.querySelectorAll("h1")[1];

  //회원초대 h1 누르면
  invite.addEventListener("click", function () {
    fetch("/friend")
      .then((response) => response.json())
      .then((data) => {
        modalUl.parentElement.classList.remove("modal-ul-div-eject");
        modalUl.innerHTML = "";
        data.flist.forEach((f) => {
          const li = document.createElement("li");
          li.className = "li-friends";

          const memberNoInput = document.createElement("input");
          memberNoInput.type = "hidden";
          memberNoInput.value = f.memberNo;
          li.appendChild(memberNoInput);

          const profileImgDiv = document.createElement("div");
          profileImgDiv.className = "profile-div";
          const profileImg = document.createElement("img");
          profileImg.src =
            f.imageUrl == null ? "/image/member/no-profile.svg" : f.imageUrl;
          profileImgDiv.append(profileImg);
          li.append(profileImgDiv);

          const nickname = document.createElement("label");
          nickname.innerText = f.memberNickname;
          li.append(nickname);

          const memberId = document.createElement("span");
          memberId.innerText = f.memberId;
          li.append(memberId);

          if (!memberNoList.includes(f.memberNo + "")) {
            const inviteBtn = document.createElement("button");
            inviteBtn.innerText = "+";
            inviteBtn.className = "invite-button";
            inviteBtn.onclick = (e) => inviteMember(e, f.memberNo + "");
            li.appendChild(inviteBtn);
          } else {
            li.classList.add("li-friends-dark");
          }

          modalUl.appendChild(li);
        });
      });
  });

  //회원추방 h1 누르면
  eject.addEventListener("click", function () {
    modalUl.parentElement.classList.add("modal-ul-div-eject");
    modalUl.innerHTML = "";
    document.querySelectorAll(".memberList-li").forEach((mem) => {
      if (
        mem.querySelector("input[type=hidden]").value !=
        document.querySelector("input[name=loginMemberNo]").value
      ) {
        const li = document.createElement("li");
        li.className = "li-members";

        const memberNoInput = document.createElement("input");
        memberNoInput.type = "hidden";
        memberNoInput.value = mem.querySelectorAll("input[type=hidden]").value;
        li.appendChild(memberNoInput);

        const profileImgDiv = document.createElement("div");
        profileImgDiv.className = "profile-div";
        const profileImg = document.createElement("img");
        profileImg.src = mem.querySelector("img").src;
        profileImgDiv.appendChild(profileImg);
        li.appendChild(profileImgDiv);

        const nickname = document.createElement("label");
        nickname.innerText = mem.querySelector(".nickname").innerText;
        li.appendChild(nickname);

        const memberId = document.createElement("label");
        memberId.innerText = mem.querySelector(".memberId").innerText;
        li.appendChild(memberId);

        const ejectBtn = document.createElement("button");
        ejectBtn.innerText = "-";
        ejectBtn.className = "eject-button";
        ejectBtn.onclick = (e) => ejectMember(e, memberNoInput.value + "");
        li.appendChild(ejectBtn);

        modalUl.appendChild(li);
      }
    });
  });
});

//회원 초대
function inviteMember(e, memberNo) {
  if (confirm("이 서버에 초대하시겠습니까?")) {
    fetch("/server/serverMember", {
      method: "post",
      headers: { "content-type": "application/json;charset=UTF-8" },
      body: JSON.stringify({
        memberNo: memberNo,
        serverNo: serverNo,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          alert("관리자가 아닙니다.");
        }
        return response.json();
      })
      .then((data) => {
        console.log("data : ", data);
        if (data == 1) {
          const li = e.target.parentElement;
          li.classList.add("li-friends-dark");
          console.log(li);
          e.target.remove();
          alert("초대 완료");
        }
      });
  }
}

//회원 추방
function ejectMember(e, memberNo) {
  if (confirm("해당 회원을 정말로 서버에서 추방하시겠습니까?")) {
    fetch("/server/serverMember", {
      method: "delete",
      headers: { "content-type": "application/json; charset=UTF-8" },
      body: JSON.stringify({
        memberNo: memberNo,
        serverNo: serverNo,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          alert("관리자가 아닙니다.");
        } else {
          return response.json();
        }
      })
      .then((data) => {
        console.log(data);
        if (data == 1) {
          const li = e.target.parentElement;
          li.remove();
          alert("추방 완료");
        }
      });
  }
}

//모달 취소 버튼
const modalCloseBtn = document
  .querySelector(".modal-container")
  .querySelector(".cancle-button");

modalCloseBtn.addEventListener("click", function () {
  document.querySelector(".modal-container").style.display = "none";
  manageModal.style.display = "none";
});
