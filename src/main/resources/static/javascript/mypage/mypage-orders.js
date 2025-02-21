document.addEventListener("DOMContentLoaded", function () {
    loadPaymentHistory();
});

// ✅ 결제 내역 불러오기
function loadPaymentHistory() {
    const userId = document.getElementById("hiddenUser").value;

    const data = {userId: userId};

    api.get(`/api/mypage/orders`, data)
        .then(response => {
            renderPaymentHistory(response);
        })
        .catch(error => {
            console.error("❌ 결제 내역 불러오기 실패:", error);
            document.getElementById("payment-list").innerHTML = "<li class='no-data-message'>결제 내역이 없습니다.</li>";
        });
}

// ✅ 결제 내역 리스트 렌더링
function renderPaymentHistory(paymentList) {
    const container = document.getElementById("payment-list");
    container.innerHTML = "";

    if (paymentList.length === 0) {
        container.innerHTML = "<li class='no-data-message'>결제 내역이 없습니다.</li>";
        return;
    }

    paymentList.forEach(payment => {
        const listItem = `
            <li class="payment-item">
                <h3>${payment.productName}</h3>
                <p class="payment-cost">${payment.cost.toLocaleString()} 원</p>
                <p class="payment-info"><strong>구매일:</strong> ${formatDate(payment.orderDt)}</p>
                <p class="payment-info"><strong>만료일:</strong> ${payment.expireDate ? formatDate(payment.expireDate) : "-"}</p>
                <p class="payment-info"><strong>구독 기간:</strong> ${payment.subscriptionPeriod}</p>
            </li>
        `;
        container.insertAdjacentHTML("beforeend", listItem);
    });
}

// ✅ 날짜 포맷 변환 함수
function formatDate(dateString) {
    if (!dateString) return "-";
    const date = new Date(dateString);
    return date.toISOString().split("T")[0]; // YYYY-MM-DD 형식 반환
}
