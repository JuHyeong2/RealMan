const inviteModal = document.querySelector(".invite-modal");
const ejectModal = document.querySelector(".eject-modal");
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
        li.appendChild(inviteBtn);

        friendList.appendChild(li);
      });
    });
});

document.querySelectorAll(".invite-button").forEach((btn) => {
  btn.addEventListener("click", function () {
    const memberNo =
      this.parentElement.querySelector("input[type=hidden]").value;
    if (confirm("이 서버에 초대하시겠습니까?")) {
      fetch("/server/member", {
        method: "post",
        headers: { "context-type": "application/json;charset=UTF-8" },
        body: JSON.stringify({
          memberNo: memberNo,
          serverNo: 1,
        }),
      });
    }
  });
});

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
