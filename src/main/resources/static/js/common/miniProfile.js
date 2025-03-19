document.addEventListener("DOMContentLoaded", function () {
    let profileImg = document.querySelector("#profileImg");
    let profileName = document.querySelector("#profileName");
    let settingsBtn = document.querySelector("#settingsBtn");
    let closeBtn = document.querySelector(".close");

    // ✅ 프로필 이미지가 없을 경우 기본 이미지 설정
    if (profileImg) {
        let imgSrc = profileImg.getAttribute("src");
        if (!imgSrc || imgSrc === "null" || imgSrc === "") {
            profileImg.setAttribute("src", "/image/default-profile.png"); // 기본 이미지 경로
        }
        profileImg.addEventListener("click", openProfileModal);
    }

    if (profileName) profileName.addEventListener("click", openProfileModal);
    if (settingsBtn) settingsBtn.addEventListener("click", goToMyAccount);
    if (closeBtn) closeBtn.addEventListener("click", closeProfileModal);
});

// ✅ 미니 프로필 모달 열기
function openProfileModal() {
    let modal = document.getElementById("profileModal");
    if (modal) {
        modal.style.display = "block";
    }
}

// ✅ 미니 프로필 모달 닫기
function closeProfileModal() {
    let modal = document.getElementById("profileModal");
    if (modal) {
        modal.style.display = "none";
    }
}

// ✅ 설정 페이지 이동
function goToMyAccount() {
    window.location.href = "/prefs/myProfile";
}
