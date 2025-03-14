let verificationCode = null;

window.onload = () => {
  //모든 취소 버튼들
  document.querySelectorAll(".cancle-button").forEach((btn) => {
    btn.addEventListener("click", function () {
      document.querySelectorAll(".modal").forEach((modal) => {
        modal.querySelectorAll("input").forEach((input) => {
          input.value = "";
        });
        modal.style.display = "none";
      });
    });
  });
  //======================프사======================
  const profilePicture = document.querySelector(".profile-pic");
  const profilePicModal = document.querySelector("#profile-pic-modal");
  profilePicture.addEventListener("click", function () {
    profilePicModal.style.display = "flex";
    const [changeBtn, deleteBtn] = profilePicModal.querySelectorAll("button");
    changeBtn.addEventListener("click", function () {
      const input = document.createElement("input");
      input.setAttribute("type", "file");
      input.setAttribute("accept", "image/*");
      input.click();
      input.addEventListener("change", function () {
        const file = this.files[0];
        const formData = new FormData();
        formData.append("image", file);
        fetch("/member/profileImg", {
          method: "post",
          body: formData,
        })
          .then((response) => response.json())
          .then((data) => {
            if (data == true) {
              alert("변경 완료");
              location.reload();
            } else {
              console.log(data);
              alert("실패");
            }
          });
      });
    });
    deleteBtn.addEventListener("click", function () {});
  });

  //======================별명======================
  // 별명 수정하기
  const nickNameModal = document.querySelector("#nickNameModal");
  document.querySelector("#nickNameBtn").addEventListener("click", () => {
    nickNameModal.style.display = "flex";
    const completeBtn = nickNameModal.querySelector("#nick-complete");
    completeBtn.addEventListener("click", function () {
      const nickName = nickNameModal.querySelector("input[type=text]").value;
      const pwd = nickNameModal.querySelector("input[type=password]").value;
      editMemberInfo("member_nickname", nickName, pwd);
    });
  });

  //======================이메일======================
  // 이메일 수정하기
  const emailModal1 = document.querySelector("#emailModal-1");
  const emailModal2 = document.querySelector("#emailModal-2");
  const emailModal3 = document.querySelector("#emailModal-3");
  document.querySelector("#emailBtn").addEventListener("click", () => {
    emailModal1.style.display = "flex";
  });

  // 이메일 수정하기2
  document.querySelector("#clickP").addEventListener("click", function () {
    document.querySelector("#sendBtn").click();
  });
  document.querySelector("#sendBtn").addEventListener("click", () => {
    emailModal2.style.display = "flex";
    emailModal1.style.display = "none";
    document.querySelector("#nextBtn").style.visibility = "hidden";
    const email = document.querySelector(".email-span").innerText;
    fetch("/member/sendEmail?email=" + email)
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        switch (data) {
          case "MailException":
            alert(
              "이메일 전송 과정중 오류가 발생했습니다 잠시 후에 다시 시도해주세요."
            );
            break;
          case "MessagingException":
            alert("이메일 형식 오류(사실상 일어날 일 없음)");
            break;
          default:
            verificationCode = data;
        }
      });
  });

  //이메일인증
  document
    .querySelector("input[name=certification]")
    .addEventListener("change", function () {
      if (this.value == verificationCode) {
        document.querySelector("#nextBtn").style.visibility = "visible";
      }
    });

  // 이메일 수정하기3
  document.querySelector("#nextBtn").addEventListener("click", () => {
    emailModal3.style.display = "flex";
    emailModal2.style.display = "none";
    const completeBtn = document.querySelector("#email-complete");
    completeBtn.addEventListener("click", function () {
      const newEmail = emailModal3.querySelector("input[type=email]").value;
      const pwd = emailModal3.querySelector("input[type=password]").value;
      editMemberInfo("member_email", newEmail, pwd);
    });
  });

  //======================전화번호======================
  // 전화번호 수정하기
  const phoneModal1 = document.querySelector("#phoneModal-1");
  const phoneModal2 = document.querySelector("#phoneModal-2");

  document.querySelector("#phoneBtn").addEventListener("click", () => {
    phoneModal1.style.display = "flex";
  });

  // 전화번호 수정하기2
  document.querySelector("#sendPhoneBtn").addEventListener("click", () => {
    phoneModal2.style.display = "flex";
    phoneModal1.style.display = "none";
  });

  //======================비밀번호======================
  // 비밀번호 변경하기
  const passwordModal = document.querySelector("#passwordModal");
  document.querySelector("#changePassword").addEventListener("click", () => {
    passwordModal.style.display = "flex";
    const completeBtn = document.querySelector("#pwd-complete");
    completeBtn.style.visibility = "hidden";
    const newInput1 = passwordModal.querySelector("input[name=newPassword]");
    const newInput2 = passwordModal.querySelector("input[name=newPassword2]");
    newInput2.addEventListener("keyup", function () {
      if (newInput1.value == newInput2.value) {
        completeBtn.style.visibility = "visible";
      }
    });
    completeBtn.addEventListener("click", function () {
      const pwd = passwordModal.querySelector("input[name=password]").value;
      editMemberInfo("member_pwd", newInput2.value, pwd);
    });
  });

  //======================계정삭제======================
  // 계정 삭제하기
  document.querySelector("#deleteBtn").addEventListener("click", () => {
    document.querySelector("#deleteModal").style.display = "flex";
  });

  // 계정 삭제하기 -> 취소
  document.querySelector("#cancelDeleteBtn").addEventListener("click", () => {
    document.querySelector("#deleteModal").style.display = "none";
  });

  // input태그 최대길이 쳌쳌
  const inputs = document.querySelectorAll(".phoneInput");
  inputs.forEach((input, index) => {
    input.addEventListener("keyup", (e) => {
      console.log("keyup들어옴");
      console.log(e.target.value.length);
      console.log(e.target.value.maxLength);
      if (e.target.value.length === e.target.maxLength) {
        const nextInput = inputs[index + 1];
        if (nextInput) {
          nextInput.focus();
        } else {
          document.querySelector("#phoneModal-3").style.display = "flex";
          document.querySelector("#phoneModal-2").style.display = "none";
        }
      }
    });
  });
};

function editMemberInfo(col, val, pwd) {
  console.log("col", col, "val", val, "pwd", pwd);

  fetch("/member/edit", {
    method: "put",
    headers: { "content-type": "application/json;charset=UTF-8" },
    body: JSON.stringify({
      col: col,
      val: val,
      pwd: pwd,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.message) {
        alert(data.message);
      } else {
        if (data == 1) {
          alert("변경이 완료되었습니다.");
          location.reload();
        }
      }
    });
}
