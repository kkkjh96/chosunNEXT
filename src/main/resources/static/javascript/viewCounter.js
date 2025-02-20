document.addEventListener("DOMContentLoaded", function () {
    saveViewRecord();
});

function saveViewRecord() {
    const newsId = window.location.pathname.split('/').pop();
    const userId = document.getElementById("userId").value;

    const data = {
        newsId: newsId,
        userId: userId
    }

    if (data) {
        api.put("/api/news/saveView", data)
            .then(() => console.log("조회 기록 저장 성공"))
            .catch(error => console.error("조회 기록 저장 실패:", error));
    }
}