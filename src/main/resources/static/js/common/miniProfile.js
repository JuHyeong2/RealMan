document.addEventListener("DOMContentLoaded", function () {
  console.log("✅ miniProfile.js 로드됨");

  // ✅ 요소 정의
  const profileImg = document.getElementById("profileImg");
  const profileName = document.getElementById("profileName");
  const settingsBtn = document.getElementById("settingsBtn");
  const editProfileBtn = document.getElementById("miniEditProfile");
  const closeModal = document.querySelector(".modal .close");
  const modal = document.getElementById("miniProfileModal");

  // ✅ 모달 열기 함수
  function openMiniProfile(imageSrc, nickname, userId) {
    console.log("✅ openMiniProfile 실행됨", imageSrc, nickname, userId);

    document.getElementById("miniProfileImage").src = imageSrc;
    document.getElementById("miniProfileNickname").textContent = nickname;
    document.getElementById("miniProfileId").textContent = `#${userId}`;

    modal.style.display = "block";
    modal.style.opacity = "1";
    modal.style.visibility = "visible";
  }

  // ✅ 모달 닫기 함수
  function closeMiniProfileModal() {
    console.log("✅ closeMiniProfileModal 실행됨");
    modal.style.opacity = "0";
    modal.style.visibility = "hidden";

    setTimeout(() => {
      modal.style.display = "none";
    }, 300);
  }

  // ✅ 내 프로필 클릭 시
  function handleMyProfileClick() {
    const imageSrc = profileImg.getAttribute("src") || "/images/default-avatar.png";
    const nickname = profileName.textContent || "사용자";
    const userId = profileName.getAttribute("data-user-id") || "unknown";

    openMiniProfile(imageSrc, nickname, userId);
  }

  // ✅ 마이 계정 이동
  const goToMyAccount = () => {
    console.log("✅ 프로필 편집 페이지로 이동");
    location.href = '/prefs/myProfile';
  };

  // ✅ 내 프로필 클릭 이벤트
  if (profileImg && profileName) {
    profileImg.addEventListener("click", handleMyProfileClick);
    profileName.addEventListener("click", handleMyProfileClick);
  } else {
    console.warn("❌ profileImg 또는 profileName 요소 없음");
  }

  // ✅ 닫기 버튼
  if (closeModal) {
    closeModal.addEventListener("click", closeMiniProfileModal);
  }

  // ✅ [🔹추가된 부분] 친구 프로필 이미지 클릭 시 모달 열기
  document.addEventListener("click", function (e) {
    if (e.target.classList.contains("friend-profile")) {
      const imageSrc = e.target.getAttribute("src");
      const nickname = e.target.getAttribute("data-nickname");
      const userId = e.target.getAttribute("data-user-id");

      openMiniProfile(imageSrc, nickname, userId);
    }
  });
  window.openMiniProfile = openMiniProfile;
  window.closeMiniProfileModal = closeMiniProfileModal;
});
document.addEventListener("click", function (e) {
  if (e.target && e.target.id === "miniEditProfile") {
    console.log("✅ 프로필 편집 버튼 클릭됨");
    location.href = "/prefs/myProfile";
  }
});
