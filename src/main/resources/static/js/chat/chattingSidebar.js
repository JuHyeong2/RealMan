//채팅 사이드바중에 채널 관련된것들 모아놓음.
//chattingSidebar.html이 너무 길어져서 보기 불편해서.

//채널 추가
function addChannel() {
  const channelSeparator =
    this.parentElement.querySelector("input[type=hidden]").value;
  //1. li
  //2. div1(b(img), button, div(div3,div4))

  //li
  const li = document.createElement("li");
  li.classList.add("channel");

  //div
  const div = document.createElement("div");
  div.classList.add("channel-info-main");

  //채널 이름
  const b = document.createElement("b");

  //채널 아이콘 (말풍선, 스피커)
  const icon = document.createElement("img");
  icon.src = channelSeparator == "T" ? "/image/chat.png" : "/image/Voice.png";

  //채널 이름 입력할 input
  const input = document.createElement("input");
  input.type = "text";
  input.classList.add("channel-name-input");
  input.placeholder = "채널명 입력후 'enter'";

  b.appendChild(icon);
  b.appendChild(input);
  div.appendChild(b);
  li.appendChild(div);

  if (channelSeparator == "V") {
    document.querySelector(".voiceChat-ul").append(li);
  } else {
    document.querySelector(".textChat-ul").append(li);
  }

  input.focus();
  input.addEventListener("keydown", function (e) {
    if (e.key == "Enter") {
      const channelNames = [];
      document.querySelectorAll("b").forEach((b) => {
        channelNames.push(b.innerText);
      });
      const val = this.value;
      if (channelNames.includes(val)) {
        alert("이미 사용중인 채널명입니다.");
        this.value = "";
        this.focus;
      } else {
        fetch("/server/channel", {
          method: "post",
          headers: {
            "content-type": "application/json;charset=UTF-8",
          },
          body: JSON.stringify({
            serverNo: serverNo,
            channelName: val,
            channelSeparator: channelSeparator,
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            //채널 추가 성공하면 data == channelNo
            if (data != 0) {
              console.log("채널 추가 data : ", data);
              input.remove();

              b.append(val);
              b.addEventListener("click", function () {
                location.href = "/chat/main/" + serverNo + "/" + data;
              });

              const menuBtn = document.createElement("button");
              menuBtn.innerText = "...";
              menuBtn.classList.add("channel-menu-button");
              menuBtn.addEventListener("click", channelMenu);
              div.append(menuBtn);

              const menu = document.createElement("div");
              menu.classList.add("channel-menu");

              const menu1 = document.createElement("div");
              menu1.innerText = "채널명 변경";
              menu1.addEventListener("click", (e) => editChannel(e, data));
              menu.append(menu1);

              const menu2 = document.createElement("div");
              menu2.innerText = "채널 삭제";
              menu2.addEventListener("click", (e) => deleteChannel(e, data));
              menu.append(menu2);

              div.append(menu);
            }
          });
      }
    }
  });
}

//채널 삭제
function deleteChannel(e, channelNo) {
  console.log("channelNo : ", channelNo);
  const channelRow = e.target.parentElement.parentElement.parentElement;
  console.log("channelRow : ", channelRow);
  if (confirm("정말로 채널을 삭제하시겠습니까?")) {
    fetch("/server/channel", {
      method: "delete",
      headers: { "content-type": "application/json;chatset=UTF-8" },
      body: JSON.stringify({
        channelNo: channelNo,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        if (data == 1) {
          alert("채널이 삭제되었습니다.");
          channelRow.remove();
        }
      });
  }
}

//채널 수정
function editChannel(e, channelNo) {
  const channelRow = e.target.parentElement.parentElement.parentElement;
  console.log(channelrow);
  const input = document.createElement("input");
  input.type = Text;
  input.classList = "new-channel-name";
  input.value = channelRow.querySelector("b").innerText;
  channelRow.append(input);
  input.addEventListener("keydown", function (e) {
    if (e.key == "Enter") {
      if (confirm("정말로 이름을 변경하시겠습니까?")) {
        const newName = input.value;
        fetch("/server/channel", {
          method: "put",
          headers: { "content-type": "application/json;chatset=UTF-8" },
          body: JSON.stringify({
            channelNo: channelNo,
            newName: newName,
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            if (data == 1) {
              input.remove();
              const html = channelRow.querySelector("b").innerHTML;
              channelRow.querySelector("b").innerHTML =
                html.substring(0, html.lastIndexOf(">") + 1) + newName;
            }
          });
      }
    }
  });
}

// //""..."버튼 누르면 메뉴 나오게
function channelMenu(e) {
  const menu = e.target.parentElement.querySelector(".channel-menu");
  if (
    !menu.classList.contains("channel-menu-show") &&
    document.querySelector(".channel-menu-show")
  ) {
    document
      .querySelector(".channel-menu-show")
      .classList.remove("channel-menu-show");
  }
  menu.classList.toggle("channel-menu-show");
}

//drop-down
function voiceDropdown() {
  document.querySelector(".voiceChat-ul").classList.toggle("channel-ul-hide");
}
function textDropdown() {
  document.querySelector(".textChat-ul").classList.toggle("channel-ul-hide");
}
