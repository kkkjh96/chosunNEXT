document.addEventListener("DOMContentLoaded", function () {
    const username = document.getElementById("hiddenUser").value;

    console.log("✅ 현재 로그인한 사용자:", username);

    // 날짜 업데이트
    function getFormattedDate() {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        return `${year}년 ${month}월 ${day}일`;
    }

    document.getElementById('current-date').innerText = getFormattedDate();

    // 현재 URL 기반으로 메뉴 `selected` 클래스 설정
    const currentUrl = window.location.pathname;
    const menuLinks = document.querySelectorAll('.news-link'); // 전체일보 & 마이일보 링크

    console.log("📌 전체 뉴스 링크 목록:", menuLinks);

    menuLinks.forEach(link => {
        console.log("📌 이벤트 등록된 링크:", link, "클래스 목록:", link.classList);

        const linkPath = link.getAttribute("href");

        if (currentUrl === linkPath) {
            link.classList.add("selected");
        }

        // 클릭 이벤트 추가
        link.addEventListener("click", function (event) {
            console.log("✅ 클릭 이벤트 실행됨 - 클릭한 링크:", this);

            document.querySelectorAll('.news-link').forEach(l => l.classList.remove("selected"));
            this.classList.add("selected");

            // 🚀 마이뉴스 버튼 클릭 시 설문조사 확인 후 이동
            if (this.classList.contains("news-link-mynews")) {
                event.preventDefault();
                console.log("✅ 마이뉴스 클릭 - 설문조사 확인 실행");
                checkSurveyStatus(this.getAttribute("href"));
            } else {
                console.log("✅ 일반 링크 클릭 - 이동:", this.getAttribute("href"));
                window.location.href = this.getAttribute("href");
            }
        });
    });

    // 설문조사 여부 확인 후 마이뉴스 이동
    function checkSurveyStatus(targetUrl) {
        console.log("🚀 checkSurveyStatus 실행됨");
        console.log(`🔍 API 요청: /api/news/${username}/survey-status`);

        api.get(`/api/news/${username}/survey-status`)
            .then(response => {
                console.log("🔍 설문조사 여부 응답:", response.data);

                if (response.data) {
                    console.log("🚨 설문조사 필요 - /survey-form/8로 이동");
                    setTimeout(() => {
                        window.location.href = '/survey-form/8';
                    }, 50);
                } else {
                    console.log("✅ 설문조사 완료 - 마이뉴스로 이동:", targetUrl);
                    setTimeout(() => {
                        window.location.href = targetUrl;
                    }, 50);
                }
            })
            .catch(error => {
                console.error('❌ 설문조사 확인 오류:', error);
            });
    }
});
