document.addEventListener("DOMContentLoaded", function () {
    loadRecommendedNews();
    loadHeadlines();
    loadHotNews();
    loadEditorPicks();
});

// 1. 추천 뉴스 (4개)
function loadRecommendedNews() {
    api.get('/api/news/recommended')
        .then(response => {
            renderRecommendedNews(response.data.slice(0, 4)); // 최대 4개
        })
        .catch(error => console.error("❌ 추천 뉴스 로드 실패:", error));
}

function renderRecommendedNews(newsList) {
    const container = document.querySelector(".recommended-news-articles");
    container.innerHTML = "";

    for (let i = 0; i < 4; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", category: "기타", imageUrl: "/images/default.png", newsId: "#" };

        const newsItem = `
            <div class="recommended-content">
                <div class="recommended-body">
                    <strong class="sub-category">${news.category || "기타"}</strong>
                    <a href="/news/${news.newsId}">
                        <h4>${news.title || "기사 제목 없음"}</h4>
                    </a>
                </div>
                <div class="recommended-images">
                    <a href="/news/${news.newsId}">
                        <img class="recommended-img" src="${news.imageUrl || '/images/default.png'}" alt="뉴스 이미지">
                    </a>
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    }
}

// 2. 주요 기사 (2개)
function loadHeadlines() {
    api.get('/api/news/headlines')
        .then(response => {
            renderHeadlines(response.data.slice(0, 2)); // 최대 2개
        })
        .catch(error => console.error("❌ 주요 기사 로드 실패:", error));
}

function renderHeadlines(newsList) {
    const container = document.querySelector(".headline-content");
    container.innerHTML = "";

    for (let i = 0; i < 2; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", summary: "설명이 없습니다.", imageUrl: "/images/default.png", newsId: "#" };

        const newsItem = `
            <div class="headline-body">
                <a href="/news/${news.newsId}">
                    <img class="headline-img" src="${news.imageUrl || '/images/default.png'}" alt="뉴스 이미지">
                    <h4>${news.title || "기사 제목 없음"}</h4>
                </a>
                <p>${news.summary || "설명이 없습니다."}</p>
            </div>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    }
}

// 3. 실시간 핫뉴스 (6개)
function loadHotNews() {
    api.get('/api/news/hot')
        .then(response => {
            renderHotNews(response.data.slice(0, 6)); // 최대 6개
        })
        .catch(error => console.error("❌ 실시간 핫뉴스 로드 실패:", error));
}

function renderHotNews(newsList) {
    const container = document.querySelector(".headline-hot-news ol");
    container.innerHTML = "";

    for (let i = 0; i < 6; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", newsId: "#" };

        const newsItem = `<li><a href="/news/${news.newsId}">${news.title || "기사 제목 없음"}</a></li>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    }
}

// 4. 에디터 추천 뉴스 (2개)
function loadEditorPicks() {
    api.get('/api/news/editor-picks')
        .then(response => {
            renderEditorPicks(response.data.slice(0, 2)); // 최대 2개
        })
        .catch(error => console.error("❌ 에디터 추천 뉴스 로드 실패:", error));
}

function renderEditorPicks(newsList) {
    const container = document.querySelector(".editor-category");
    container.innerHTML = "";

    for (let i = 0; i < 2; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", summary: "설명이 없습니다.", imageUrl: "/images/default.png", newsId: "#" };

        const newsItem = `
            <article class="editor-content">
                <h4>${news.title || "기사 제목 없음"}</h4>
                <img class="editor-img" src="${news.imageUrl || '/images/default.png'}" alt="뉴스 이미지">
                <p>${news.summary || "설명이 없습니다."}</p>
                <a href="/news/${news.newsId}">기사 더보기</a>
            </article>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    }
}
