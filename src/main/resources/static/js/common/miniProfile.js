document.addEventListener("DOMContentLoaded", function () {
    // ✅ 서버 채널의 사용자 목록에서 닉네임 또는 프로필 사진 클릭 시 이벤트 추가
    document.querySelectorAll(".user-item").forEach(user => {
        user.addEventListener("click", function () {
            const profileImage = this.getAttribute("data-profile-img") || "/images/default-avatar.png";
            const nickname = this.getAttribute("data-nickname") || "사용자";
            const userId = this.getAttribute("data-user-id") || "0000";
            const isOnline = this.getAttribute("data-online") === "true";

            openMiniProfile(profileImage, nickname, userId, isOnline);
        });
    });
});

// ✅ 미니 프로필 모달 열기 함수
function openMiniProfile(imageSrc, nickname, userId, isOnline) {
    document.getElementById("miniProfileImage").src = imageSrc;
    document.getElementById("miniProfileNickname").innerHTML = 
        `<span id="miniProfileStatus" class="status-indicator ${isOnline ? 'online' : 'offline'}">●</span> ${nickname}`;
    document.getElementById("miniProfileId").textContent = `#${userId}`;
    
    // 모달 열기
    document.getElementById("miniProfileModal").style.display = "block";
}

// ✅ 모달 닫기
function closeMiniProfileModal() {
    document.getElementById("miniProfileModal").style.display = "none";
}
