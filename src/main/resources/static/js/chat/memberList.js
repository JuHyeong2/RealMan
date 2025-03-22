const inviteModal = document.querySelector(".invite-modal");
const memberNoList = [];
document.querySelectorAll(".memberList-li").forEach((li) => {
  const n = li.querySelector("input[type=hidden]").value;
  memberNoList.push(n);
});

//회원 초대
const inviteModalBtn = document.querySelector(".invite-modal-button");
const friendList = document.querySelector(".ul-friends");
inviteModalBtn.addEventListener("click", function () {
  document.querySelector(".modal-container").style.display = "flex";
  inviteModal.style.display = "flex";
  fetch("/friend")
    .then((response) => response.json())
    .then((data) => {
      friendList.innerHTML = "";
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
          li.classList.add("li-dark");
        }

        friendList.appendChild(li);
      });
    });
});

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
          li.classList.add("li-dark");
          console.log(li);
          e.target.remove();
          alert("초대 완료");
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
  inviteModal.style.display = "none";
});

//회원 추방
document.querySelectorAll(".eject-button").forEach((btn) => {
  btn.addEventListener("click", function (e) {
    const memberNo =
      this.parentElement.querySelector("input[type=hidden]").value;
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
            const li = this.parentElement;
            li.remove();
            alert("추방 완료");
          }
        });
    }
  });
});
