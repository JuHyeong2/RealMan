function ProfileModal() {
    document.getElementById("profileModal").style.display = "block";
}

function closeProfileModal() {
    document.getElementById("profileModal").style.display = "none";
}

function goToMyAccount() {
    location.href = '/prefs/myProfile';
}

function saveStatusMessage() {
    const message = document.getElementById("statusMessage").value;
    alert("상태 메시지가 저장되었습니다: " + message);
    closeProfileModal();
}
