document.addEventListener("DOMContentLoaded", function () {
    loadSubscribedNews();
});

// ✅ 페이지네이션 설정
let currentPage = 1;
const pageSize = 5; // 페이지당 항목 개수
const userId = document.getElementById('hiddenUser').value;

function loadSubscribedNews() {
    api.get(`/api/mypage/subscriptions?userId=${userId}&page=${currentPage}&size=${pageSize}`)
        .then(response => {
            const items = response;
            if (items.totalCount === 0) {
                handleNoSubscribedNews();
            } else {
                document.getElementById("total-count").textContent = `총 ${items.totalCount}개`;
                renderSubscribedNews(items.data);
                updatePagination(items.totalPages);
            }
        })
        .catch(error => {
            document.getElementById("article-list").innerHTML = "<p>데이터를 불러오지 못했습니다.</p>";
            document.querySelector(".pagination").style.display = "none";
        });
}

// ✅ 데이터 없을 경우
function handleNoSubscribedNews() {
    document.getElementById("article-list").innerHTML = "<p class='no-data-message'>구독한 기자의 기사가 없습니다.</p>";
    document.getElementById("total-count").textContent = "총 0개";
    document.querySelector(".pagination").style.display = "none";
}

// ✅ 뉴스 데이터 렌더링
function renderSubscribedNews(items) {
    const contentContainer = document.getElementById("article-list");
    contentContainer.innerHTML = "";

    items.forEach(item => {
        const itemElement = document.createElement("div");
        itemElement.classList.add("article-item");
        itemElement.innerHTML = `
            <img src="${item.fileUrl ? item.fileUrl : '/images/default.jpg'}" alt="기사 이미지">
            <div class="article-info">
                <span class="journalist">${item.writer} 기자</span>
                <h3><a href="/categoryNews/board/detail/${item.newsId}" class="news-link">${item.title}</a></h3>
                <span class="article-meta">${item.credateDt}</span>
            </div>
        `;
        contentContainer.appendChild(itemElement);
    });
}

// ✅ 페이지네이션 업데이트
function updatePagination(totalPages) {
    const paginationContainer = document.querySelector(".pagination");
    paginationContainer.innerHTML = "";

    if (totalPages === 0) {
        paginationContainer.style.display = "none";
        return;
    }

    paginationContainer.style.display = "flex";

    const prevBtn = document.createElement("button");
    prevBtn.textContent = "« 이전";
    prevBtn.classList.add("pagination-btn");
    prevBtn.dataset.page = currentPage - 1;
    if (currentPage === 1) prevBtn.style.display = "none";

    const nextBtn = document.createElement("button");
    nextBtn.textContent = "다음 »";
    nextBtn.classList.add("pagination-btn");
    nextBtn.dataset.page = currentPage + 1;
    if (currentPage >= totalPages) nextBtn.style.display = "none";

    paginationContainer.appendChild(prevBtn);

    let startPage = 1;
    let endPage = totalPages;

    if (totalPages > 5) {
        startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
        endPage = Math.min(startPage + 4, totalPages);
    }

    for (let i = startPage; i <= endPage; i++) {
        const pageBtn = document.createElement("button");
        pageBtn.textContent = i;
        pageBtn.classList.add("pagination-btn");
        pageBtn.dataset.page = i;
        if (i === currentPage) {
            pageBtn.classList.add("active");
        }
        paginationContainer.appendChild(pageBtn);
    }

    paginationContainer.appendChild(nextBtn);
}

// ✅ 이벤트 위임 방식으로 페이지 버튼 클릭 이벤트 추가
document.querySelector(".pagination").addEventListener("click", function (event) {
    const target = event.target;
    if (target.classList.contains("pagination-btn")) {
        const newPage = parseInt(target.dataset.page);
        if (!isNaN(newPage)) {
            changePage(newPage);
        }
    }
});

function changePage(page) {
    if (page < 1 || page > totalPages) return;
    currentPage = page;
    loadSubscribedNews();
}
