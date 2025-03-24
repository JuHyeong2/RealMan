document.addEventListener("DOMContentLoaded", function () {
  console.log("✅ userProfile.js 로드됨");

  window.openUserProfile = function (imageSrc, nickname, userId) {
    console.log("✅ openUserProfile 실행됨", imageSrc, nickname, userId);

    const modal = document.getElementById("userProfileModal");
    if (!modal) return;

    document.getElementById("userProfileImage").src = imageSrc;
    document.getElementById("userProfileNickname").textContent = nickname;
    document.getElementById("userProfileId").textContent = `#${userId}`;
    modal.style.display = "block";
    modal.style.opacity = "1";
    modal.style.visibility = "visible";
  };

  window.closeUserProfileModal = function () {
    const modal = document.getElementById("userProfileModal");
    modal.style.opacity = "0";
    modal.style.visibility = "hidden";
    setTimeout(() => {
      modal.style.display = "none";
    }, 300);
  };

  // ✅ 친구 프로필 클릭
  document.addEventListener("click", function (e) {
    if (e.target.classList.contains("friend-profile")) {
      const imageSrc = e.target.getAttribute("src");
      const nickname = e.target.getAttribute("data-nickname");
      const userId = e.target.getAttribute("data-user-id");
      openUserProfile(imageSrc, nickname, userId);
    }
  });
});
