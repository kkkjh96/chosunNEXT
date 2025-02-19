document.addEventListener("DOMContentLoaded", function () {
    loadMainArticle(); //
    loadRecommendedNews(); //
    loadHeadlines(); //
    loadHotNews(); //
    loadEditorPicks();
});

function loadMainArticle() {
    api.get('/api/news/Main') // ✅ 백엔드에서 메인 기사 데이터를 가져옴
        .then(response => {
            renderMainArticle(response);
        })
        .catch(error => console.error("❌ 메인 기사 로드 실패:", error));
}

function renderMainArticle(news) {
    const container = document.querySelector(".main-article");
    container.innerHTML = ""; // 기존 내용 초기화

    const newsItem = `
        <a href="/news/${news.newsId || '#'}">
            <img class="default" src="${news.fileUrl || '/images/default.png'}" alt="${news.title || '기사 이미지'}">
            <h3>${news.title || "기사 제목 없음"}</h3>
        </a>
        <p>${news.subTitle || "기사 요약 정보가 없습니다."}</p>
    `;

    container.insertAdjacentHTML('beforeend', newsItem);
}

// 1. 추천 뉴스 (4개)
function loadRecommendedNews() {
    api.get(`/api/news/recommended`)
        .then(response => {
            console.log(response);
            renderRecommendedNews(response.slice(0, 4)); // 최대 4개
        })
        .catch(error => console.error("❌ 추천 뉴스 로드 실패:", error));
}

function renderRecommendedNews(newsList) {
    const container = document.querySelector(".recommended-news-articles");
    container.innerHTML = "";

    for (let i = 0; i < 4; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", mainCategory: "기타", fileUrl: "/images/default.png", newsId: "#" };

        const newsItem = `
            <div class="recommended-content">
                <div class="recommended-body">
                    <strong class="sub-category">${news.mainCategory || "기타"}</strong>
                    <a href="/news/${news.newsId}">
                        <h4>${news.title || "기사 제목 없음"}</h4>
                    </a>
                </div>
                <div class="recommended-images">
                    <a href="/news/${news.newsId}">
                        <img class="recommended-img" src="${news.fileUrl || '/images/default.png'}" alt="뉴스 이미지">
                    </a>
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    }
}

// 2. 주요 기사 (2개)
function loadHeadlines() {
    api.get(`/api/news/headlines`)
        .then(response => {
            renderHeadlines(response.slice(0, 5)); // 최대 2개
        })
        .catch(error => console.error("❌ 주요 기사 로드 실패:", error));
}

function renderHeadlines(newsList) {
    const mainContainer = document.querySelector(".headline-content");
    mainContainer.innerHTML = "";

    const sideContainer = document.querySelector(".headline-side-contain");
    sideContainer.innerHTML = "";

    // 메인 주요 기사 (2개)
    for (let i = 0; i < 2; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", summary: "설명이 없습니다.", fileUrl: "/images/default.png", newsId: "#" };

        const newsItem = `
            <div class="headline-body">
                <a href="/news/${news.newsId}">
                    <img class="headline-img" src="${news.fileUrl || '/images/default.png'}" alt="뉴스 이미지">
                    <h4>${news.title || "기사 제목 없음"}</h4>
                </a>
                <p>${news.summary || ""}</p>
            </div>`;
        mainContainer.insertAdjacentHTML('beforeend', newsItem);
    }

    // 사이드 주요 기사 (3개)
    for (let i = 2; i < 5; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", summary: "설명이 없습니다.", fileUrl: "/images/default.png", newsId: "#" };

        const newsItem = `
            <div class="headline-side-content">
                <div class="headline-side-body">
                    <a href="/news/${news.newsId}">
                        <h4>${news.title || "기사 제목 없음"}</h4>
                    </a>
                </div>
                <div class="headline-side-images">
                    <a href="/news/${news.newsId}">
                        <img class="headline-side-img" src="${news.fileUrl || '/images/default.png'}" alt="뉴스 이미지">
                    </a>
                </div>
                <div class="headline-side-subtitle">
                    <a href="/news/${news.newsId}">
                        <p>${news.summary || ""}</p>
                    </a>
                </div>
            </div>`;
        sideContainer.insertAdjacentHTML('beforeend', newsItem);
    }
}

// 3. 실시간 핫뉴스 (6개)
function loadHotNews() {
    api.get(`/api/news/hot`)
        .then(response => {
            console.log(response);
            renderHotNews(response.slice(0, 6)); // 최대 6개
        })
        .catch(error => console.error("❌ 실시간 핫뉴스 로드 실패:", error));
}

function renderHotNews(newsList) {
    const container = document.querySelector(".headline-hot-news ol");
    container.innerHTML = "";

    for (let i = 0; i < 6; i++) {
        const news = newsList[i] || { title: "기사 제목 없음", newsId: "#" };

        const newsItem = `
            <li>
                <a href="/news/${news.newsId}">${news.title || "기사 제목 없음"}</a>
            </li>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    }
}

// 4. 에디터 추천 뉴스 (2개)
function loadEditorPicks() {
    api.get(`/api/news/editor-picks`)
        .then(response => {
            renderEditorPicks(response); // 에디터 추천 뉴스
            renderEditorHotNews(response); // 에디터 핫 뉴스
        })
        .catch(error => console.error("❌ 에디터 추천 뉴스 로드 실패:", error));
}

function renderEditorPicks(newsList) {
    const editorContainer = document.querySelector(".editor-category");
    editorContainer.innerHTML = ""; // 기존 내용 초기화

    newsList.slice(0, 3).forEach(news => { // 최대 3개 표시
        const newsItem = `
            <article class="editor-content">
                <a href="/news/${news.newsId}">
                    <h4>${news.title || "기사 제목 없음"}</h4>
                    <img class="editor-img" src="${news.fileUrl || '/images/default.png'}" alt="뉴스 이미지">
                    <p>${news.summary || ""}</p>
                </a>
            </article>`;
        editorContainer.insertAdjacentHTML('beforeend', newsItem);
    });
}

function renderEditorHotNews(newsList) {
    const hotNewsContainer = document.querySelector(".hot-news ol");
    hotNewsContainer.innerHTML = ""; // 기존 내용 초기화

    newsList.slice(3, 9).forEach(news => { // 6개 표시
        const hotNewsItem = `
            <li><a href="/news/${news.newsId}">${news.title || "기사 제목 없음"}</a></li>`;
        hotNewsContainer.insertAdjacentHTML('beforeend', hotNewsItem);
    });
}
