const findIdBtn = document.querySelector('#findIdBtn');
const findPwdBtn = document.querySelector('#findPwdBtn');
const sendBtn = document.querySelector('.send-btn');
const form = document.querySelector('form');
const nameInput = document.querySelector('#name');
const idInput = document.querySelector('#id');
const email = document.querySelector('#email');
const code = document.querySelector('#code');
let verified = false;
let verificationCode = '123';
let randomPwd = '';

window.onload = () => {

    //이메일 전송 버튼
    let lastRequestTime = 0;

    sendBtn.addEventListener('click', async function () {
        console.log('이메일 전송 버튼 클릭');

        if (validateEmail(email.value)) {
            if (Date.now() - lastRequestTime > 60000) {
                console.log(email.value + ' 로 이메일 전송 시작');

                const response = await fetch('/member/sendEmail?email=' + email.value);
                const data = await response.text();

                console.log('data : ' + data);
                switch (data) {
                    case 'EmailNotFound':
                        alert('해당 이메일로 가입된 회원이 존재하지 않습니다.');
                        email.focus();
                        break;
                    case 'MailException':
                        alert('이메일 전송 과정중 오류가 발생했습니다 잠시 후에 다시 시도해주세요.');
                        break;
                    case 'MessagingException':
                        alert('이메일 형식 오류(사실상 일어날 일 없음)');
                        break;
                    default:
                        alert('이메일이 전송되었습니다.');
                        verificationCode = data;
                        console.log('verificationCode : ' + verificationCode);
                        coolDown();
                        break;
                }
            }
        } else {
            alert('올바른 형식의 이메일 주소를 입력해주세요.');
            email.focus();
        }
    });

    //이메일 형식 검증
    const validateEmail = str => {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(str);
    };

    //타이머
    const coolDown = () => {
        sendBtn.disabled = true;
        let sec = 60;
        setInterval(() => {
            sendBtn.innerText = sec;
            sendBtn.classList.add('send-btn-disabled');
            sendBtn.disabled = true;
            sec--;
        }, 1000)
        sendBtn.classList.remove('send-btn-disabled');
        sendBtn.disabled = false;
    };


    //인증번호 확인 함수
    code.addEventListener('keyup', function () {
        const msg = document.querySelector('.verification-msg');
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

    //아이디 찾기 버튼
    if (findIdBtn) {
        findIdBtn.addEventListener('click', async function (e) {
            e.preventDefault();
            if (nameInput.value == '' || email.value == '' || code.value == '') {
                alert('비어있는 항목이 있습니다.');
            } else {
                if (verified) {

                    alert('아이디 찾기에 성공했습니다 : ' + id);
                } else {
                    alert('인증번호가 일치하지 않습니다.');
                }
            }
        });
    }

    //비밀번호 찾기 버튼
    if (findPwdBtn) {
        findPwdBtn.addEventListener('click', async function (e) {
            e.preventDefault();
            if (idInput.value == '' || email.value == '' || code.value == '') {
                alert('비어있는 항목이 있습니다.');
            } else {
                if (verified) {


                    alert('비밀번호 찾기에 성공했습니다 : ' + randomPwd);
                } else {
                    alert('인증번호가 일치하지 않습니다.');
                }
            }
        });
    }

}  