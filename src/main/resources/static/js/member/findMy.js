const findIdBtn = document.querySelector("#findIdBtn");
const findPwdBtn = document.querySelector("#findPwdBtn");
const sendBtn = document.querySelector(".send-btn");
const form = document.querySelector("form");
// const nameInput = document.querySelector('#name');
const idInput = document.querySelector("#id");
const email = document.querySelector("#email");
const code = document.querySelector("#code");
const newPwd = document.querySelector("#newPwd");
const resetPwdBtn = document.querySelector("#resetPwdBtn");
let verified = false;
let verificationCode = "123";

window.onload = () => {
  //이메일 전송 버튼
  sendBtn.addEventListener("click", async function () {
    console.log("이메일 전송 버튼 클릭");

    if (validateEmail(email.value)) {
      console.log(email.value + " 로 이메일 전송 시작");
      document.querySelector(".modal-container").style.display = "flex";
      fetch("/member/sendEmail?email=" + email.value)
        .then((response) => response.text())
        .then((data) => {
          document.querySelector(".modal-container").style.display = "none";
          switch (data) {
            case "EmailNotFound":
              alert("해당 이메일로 가입된 회원이 존재하지 않습니다.");
              email.focus();
              break;
            case "MailException":
              alert(
                "이메일 전송 과정중 오류가 발생했습니다 잠시 후에 다시 시도해주세요."
              );
              break;
            case "MessagingException":
              alert("이메일 형식 오류(사실상 일어날 일 없음)");
              break;
            default:
              document.querySelector(".modal-container").style.display = "none";
              verificationCode = data;
              console.log("verificationCode : " + verificationCode);
              timer();
              break;
          }
        });
    } else {
      alert("올바른 형식의 이메일 주소를 입력해주세요.");
      email.focus();
    }
  });

  //이메일 형식 검증
  const validateEmail = (str) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(str);
  };

  //타이머
  const timer = () => {
    sendBtn.disabled = true;
    let sec = 60;
    const interval = setInterval(() => {
      if (sec > 0) {
        sendBtn.innerText = sec;
        sendBtn.classList.add("send-btn-disabled");
        sendBtn.disabled = true;
        sec--;
      } else {
        clearInterval(interval);
        sendBtn.innerText = "send";
        sendBtn.classList.remove("send-btn-disabled");
        sendBtn.disabled = false;
      }
    }, 1000);
  };

  //인증번호 확인 함수
  code.addEventListener("keyup", function () {
    const msg = document.querySelector(".verification-msg");
    if (code.value == "") {
      msg.classList.remove("msg-yes");
      msg.classList.remove("msg-no");
      msg.innerText = "";
      verified = false;
      console.log("input none, verified : " + verified);
    } else {
      if (code.value != verificationCode) {
        msg.classList.remove("msg-yes");
        msg.classList.add("msg-no");
        msg.innerText = "인증번호가 일치 하지 않습니다.";
        verified = false;
        console.log("input wrong, verified : " + verified);
      } else {
        msg.classList.remove("msg-no");
        msg.classList.add("msg-yes");
        msg.innerText = "인증번호가 일치 합니다.";
        verified = true;
        console.log("input correct, verified : " + verified);
      }
    }
  });

  //아이디 찾기 버튼
  if (findIdBtn) {
    findIdBtn.addEventListener("click", async function (e) {
      e.preventDefault();
      if (email.value == "" || code.value == "") {
        alert("비어있는 항목이 있습니다.");
      } else {
        if (verified) {
          const response = await fetch("/member/findId?email=" + email.value);
          const data = await response.text();
          alert("아이디 찾기에 성공했습니다 : " + data);
        } else {
          alert("인증번호가 일치하지 않습니다.");
        }
      }
    });
  }

  //비밀번호 찾기 버튼
  if (findPwdBtn) {
    findPwdBtn.addEventListener("click", async function (e) {
      e.preventDefault();
      if (idInput.value == "" || email.value == "" || code.value == "") {
        alert("비어있는 항목이 있습니다.");
      } else {
        if (verified) {
          console.log(document.querySelector(".reset-pwd"));
          document.querySelector(".reset-pwd").classList.add("reset-pwd-show");
          findPwdBtn.style =
            "overflow: hidden; height : 0; padding:0; margin:0; transition: all 0.3s ease;";
        } else {
          alert("인증번호가 일치하지 않습니다.");
        }
      }
    });
  }

  if (resetPwdBtn) {
    resetPwdBtn.addEventListener("click", async () => {
      console.log(verified);
      if (verified) {
        const formData = new FormData();
        formData.append("memberId", idInput.value);
        formData.append("memberEmail", email.value);
        formData.append("newPwd", newPwd.value);

        const response = await fetch("/member/resetPwd", {
          method: "POST",
          body: formData,
        });

        const data = await response.text();
        switch (data) {
          case "MemberNotFound":
            alert(
              "해당 이메일과 아이디를 가진 회원이 없습니다." +
                "아이디를 모르시는경우 아이디찾기를 먼저 진행해주세요."
            );
            break;
          case "success":
            alert("비밀번호 변경 성공");
            resetPwdBtn.innerText = "로그인";
            resetPwdBtn.classList.add("to-login");
            resetPwdBtn.onclick = () => (window.location.href = "/");
            break;
          default:
            console.log(data);
        }
      }
    });
  }
};
