const inviteModal = document.querySelector(".invite-modal");
const ejectModal = document.querySelector(".eject-modal");
console.log("serverNo : ", serverNo);
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
      data.list.forEach((f) => {
        //f의 memberNo가 회원리스트에 없는거만 조회
        const li = document.createElement("li");
        li.className = "li-friends";

        const memberNoInput = document.createElement("input");
        memberNoInput.type = "hidden";
        memberNoInput.value = f.memberNo;
        li.appendChild(memberNoInput);

        const profileImgDiv = document.createElement("div");
        profileImgDiv.className = "profile-div";
        const profileImg = document.createElement("img");
        profileImg.src = "/profile-images/" + f.profileImage;
        profileImgDiv.append(profileImg);
        li.append(profileImgDiv);

        const nickname = document.createElement("label");
        nickname.innerText = f.memberNickname;
        li.append(nickname);

        const memberId = document.createElement("span");
        memberId.innerText = f.memberId;
        li.append(memberId);

        const inviteBtn = document.createElement("button");
        inviteBtn.innerText = "+";
        inviteBtn.className = "invite-button";
        inviteBtn.onclick = inviteMember;
        li.appendChild(inviteBtn);

        friendList.appendChild(li);
      });
    });
});

function inviteMember(e) {
  const memberNo =
    e.target.parentElement.querySelector("input[type=hidden]").value;
  console.log(memberNo, serverNo);
  if (confirm("이 서버에 초대하시겠습니까?")) {
    fetch("/server/serverMember", {
      method: "post",
      headers: { "content-type": "application/json;charset=UTF-8" },
      body: JSON.stringify({
        memberNo: memberNo,
        serverNo: serverNo,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data == 1) {
          alert("초대 완료");
          e.target.parentElement.remove();
        }
      })
      .catch((error) => {
        alert("관리자가 아닙니다.");
      });
  }
}

//회원 추방
const ejectModalBtn = document.querySelector(".eject-modal-button");
ejectModalBtn.addEventListener("click", function () {
  document.querySelector(".modal-container").style.display = "flex";
  ejectModal.style.display = "flex";
});

//모달
//모달 취소 버튼
const modalCloseBtn = document
  .querySelector(".modal-container")
  .querySelectorAll(".cancle-button")
  .forEach((btn) => {
    btn.addEventListener("click", function () {
      console.log("asf");
      document.querySelector(".modal-container").style.display = "none";
    });
  });
