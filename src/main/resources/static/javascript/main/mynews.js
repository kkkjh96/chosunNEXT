const username = document.getElementById("hiddenUser").value;
console.log(username);

document.addEventListener("DOMContentLoaded", function () {
    loadMainArticle();
    loadRecommendedNews();
    loadHeadlines();
    loadHotNews();
    loadEditorPicks();
});

// ✅ 메인 기사 로드
function loadMainArticle() {
    api.get('/api/news/myMain')
        .then(response => {
            renderMainArticle(response);
        })
        .catch(error => console.error("❌ 메인 기사 로드 실패:", error));
}

function renderMainArticle(news) {
    const container = document.querySelector(".main-article");
    container.innerHTML = "";

    const imageUrl = news.fileUrl ? (Array.isArray(news.fileUrl) ? news.fileUrl[0] : news.fileUrl) : '/images/default.png';

    const newsItem = `
        <a href="/categoryNews/detailNews/${news.newsId || '#'}">
            <img class="default" src="${imageUrl}" alt="${news.title || '기사 이미지'}">
            <h3>${news.title || "기사 제목 없음"}</h3>
        </a>
        <p>${news.subTitle || "기사 요약 정보가 없습니다."}</p>
    `;

    container.insertAdjacentHTML('beforeend', newsItem);
}

// ✅ 추천 뉴스 로드 (중복 제거 적용)
function loadRecommendedNews() {
    api.get(`/api/news/recommended/${username}`)
        .then(response => {
            const uniqueNews = removeDuplicateNews(response);
            renderRecommendedNews(uniqueNews.slice(0, 4));
        })
        .catch(error => console.error("❌ 추천 뉴스 로드 실패:", error));
}

function renderRecommendedNews(newsList) {
    const container = document.querySelector(".recommended-news-articles");
    container.innerHTML = "";

    newsList.forEach(news => {
        const imageUrl = news.fileUrl ? (Array.isArray(news.fileUrl) ? news.fileUrl[0] : news.fileUrl) : '/images/default.png';

        const newsItem = `
            <div class="recommended-content">
                <div class="recommended-body">
                    <strong class="sub-category">${news.subCategory || "기타"}</strong>
                    <a href="/categoryNews/detailNews/${news.newsId}">
                        <h4>${news.title || "기사 제목 없음"}</h4>
                    </a>
                </div>
                <div class="recommended-images">
                    <a href="/categoryNews/detailNews/${news.newsId}">
                        <img class="recommended-img" src="${imageUrl}" alt="뉴스 이미지">
                    </a>
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    });
}

// ✅ 주요 뉴스 로드 (중복 제거 적용)
function loadHeadlines() {
    api.get(`/api/news/headlines/${username}`)
        .then(response => {
            const uniqueNews = removeDuplicateNews(response);
            renderHeadlines(uniqueNews.slice(0, 5));
        })
        .catch(error => console.error("❌ 주요 기사 로드 실패:", error));
}

function renderHeadlines(newsList) {
    const mainContainer = document.querySelector(".headline-content");
    mainContainer.innerHTML = "";

    const sideContainer = document.querySelector(".headline-side-contain");
    sideContainer.innerHTML = "";

    newsList.forEach((news, index) => {
        const imageUrl = news.fileUrl ? (Array.isArray(news.fileUrl) ? news.fileUrl[0] : news.fileUrl) : '/images/default.png';

        const newsItem = index < 2 ? `
            <div class="headline-body">
                <a href="/categoryNews/detailNews/${news.newsId}">
                    <img class="headline-img" src="${imageUrl}" alt="뉴스 이미지">
                    <h4>${news.title || "기사 제목 없음"}</h4>
                </a>
                <p>${news.summary || ""}</p>
            </div>` : `
            <div class="headline-side-content">
                <div class="headline-side-body">
                    <a href="/categoryNews/detailNews/${news.newsId}">
                        <h4>${news.title || "기사 제목 없음"}</h4>
                    </a>
                </div>
                <div class="headline-side-images">
                    <a href="/categoryNews/detailNews/${news.newsId}">
                        <img class="headline-side-img" src="${imageUrl}" alt="뉴스 이미지">
                    </a>
                </div>
                <div class="headline-side-subtitle">
                    <a href="/categoryNews/detailNews/${news.newsId}">
                        <p>${news.summary || ""}</p>
                    </a>
                </div>
            </div>`;

        if (index < 2) {
            mainContainer.insertAdjacentHTML('beforeend', newsItem);
        } else {
            sideContainer.insertAdjacentHTML('beforeend', newsItem);
        }
    });
}

// ✅ 실시간 핫뉴스 로드 (중복 제거 적용)
function loadHotNews() {
    api.get(`/api/news/hot/${username}`)
        .then(response => {
            const uniqueNews = removeDuplicateNews(response);
            renderHotNews(uniqueNews.slice(0, 6));
        })
        .catch(error => console.error("❌ 실시간 핫뉴스 로드 실패:", error));
}

function renderHotNews(newsList) {
    const container = document.querySelector(".headline-hot-news ol");
    container.innerHTML = "";

    newsList.forEach(news => {
        const newsItem = `
            <li>
                <a href="/categoryNews/detailNews/${news.newsId}">${news.title || "기사 제목 없음"}</a>
            </li>`;
        container.insertAdjacentHTML('beforeend', newsItem);
    });
}

// ✅ 에디터 추천 뉴스 로드 (중복 제거 적용)
function loadEditorPicks() {
    api.get(`/api/news/editor-picks/${username}`)
        .then(response => {
            const uniqueNews = removeDuplicateNews(response);
            renderEditorPicks(uniqueNews.slice(0, 3));
            renderEditorHotNews(uniqueNews.slice(3, 9));
        })
        .catch(error => console.error("❌ 에디터 추천 뉴스 로드 실패:", error));
}

function renderEditorPicks(newsList) {
    const editorContainer = document.querySelector(".editor-category");
    editorContainer.innerHTML = "";

    newsList.forEach(news => {
        const imageUrl = news.fileUrl ? (Array.isArray(news.fileUrl) ? news.fileUrl[0] : news.fileUrl) : '/images/default.png';

        const newsItem = `
            <article class="editor-content">
                <a href="/categoryNews/detailNews/${news.newsId}">
                    <h4>${news.title || "기사 제목 없음"}</h4>
                    <img class="editor-img" src="${imageUrl}" alt="뉴스 이미지">
                    <p>${news.summary || ""}</p>
                </a>
            </article>`;
        editorContainer.insertAdjacentHTML('beforeend', newsItem);
    });
}

function renderEditorHotNews(newsList) {
    const hotNewsContainer = document.querySelector(".hot-news ol");
    hotNewsContainer.innerHTML = "";

    newsList.forEach(news => {
        const hotNewsItem = `
            <li><a href="/categoryNews/detailNews/${news.newsId}">${news.title || "기사 제목 없음"}</a></li>`;
        hotNewsContainer.insertAdjacentHTML('beforeend', hotNewsItem);
    });
}

// ✅ 중복된 뉴스 제거 함수
function removeDuplicateNews(newsList) {
    const uniqueNews = [];
    const newsSet = new Set();

    newsList.forEach(news => {
        if (!newsSet.has(news.newsId)) {
            newsSet.add(news.newsId);
            uniqueNews.push(news);
        }
    });

    return uniqueNews;
}
