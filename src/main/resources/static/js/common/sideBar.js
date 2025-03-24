$(document).ready(function () {
    initSidebar(); // 시작 시 전체 초기화
});

function initSidebar() {
    loadServerList();
    setupServerModalHandlers();
    createServer();
}

// 서버 리스트 비동기 로딩
function loadServerList() {
    $.ajax({
        url: "/server/list",
        method: "GET",
        success: renderServerList,
        error: () => alert("서버 리스트 불러오기 실패")
    });
}

function renderServerList(list) {
    const container = $("#dynamic-server-list");
    container.empty();

    list.forEach(server => {
        const imgtag = document.createElement("img");
        imgtag.src= server.IMG_RENAME;
        const imgTag = server.IMG_RENAME
            ? `<img src="${server.IMG_RENAME}" width="50" height="50" class="rounded-circle" />`
            : `<span>${server.SERVER_NAME}</span>`;

        const html = `
            <div class="mb-2">
                <div class="server-btn bg-light rounded-circle mx-auto"
                     style="width: 50px; height: 50px; cursor: pointer;">
                    ${imgTag}
                </div>
                <input type="hidden" value="${server.SERVER_NO}">
            </div>
        `;
        container.append(html);
    });

    $(".server-btn").on("click", handleServerClick);
}

function handleServerClick() {
    const serverNo = $(this).parent().find("input").val();
    $.ajax({
        url: "/chat/selectSmallestChatNo",
        method: "POST",
        data: { serverNo },
        success: chatNo => {
            if (chatNo != 0) {
                location.href = `/chat/main/${serverNo}/${chatNo}`;
            }
        }
    });
}

// 서버 추가 모달 동작
function setupServerModalHandlers() {
    const overlay = $("#modalOverlay");
    const modal = $("#addServerModal");

    $(".addServer").on("click", () => {
        overlay.show();
        modal.show();
        showModalStep(1);
    });

    overlay.on("click", () => {
        overlay.hide();
        modal.hide();
        resetModalInputs();
    });

    $(".next-step").on("click", () => {
        const name = $(".server-name-input").val();
        if (!name) {
            alert("서버 이름을 입력해주세요.");
            return;
        }
        showModalStep(2);
    });

    $(".prev-step").on("click", () => showModalStep(1));
}

function showModalStep(step) {
    if (step === 1) {
        $(".server-step-1").show();
        $(".server-step-2").hide();
    } else {
        $(".server-step-1").hide();
        $(".server-step-2").show();
    }
}

function resetModalInputs() {
    $(".server-name-input").val("");
    $(".server-img-input").val("");
}

// 서버 생성 동기 생성후 해당페이지로
function createServer() {
    $(".add-server-final").on("click", () => {
        const name = $(".server-name-input").val();
        const file = $(".server-img-input")[0].files[0];

        if (!name) {
            alert("서버 이름을 입력해주세요.");
            return;
        }

        // hidden form에 값 세팅
        $("#serverNameInput").val(name);
        if (file) {
            $("#serverImageInput")[0].files = $(".server-img-input")[0].files;
        }

        // 폼 제출 (동기 요청 → 서버에서 redirect)
        $("#addServerForm").submit();
    });
}