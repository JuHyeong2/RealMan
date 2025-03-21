document.addEventListener("DOMContentLoaded", function () {
    console.log("✅ miniProfile.js 로드됨");

    // ✅ 요소 가져오기
    const profileImg = document.getElementById("profileImg");
    const profileName = document.getElementById("profileName");
    const settingsBtn = document.getElementById("settingsBtn");
    const editProfileBtn = document.getElementById("miniEditProfile");
    const closeModal = document.querySelector(".modal .close");
    const modal = document.getElementById("miniProfileModal");

    // ✅ 프로필 클릭 시 모달 열기
    function openMiniProfile(imageSrc, nickname, userId) {
        console.log("✅ openMiniProfile 실행됨", imageSrc, nickname, userId);

        document.getElementById("miniProfileImage").src = imageSrc;
        document.getElementById("miniProfileNickname").textContent = nickname;
        document.getElementById("miniProfileId").textContent = `#${userId}`;

        modal.style.display = "block";
        modal.style.opacity = "1";
        modal.style.visibility = "visible";
    }

    // ✅ 프로필 닫기 버튼
    function closeMiniProfileModal() {
        console.log("✅ closeMiniProfileModal 실행됨");

        modal.style.opacity = "0";
        modal.style.visibility = "hidden";

        setTimeout(() => {
            modal.style.display = "none";
        }, 300);
    }

    // ✅ 프로필 편집 페이지로 이동
    const goToMyAccount = () => {
        console.log("✅ 프로필 편집 페이지로 이동");
        location.href = '/prefs/myProfile';
    };

    // ✅ 프로필 클릭 이벤트 (중복 제거)
    function handleProfileClick() {
        console.log("✅ 프로필 클릭됨");

        const imageSrc = profileImg.src;
        const nickname = profileName.textContent;
        const userId = profileName.getAttribute("data-user-id") || "unknown";

        openMiniProfile(imageSrc, nickname, userId);
    }

    if (profileImg && profileName) {
        profileImg.addEventListener("click", handleProfileClick);
        profileName.addEventListener("click", handleProfileClick);
    } else {
        console.log("❌ 프로필 이미지 또는 닉네임 요소를 찾을 수 없음");
    }

    // ✅ 환경설정 버튼 및 프로필 편집 버튼 이벤트 등록
    document.querySelectorAll("#settingsBtn, #miniEditProfile").forEach(btn => {
        btn.addEventListener("click", goToMyAccount);
    });

    // ✅ 모달 닫기 버튼 이벤트 등록
    if (closeModal) {
        closeModal.addEventListener("click", closeMiniProfileModal);
    }
});
