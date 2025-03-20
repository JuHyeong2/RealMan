document.addEventListener("DOMContentLoaded", function () {
    function openMiniProfile(image, nickname, userId, isMyProfile) {
        const modal = document.getElementById("miniProfileModal"); 
        if (!modal) {
            console.error("❌ [Error] 미니 프로필 모달 요소가 존재하지 않음");
            return;
        }

        // ✅ 요소가 없으면 경고 메시지 출력 후 함수 실행을 막지 않도록 수정
        const profileImage = document.getElementById("miniProfileImage") || console.warn("⚠️ [경고] profileImage 요소가 없음");
        const profileNickname = document.getElementById("miniProfileNickname") || console.warn("⚠️ [경고] profileNickname 요소가 없음");
        const profileId = document.getElementById("miniProfileId") || console.warn("⚠️ [경고] profileId 요소가 없음");
        const profileActions = document.getElementById("miniProfileActions") || console.warn("⚠️ [경고] profileActions 요소가 없음");
        const statusElement = document.getElementById("miniProfileStatus") || console.warn("⚠️ [경고] statusElement 요소가 없음");

        profileImage.src = image;
        profileNickname.textContent = nickname ? nickname : "알 수 없는 사용자";
        profileId.textContent = userId ? userId : "#0000"; 

        if (isMyProfile && profileActions) {
            profileActions.style.display = "flex";
        } else if (profileActions) {
            profileActions.style.display = "none";
        }

        modal.style.display = "block"; 
    }

    // ✅ `goToMyAccount()` 오류 방지
    function goToMyAccount() {
        window.location.href = "/prefs/myProfile";
    }

    // ✅ `toggleStatusDropdown()` 오류 방지
    function toggleStatusDropdown() {
        const dropdown = document.getElementById("statusDropdown");
        if (!dropdown) {
            console.warn("⚠️ [경고] statusDropdown 요소가 없음");
            return;
        }
        dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
    }

    // ✅ `updateStatus()` 오류 방지 및 추가 기능 개선
    function updateStatus(status) {
        const statusElement = document.getElementById("miniProfileStatus");
        const statusDropdown = document.getElementById("statusDropdown");

        if (!statusElement) {
            console.warn("⚠️ [경고] statusElement 요소가 없음");
            return;
        }

        if (status === "online") {
            statusElement.classList.remove("offline");
            statusElement.classList.add("online");
            statusElement.textContent = " ●";
        } else if (status === "offline") {
            statusElement.classList.remove("online");
            statusElement.classList.add("offline");
            statusElement.textContent = " ●";
        }

        if (statusDropdown) {
            statusDropdown.style.display = "none";
        }
    }

    // ✅ 전역 함수 등록
    window.openMiniProfile = openMiniProfile;
    window.closeMiniProfileModal = function() {
        const modal = document.getElementById("miniProfileModal");
        if (modal) modal.style.display = "none";
    };
    window.goToMyAccount = goToMyAccount;
    window.toggleStatusDropdown = toggleStatusDropdown;
    window.updateStatus = updateStatus;
});
