const findIdBtn = document.querySelector("#findIdBtn");
const findPwdBtn = document.querySelector("#findPwdBtn");
const sendBtn = document.querySelector(".send-btn");
const form = document.querySelector("form");
const nameInput = document.querySelector("#name");
const idInput = document.querySelector("#id");
const email = document.querySelector('#email');
const code = document.querySelector('#code');
let verified = false;
let verificationCode = '123';
let randomPwd = '';

window.onload = () => {
    //인증번호 확인 함수


    code.addEventListener('keyup', function () {
        const msg = document.querySelector(".verification-msg");
        if (code.value == '') {
            msg.classList.remove('msg-yes');
            msg.classList.remove('msg-no');
            msg.innerText = '';
            verified = false;
            console.log('input none, verified : ' + verified);
        } else {
            if (code.value != verificationCode) {
                msg.classList.remove('msg-yes');
                msg.classList.add('msg-no');
                msg.innerText = '인증번호가 일치 하지 않습니다.';
                verified = false;
                console.log('input wrong, verified : ' + verified);
            } else {
                msg.classList.remove('msg-no');
                msg.classList.add('msg-yes');
                msg.innerText = '인증번호가 일치 합니다.';
                verified = true;
                console.log('input correct, verified : ' + verified);
            }
        }
    });

    //이메일 전송 버튼
    sendBtn.addEventListener('click', async function (e) {
        e.preventDefault();
        if (email.value == '') {
            alert('이메일을 입력해주세요.');
        } else {
            if (confirm('입력하신 이메일 주소가"' + email.value + '"이 맞습니까?')) {
                console.log("메일 보내기 시작")
                const formData = new FormData();
                formData.append("email", email.value);

                const resopnse = await fetch('/member/sendEmail', {
                    method: 'POST',
                    body: formData
                });

                verificationCode = await response.text();
                alert('인증번호가 전송되었습니다.');
            } else {
                email.value = '';
                email.focus();
            }
        }
    });

    //아이디 찾기 버튼
    findIdBtn.addEventListener('click', async function (e) {
        e.preventDefault();
        if (nameInput.value == '' || email.value == '' || code.value == '') {
            alert('비어있는 항목이 있습니다.');
        } else {
            if (verified) {
                const formData = new FormData();
                formData.append('email', email.value)
                const response = await fetch('/member/findId', {
                    method: 'GET',
                    body: formData
                });

                const id = await response.text();

                alert("아이디 찾기에 성공했습니다 : " + id);
            } else {
                alert('인증번호가 일치하지 않습니다.');
            }
        }
    });

    //비밀번호 찾기 버튼
    findPwdBtn.addEventListener('click', async function (e) {
        e.preventDefault();
        if (idInput.value == '' || emailInput == '' || code.value == '') {
            alert('비어있는 항목이 있습니다.');
        } else {
            if (verified) {
                const formData = new FormData();
                formData.append("memberId", idInput.value);

                const response = await fetch('/member/getTempPwd', {
                    method: "POST",
                    body: formData
                });

                randomPwd = await response.text();
                alert("비밀번호 찾기에 성공했습니다 : " + randomPwd);
            } else {
                alert('인증번호가 일치하지 않습니다.');
            }
        }
    });

}  