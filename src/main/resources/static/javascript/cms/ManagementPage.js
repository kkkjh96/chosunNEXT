
    let currentPage = 1;
    const pageSize = 5;

    window.onload = function () {
    getAllNews(currentPage);
};

    async function getAllNews(currentPage) {
    try {
    let response = await axios.get(`/api/cms/news/paged?currentPage=${currentPage}&size=${pageSize}`);
    console.log("불러온 데이터:", response.data);

    if (response.data.length === 0) {
    alert("불러올 뉴스가 없습니다.");
    return;
}
    updateNewsTable(response.data.newsList);
    let totalPages = response.data.totalPages;
    updatePagination(response.data.currentPage, totalPages);
} catch (error) {
    console.error("불러오기 실패:", error);
    alert("불러오기 실패");
}
}



function updatePagination(currentPage, totalPages) {
    let paginationContainer = document.querySelector(".pagination");
    paginationContainer.innerHTML = ""; // 기존 버튼 초기화

    let prevButton = document.createElement("button");
    prevButton.innerText = "이전";
    prevButton.disabled = currentPage === 1;
    prevButton.onclick = function () {
    if (currentPage > 1) getAllNews(--currentPage);
};
    paginationContainer.appendChild(prevButton);

    // 페이지 번호 추가
    for (let i = 1; i <= totalPages; i++) {
    let pageButton = document.createElement("button");
    pageButton.innerText = i;
    if (i === currentPage) {
    pageButton.style.fontWeight = "bold"; // 현재 페이지 강조
}
    pageButton.onclick = function () {
    getAllNews(i);
};
    paginationContainer.appendChild(pageButton);
}

    let nextButton = document.createElement("button");
    nextButton.innerText = "다음";
    nextButton.disabled = currentPage === totalPages;
    nextButton.onclick = function () {
    if (currentPage < totalPages) getAllNews(++currentPage);
};
    paginationContainer.appendChild(nextButton);
}


    function updateNewsTable(newsList) {
    let tbody = document.querySelector(".news_list_wrap tbody");
    tbody.innerHTML = "";

    newsList.forEach(news => {
    let newsId = news.newsId ? news.newsId : "N/A";
    let row = document.createElement("tr");

    row.innerHTML = `
            <td><input type="checkbox"></td>
            <td><a href="#" class="news-link" onclick="loadNewsDetail(${news.newsId})">${news.title}</a></td>
            <td>${news.writer || 'N/A'}</td>
            <td>${news.credate_dt || 'N/A'}</td>
            <td><div class="btn release-btn">출고</div></td>
            <td><div class="btn edit-btn"><a href="/cms/update/${news.newsId}">수정</a></div></td>
            <td><button onclick="deleteBtn(${news.newsId})"><i class="fas fa-trash"></i></button></td>
        `;
    tbody.appendChild(row);
});


}

    async function loadNewsDetail(newsId) {
    if (!newsId || isNaN(Number(newsId)) || Number(newsId) === 0) {
    console.error("잘못된 뉴스 ID:", newsId);
    alert("잘못된 뉴스 ID 요청입니다.");
    return;
}

    try {
    let response = await axios.get(`/api/cms/news/${newsId}`);
    let news = response.data;
    console.log(news);

    document.querySelector(".new_detail").innerHTML = `
            <div class="close-btn" onclick="closeDetail()"><i class="fa-solid fa-xmark"></i></div>
            <h1>${news.title}</h1>
            <h2>${news.sub_title || '부제목 없음'}</h2>
            <p><strong>기자:</strong> ${news.writer || 'N/A'}</p>
            <p><strong>작성일:</strong> ${news.credate_dt || 'N/A'}</p>
            <div class="news-content">${news.content ? news.content.replace(/\n/g, "<br>") : '내용 없음'}</div>
        `;

    document.querySelector(".new_detail").style.display = "block";
} catch (error) {
    console.error("상세보기 불러오기 실패:", error);
    alert("기사 상세를 불러오지 못했습니다.");
}
}


    function closeDetail() {
    const newsDetail = document.querySelector(".new_detail");
    if (newsDetail) {
    newsDetail.style.display = "none";
}
}


    async function deleteBtn(newsId) {
    if (!newsId || isNaN(Number(newsId))) {
    alert("잘못된 뉴스 ID입니다.");
    return;
}
    if (!confirm("정말 삭제하시겠습니까?")) {
    return;
}

    try {
    await axios.delete(`/api/cms/news/${newsId}`);
    alert("삭제 완료");
    getAllNews(); // 삭제 후 목록 새로고침
} catch (error) {
    console.error("삭제 실패:", error);
    alert("삭제 실패");
}
}



