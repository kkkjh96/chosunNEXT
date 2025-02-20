document.addEventListener("DOMContentLoaded", function () {
    loadMyPageData();
});

function logout() {
    if (confirm("정말 로그아웃하시겠습니까?")) {
        window.location.href = "/logout";
    }
}

function loadMyPageData() {
    const userId = document.getElementById("hiddenUser").value;

    api.get(`/api/mypage/${userId}`)
        .then(response => {
            console.log(response);

            const data = response;

            document.getElementById("username").textContent = `${data.username}님 환영합니다.`;

            document.getElementById("points").textContent = `${data.point}P`;

            const level = calculateLevel(data.points);

            document.getElementById("level").textContent = `Lv. ${level}`;

            document.getElementById("recentRead").textContent = data.recentRead;
            document.getElementById("subscribed").textContent = data.subscribed;
            document.getElementById("bookmarked").textContent = data.bookmarked;
            document.getElementById("posted").textContent = data.tugo;
            document.getElementById("liked").textContent = data.ddabong;
            document.getElementById("commented").textContent = data.commented;

            renderChart(data.weeklyViews);
        })
        .catch(error => console.error("마이페이지 데이터 불러오기 실패:", error));
}

// 레벨 계산 함수
function calculateLevel(points) {
    if (points >= 54000) return 5;
    if (points >= 27000) return 4;
    if (points >= 13500) return 3;
    if (points >= 4500) return 2;
    return 1; // 기본 Lv1
}

function renderChart(weeklyViews) {
    const labels = Object.keys(weeklyViews); // 최근 7일 날짜
    const data = Object.values(weeklyViews); // 조회 수

    const ctx = document.getElementById("activityChart").getContext("2d");
    new Chart(ctx, {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "조회 수",
                data: data,
                backgroundColor: ["#4CAF50", "#2196F3", "#FF9800", "#F44336", "#9C27B0", "#FFC107", "#03A9F4"],
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1, // ✅ Y축 눈금을 1단위로 설정
                        precision: 0 // ✅ 소수점 제거 (정수만 표시)
                    }
                }
            }
        }
    });
}
