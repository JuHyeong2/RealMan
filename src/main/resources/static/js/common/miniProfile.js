function openMiniProfile(image, nickname, userId, isMyProfile) {
    const modal = document.getElementById("miniProfileModal"); // ✅ ID로 변경
    if (!modal) {
        console.error("❌ [Error] 미니 프로필 모달 요소가 존재하지 않음");
        return;
    }

    const profileImage = document.getElementById("miniProfileImage");
    const profileNickname = document.getElementById("miniProfileNickname");
    const profileId = document.getElementById("miniProfileId");
    const profileActions = document.getElementById("miniProfileActions");

    if (!profileImage || !profileNickname || !profileId) {
        console.error("❌ [Error] 미니 프로필 요소가 로드되지 않음");
        return;
    }

    profileImage.src = image;
    profileNickname.textContent = nickname;
    profileId.textContent = userId;

    if (isMyProfile) {
        profileActions.style.display = "flex";
    } else {
        profileActions.style.display = "none";
    }

    modal.classList.add("active"); // ✅ `display: flex;` 적용
}

// ✅ 모달 닫기 기능
function closeMiniProfileModal() {
    document.getElementById("miniProfileModal").classList.remove("active");
}
