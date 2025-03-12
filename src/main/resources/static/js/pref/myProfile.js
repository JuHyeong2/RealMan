window.onload = () => {
  // 별명 수정하기
  const nickNameModal = document.querySelector("#nickNameModal");
  document.querySelector("#nickNameBtn").addEventListener("click", () => {
    nickNameModal.style.display = "flex";
    const nickName = nickNameModal.querySelector("input[type=text").value;
    const pwd = nickNameModal.querySelector("input[type=password]").value;
    const completeBtn = nickNameModal.querySelector("#completeBtn");
    completeBtn.addEventListener("click", function () {
      editMemberInfo("member_nickname", nickName, pwd);
    });
  });

  // 별명 수정하기 취소
  document.querySelector("#cancelBtn").addEventListener("click", () => {
    nickNameModal.style.display = "none";
  });

  // 이메일 수정하기
  const emailModal1 = document.querySelector("#emailModal-1");
  const emailModal2 = document.querySelector("#emailModal-2");
  const emailModal3 = document.querySelector("#emailModal-3");
  document.querySelector("#emailBtn").addEventListener("click", () => {
    emailModal1.style.display = "flex";
  });

  // 이메일 수정하기 취소
  document.querySelector("#cancelBtn2").addEventListener("click", () => {
    emailModal1.style.display = "none";
  });

  // 이메일 수정하기2
  document.querySelector("#sendBtn").addEventListener("click", () => {
    emailModal2.style.display = "flex";
    emailModal1.style.display = "none";
  });

  // 이메일 수정하기3
  document.querySelector("#nextBtn").addEventListener("click", () => {
    emailModal3.style.display = "flex";
    emailModal2.style.display = "none";
  });

  // 이메일 수정하기 뒤로가기
  document.querySelector("#backBtn").addEventListener("click", () => {
    emailModal3.style.display = "none";
    emailModal2.style.display = "flex";
  });

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

  // 비밀번호 변경하기
  document.querySelector("#changePassword").addEventListener("click", () => {
    document.querySelector("#passwordModal").style.display = "flex";
  });

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
    .then((data) => {});
}
