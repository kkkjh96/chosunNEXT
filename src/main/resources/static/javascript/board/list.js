document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 1;
    const pageSize = 10;
    let totalPosts = 0;

    function fetchPosts() {
        const sortType = document.getElementById("sort").value;

        api.get(`/api/board/list?page=${currentPage}&size=${pageSize}&sort=${sortType}`)
            .then(response => {
                totalPosts = response.totalCount;
                renderBoardList(response.posts);
                updatePagination();
            })
            .catch(error => {
                console.error("❌ 게시글 불러오기 실패:", error);
            });
    }

    function renderBoardList(posts) {
        const boardList = document.getElementById("board-list");
        boardList.innerHTML = "";

        posts.forEach(post => {
            const postElement = document.createElement("div");
            postElement.classList.add("board-item");
            postElement.innerHTML = `
                <a href="/board/detail/${post.tugoId}">${post.title}</a>
                <div class="board-meta">
                    ${post.userId} | ${new Date(post.createDt).toLocaleString()}
                </div>
                <span class="like-count">추천 수: ${post.likeCount}</span>
            `;
            boardList.appendChild(postElement);
        });
    }

    function updatePagination() {
        const totalPages = Math.ceil(totalPosts / pageSize);
        document.getElementById("page-info").textContent = `${currentPage} / ${totalPages}`;
        document.getElementById("prev-page").disabled = currentPage === 1;
        document.getElementById("next-page").disabled = currentPage >= totalPages;
    }

    document.getElementById("sort").addEventListener("change", () => {
        currentPage = 1;
        fetchPosts();
    });

    document.getElementById("prev-page").addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            fetchPosts();
        }
    });

    document.getElementById("next-page").addEventListener("click", () => {
        currentPage++;
        fetchPosts();
    });

    document.getElementById("write-post").addEventListener("click", () => {
        window.location.href = "/board/write";
    });

    fetchPosts();
});
