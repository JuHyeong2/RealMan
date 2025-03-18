document.addEventListener("DOMContentLoaded", function () {
    // 프로필 클릭 시 모달 열기
    document.getElementById("profileImg").addEventListener("click", function () {
        document.getElementById("profileModal").style.display = "block";
    });

    // 모달 닫기
    function closeProfileModal() {
        document.getElementById("profileModal").style.display = "none";
    }

    // 닫기 버튼 클릭 시 모달 닫기 이벤트 추가
    document.querySelector(".close").addEventListener("click", closeProfileModal);
});
