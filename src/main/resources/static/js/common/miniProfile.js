document.addEventListener("DOMContentLoaded", function () {
    // ✅ 프로필 이미지 클릭 시 미니 프로필 모달 열기
    let profileImg = document.querySelector("#profileImg");
    let profileName = document.querySelector("#profileName");
    let settingsBtn = document.querySelector("#settingsBtn");
    let closeBtn = document.querySelector(".close");

    if (profileImg) profileImg.addEventListener("click", openProfileModal);
    if (profileName) profileName.addEventListener("click", openProfileModal);
    if (settingsBtn) settingsBtn.addEventListener("click", goToMyAccount);
    if (closeBtn) closeBtn.addEventListener("click", closeProfileModal);
});

// ✅ 미니 프로필 모달 열기
function openProfileModal() {
    document.getElementById("profileModal").style.display = "block";
}

// ✅ 미니 프로필 모달 닫기
function closeProfileModal() {
    document.getElementById("profileModal").style.display = "none";
}

// ✅ 설정 페이지 이동
function goToMyAccount() {
    window.location.href = "/prefs/myProfile";
}

// ✅ 상태 변경 기능 (온라인, 오프라인만)
function changeStatus(status) {
    const statusIndicator = document.getElementById("statusIndicator");

    if (status === "online") {
        statusIndicator.className = "status-online";
    } else {
        statusIndicator.className = "status-offline";
    }
}
