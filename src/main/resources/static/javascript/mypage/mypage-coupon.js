document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("couponCode").addEventListener("input", formatCouponCode);
    document.getElementById("registerCouponBtn").addEventListener("click", registerCoupon);
});

// ✅ 쿠폰 코드 입력 시 자동으로 0000-0000-0000-0000 형식 적용
function formatCouponCode(event) {
    let value = event.target.value.replace(/[^0-9]/g, ""); // 숫자만 입력
    if (value.length > 16) value = value.slice(0, 16);

    let formatted = value.replace(/(\d{4})(?=\d)/g, "$1-");
    event.target.value = formatted;
}

// ✅ 쿠폰 등록
function registerCoupon() {
    let couponCode = document.getElementById("couponCode").value.replace(/-/g, ""); // '-' 제거
    let couponMessage = document.getElementById("couponMessage");

    if (couponCode.length !== 16) {
        couponMessage.textContent = "쿠폰 번호를 정확히 입력하세요.";
        couponMessage.style.color = "red";
        return;
    }

    api.post("/api/mypage/coupon", { couponNum: couponCode })
        .then(response => {
            couponMessage.textContent = response.message;
            couponMessage.style.color = response.message.includes("정상적으로 등록") ? "green" : "red";
        })
        .catch(() => {
            couponMessage.textContent = "쿠폰 등록에 실패했습니다.";
            couponMessage.style.color = "red";
        });
}
