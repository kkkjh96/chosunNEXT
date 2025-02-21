document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("passwordCheckBtn").addEventListener("click", checkPassword);
    document.getElementById("updateInfoBtn").addEventListener("click", updateUserInfo);
    // document.getElementById("updatePasswordBtn").addEventListener("click", updatePassword);
    document.getElementById("updatePasswordBtn").addEventListener("click", openPasswordModal);
    document.getElementById("deleteAccountBtn").addEventListener("click", openDeleteModal);
    formatPhoneNumber(); // 전화번호 자동 포맷 적용
});

// ✅ 비밀번호 확인 후 계정 정보 가져오기
function checkPassword() {
    const userId = document.getElementById("hiddenUser").value;
    const password = document.getElementById("passwordCheck").value;

    const userData = { userId, password };

    api.post('/api/mypage/account/info', userData)
        .then(response => {
            document.querySelector(".password-check-container").style.display = "none";
            document.querySelector(".user-info-container").style.display = "block";

            const user = response;
            document.getElementById("email").value = user.email;
            document.getElementById("nickname").value = user.nickname;
            document.getElementById("gender").value = user.gender || "";
            document.getElementById("birth").value = user.birth || "";
            document.getElementById("phone").value = user.phone || "";
            document.getElementById("address").value = user.address || "";
            document.getElementById("address2").value = user.address2 || "";
            document.getElementById("zipCd").value = user.zipCd || "";
        })
        .catch(() => alert("비밀번호가 일치하지 않습니다."));
}

// ✅ 주소 검색 (카카오 지도 API)
function searchAddress() {
    new daum.Postcode({
        oncomplete: function (data) {
            document.getElementById("address").value = data.address;
            document.getElementById("zipCd").value = data.zonecode;
        }
    }).open();
}

// ✅ 계정 정보 수정
function updateUserInfo() {
    const userId = document.getElementById("hiddenUser").value;

    const userData = {
        userId : userId,
        nickname: document.getElementById("nickname").value,
        gender: document.getElementById("gender").value,
        birth: document.getElementById("birth").value,
        phone: document.getElementById("phone").value,
        address: document.getElementById("address").value,
        address2: document.getElementById("address2").value,
        zipCd: document.getElementById("zipCd").value
    };

    api.put(`/api/mypage/account/update`, userData)
        .then(() => alert("정보 수정 완료"))
        .catch(() => alert("정보 수정 실패"));
}

// ✅ 전화번호 자동 형식 적용
function formatPhoneNumber() {
    const phoneInput = document.getElementById("phone");

    phoneInput.addEventListener("input", function (event) {
        let value = event.target.value.replace(/[^0-9]/g, "");
        if (value.length > 11) value = value.slice(0, 11);

        if (value.length <= 3) {
            event.target.value = value;
        } else if (value.length <= 7) {
            event.target.value = value.slice(0, 3) + "-" + value.slice(3);
        } else {
            event.target.value = value.slice(0, 3) + "-" + value.slice(3, 7) + "-" + value.slice(7);
        }
    });

    phoneInput.addEventListener("keydown", function (event) {
        if (event.key === "Backspace") {
            const value = phoneInput.value;
            if (value.endsWith("-")) phoneInput.value = value.slice(0, -1);
        }
    });
}

// ✅ 모달 열기 (비밀번호 변경)
function openPasswordModal() {
    document.getElementById("modalTitle").textContent = "비밀번호 변경";
    document.getElementById("modalDescription").textContent = "비밀번호를 변경하려면 현재 비밀번호를 입력하세요.";
    document.getElementById("modalConfirmBtn").setAttribute("onclick", "updatePassword()");
    document.getElementById("newPasswordFields").style.display = "block";
    document.getElementById("accountModal").style.display = "flex";
}

// ✅ 모달 열기 (회원 탈퇴)
function openDeleteModal() {
    document.getElementById("modalTitle").textContent = "회원 탈퇴";
    document.getElementById("modalDescription").textContent = "회원 탈퇴를 진행하려면 비밀번호를 입력하세요.";
    document.getElementById("modalConfirmBtn").setAttribute("onclick", "deleteAccount()");
    document.getElementById("newPasswordFields").style.display = "none";
    document.getElementById("accountModal").style.display = "flex";
}

// ✅ 모달 닫기
function closeModal() {
    document.getElementById("accountModal").style.display = "none";
}

// ✅ 비밀번호 변경 실행
function updatePassword() {
    const userId = document.getElementById("hiddenUser").value;
    const currentPassword = document.getElementById("modalPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmNewPassword").value;

    if (!currentPassword || !newPassword || !confirmPassword) {
        alert("모든 필드를 입력하세요.");
        return;
    }

    if (newPassword !== confirmPassword) {
        alert("새 비밀번호가 일치하지 않습니다.");
        return;
    }

    const passwordData = { userId: userId,
        currentPassword: currentPassword,
        newPassword: newPassword };

    api.put("/api/mypage/account/password", passwordData)
        .then(() => {
            alert("비밀번호 변경 완료");
            closeModal();
            window.location.href = "/login";
        })
        .catch(() => alert("비밀번호 변경 실패. 현재 비밀번호를 확인하세요."));
}

// ✅ 회원 탈퇴 실행
function deleteAccount() {
    const userId = document.getElementById("hiddenUser").value;
    const password = document.getElementById("modalPassword").value;

    if (!password) {
        alert("비밀번호를 입력해야 합니다.");
        return;
    }

    const userData = {
        userId: userId,
        password: password };

    api.delete("/api/mypage/account/delete", userData)
        .then(() => {
            alert("회원 탈퇴가 완료되었습니다.");
            window.location.href = "/logout";
        })
        .catch(() => alert("비밀번호가 일치하지 않습니다."));
}
