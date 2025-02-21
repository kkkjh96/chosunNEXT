document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("passwordCheckBtn").addEventListener("click", checkPassword);
    document.getElementById("updateInfoBtn").addEventListener("click", updateUserInfo);
    document.getElementById("updatePasswordBtn").addEventListener("click", updatePassword);
    document.getElementById("deleteAccountBtn").addEventListener("click", deleteAccount);
});

// ✅ 비밀번호 확인 후 계정 정보 가져오기
function checkPassword() {
    const userId = document.getElementById("hiddenUser").value;
    const password = document.getElementById("passwordCheck").value;

    const userData = {
        userId: userId,
        password: password
    };

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
        oncomplete: function(data) {
            document.getElementById("address").value = data.address;
            document.getElementById("zipCd").value = data.zonecode;
        }
    }).open();
}

// ✅ 계정 정보 수정
function updateUserInfo() {
    const userId = document.getElementById("hiddenUser").value;

    // JSON 객체로 데이터를 구성
    const userData = {
        userId: userId,
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
