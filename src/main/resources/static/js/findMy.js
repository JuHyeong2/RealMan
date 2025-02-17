const findIdBtn = document.querySelector("#findIdBtn");
const findPwdBtn = document.querySelector("#findPwdBtn");
const sendBtn = document.querySelector(".send-btn");
const form = document.querySelector("form");
const name = document.querySelector("#name");
const id = document.querySelector("#id");
const email = document.querySelector('#email');
const code = document.querySelector('#code');

window.onload = () =>{
    //인증번호 확인 함수
    let verified = false;
    let verificationCode = '123';

    code.addEventListener('keyup', function(){
        const msg = document.querySelector(".verification-msg");
        if(code.value == ''){
            msg.classList.remove('msg-yes');
            msg.classList.remove('msg-no');
            msg.innerText='';
            verified = false;
            console.log('input none, verified : '+verified);
        }else{
            if(code.value != verificationCode){
                msg.classList.remove('msg-yes');
                msg.classList.add('msg-no');
                msg.innerText='인증번호가 일치 하지 않습니다.';
                verified = false;
                console.log('input wrong, verified : '+verified);
            }else{
                msg.classList.remove('msg-no');
                msg.classList.add('msg-yes');
                msg.innerText='인증번호가 일치 합니다.';
                verified = true;
                console.log('input correct, verified : '+verified);
            }
        }
    });

    //이메일 전송 버튼
    sendBtn.addEventListener('click', async function(e){
        e.preventDefault();
        if(email.value ==''){
            alert('이메일을 입력해주세요.');
        }else{
            if(confirm('입력하신 이메일 주소가"'+email.value+'"이 맞습니까?')){

            }else{
                email.value='';
                email.focus();
            }
        }
    });

    //아이디 찾기 버튼
    findBtn.addEventListener('click', async function(e){
        e.preventDefault();
        if(verified){
            const response = await fetch({
                
            });
        }  
    });

    //비밀번호 찾기 버튼
    findPwdBtn.addEventListener('click', async function(e){
        e.preventDefault();
        if(verified){
            const response = await fetch({
                
            });
        }  
    });
}  