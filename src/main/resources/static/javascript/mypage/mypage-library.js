document.addEventListener("DOMContentLoaded", function () {
    loadLibraryContent();
});

// 페이지네이션 설정
let currentPage = 1;
const pageSize = 5; // 페이지당 항목 개수
const userId = document.getElementById('hiddenUser').value;
let totalPages = 1;

function loadLibraryContent() {
    const params = new URLSearchParams(window.location.search);
    const tab = params.get("tab") || "recent";

    document.querySelectorAll(".tab-link").forEach(link => {
        link.classList.remove("active");
        if (link.dataset.tab === tab) {
            link.classList.add("active");
        }
    });

    api.get(`/api/mypage/library?tab=${tab}&page=${currentPage}&size=${pageSize}&userId=${userId}`)
        .then(response => {
            const items = response;

            if (items.totalCount === 0) {
                handleNoData(tab);
            } else {
                document.getElementById("total-count").textContent = `총 ${items.totalCount}개`;
                renderContent(tab, items.data);
                updatePagination(items.totalPages);
                totalPages = items.totalPages;
            }
        })
        .catch(error => {
            document.getElementById("article-list").innerHTML = "<p>데이터를 불러오지 못했습니다.</p>";
            document.querySelector(".pagination").style.display = "none";
        });
}

// ✅ 데이터가 없을 경우 처리하는 함수
function handleNoData(tab) {
    const contentContainer = document.getElementById("article-list");
    contentContainer.innerHTML = "";

    let message = "데이터가 없습니다.";
    if (tab === "ddabong") {
        message = "아직 공감한 내역이 없어요.";
    } else if (tab === "bookmarked") {
        message = "아직 북마크한 기사가 없어요.";
    } else if (tab === "recent") {
        message = "아직 최근 본 기사가 없어요.";
    } else if (tab === "tugo") {
        message = "아직 투고한 내역이 없어요.";
    } else if (tab === "commented") {
        message = "아직 댓글을 단 기사가 없어요.";
    } else if (tab === "subscribed") {
        message = "아직 구독한 기자가 없어요.";
    }

    contentContainer.innerHTML = `<p class='no-data-message'>${message}</p>`;
    document.getElementById("total-count").textContent = "총 0개";

    // ✅ 페이지네이션 숨기기
    document.querySelector(".pagination").style.display = "none";
}

// ✅ 페이지네이션 업데이트
function updatePagination(totalPages) {
    const paginationContainer = document.querySelector(".pagination");
    paginationContainer.innerHTML = ""; // 기존 버튼 초기화

    if (totalPages === 0) {
        paginationContainer.style.display = "none"; // 데이터 없을 때 숨김
        return;
    }

    paginationContainer.style.display = "flex"; // 데이터 있을 때 표시

    const prevBtn = document.createElement("button");
    prevBtn.textContent = "« 이전";
    prevBtn.classList.add("pagination-btn");
    prevBtn.dataset.page = currentPage - 1;
    if (currentPage === 1) prevBtn.style.display = "none"; // 첫 페이지에서 숨김

    const nextBtn = document.createElement("button");
    nextBtn.textContent = "다음 »";
    nextBtn.classList.add("pagination-btn");
    nextBtn.dataset.page = currentPage + 1;
    if (currentPage >= totalPages) nextBtn.style.display = "none"; // 마지막 페이지에서 숨김

    paginationContainer.appendChild(prevBtn);

    let startPage = 1;
    let endPage = totalPages;

    if (totalPages > 5) {
        // 현재 페이지가 6 이상이면 5의 배수에서 시작
        startPage = Math.floor((currentPage - 1) / 5) * 5 + 1;
        endPage = Math.min(startPage + 4, totalPages);
    }

    for (let i = startPage; i <= endPage; i++) {
        const pageBtn = document.createElement("button");
        pageBtn.textContent = i;
        pageBtn.classList.add("pagination-btn");
        pageBtn.dataset.page = i;
        if (i === currentPage) {
            pageBtn.classList.add("active"); // 현재 페이지 강조
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
    loadLibraryContent();
}

// ✅ 데이터 렌더링 (이미지 한 개만 노출, 기본 이미지 추가)
function renderContent(tab, items) {
    const contentContainer = document.getElementById("article-list");
    contentContainer.innerHTML = "";

    const newsMap = new Map(); // ✅ 중복 제거를 위한 Map 사용

    items.forEach(item => {
        if (!newsMap.has(item.newsId)) { // ✅ 같은 newsId가 없을 경우만 추가
            newsMap.set(item.newsId, true);

            const itemElement = document.createElement("div");
            itemElement.classList.add("article-item");

            // ✅ 이미지가 없으면 기본 이미지 적용
            const defaultImage = "/images/default.png"; // 기본 이미지 경로
            const imageUrl = item.image ? item.image : defaultImage;

            const imageTag = `<img src="${imageUrl}" alt="기사 이미지">`;

            itemElement.innerHTML = `
                ${imageTag}
                <div class="article-info">
                    <h3><a href="/categoryNews/detailNews/${item.newsId}" class="detail-link">${item.title}</a></h3>
                    <p>${item.content ? item.content : ""}</p>
                    <span class="article-meta">${item.date} 읽음</span>
                </div>
            `;

            contentContainer.appendChild(itemElement);
        }
    });
}
