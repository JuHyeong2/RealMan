window.openMiniProfile = function (image, nickname, userId, isMyProfile) {
    console.log("🔹 openMiniProfile 함수 호출됨");

    let modal = document.getElementById("miniProfileModal");
    let profileImage = document.getElementById("miniProfileImage");
    let profileNickname = document.getElementById("miniProfileNickname");
    let profileId = document.getElementById("miniProfileId");
    let profileActions = document.getElementById("miniProfileActions");
    let statusElement = document.getElementById("miniProfileStatus");

    let attempts = 0;
    const checkElements = setInterval(() => {
        if (!modal || !profileImage || !profileNickname || !profileId || !statusElement) {
            console.warn(`⚠️ [경고] 필수 요소가 없음. ${++attempts}번째 재시도...`);
            modal = document.getElementById("miniProfileModal");
            profileImage = document.getElementById("miniProfileImage");
            profileNickname = document.getElementById("miniProfileNickname");
            profileId = document.getElementById("miniProfileId");
            profileActions = document.getElementById("miniProfileActions");
            statusElement = document.getElementById("miniProfileStatus");
        } else {
            clearInterval(checkElements);
            console.log("✅ [성공] 모든 요소를 찾음!");
        }
        if (attempts >= 3) clearInterval(checkElements);
    }, 500);

    profileImage.src = image;
    profileNickname.textContent = nickname || "알 수 없는 사용자";
    profileId.textContent = userId ? "#" + userId : "#0000";

    if (isMyProfile && profileActions) {
        profileActions.style.display = "flex";
    } else if (profileActions) {
        profileActions.style.display = "none";
    }

    modal.style.display = "block";
};
