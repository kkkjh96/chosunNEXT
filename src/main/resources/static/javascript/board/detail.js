document.addEventListener('DOMContentLoaded', function () {
    const path = window.location.pathname;
    const tugoId = path.split('/').pop();
    let liked = false; // 🔹 현재 사용자가 좋아요를 눌렀는지 저장
    let loggedInUser = null; // 로그인한 유저 정보 저장

    function fetchPost() {
        api.get(`/api/board/${tugoId}`)
            .then(response => {
                console.log(response);
                const data = response;

                document.getElementById('title').textContent = data.title;
                document.getElementById('nickname').textContent = data.userId;
                document.getElementById('instDt').textContent = data.createDt;
                document.getElementById('contents').innerHTML = data.contents;
                if(data.likeCount === 0){
                    document.getElementById('like-count').textContent = "0";
                } else {
                    document.getElementById('like-count').textContent = data.likeCount;
                }

                fetchLikeStatus(); // 🔹 좋아요 상태 별도 요청
                updateLikeButton();

                // 파일 리스트 표시
                const fileList = document.getElementById('fileList');
                fileList.innerHTML = "";
                data.files.forEach(file => {
                    const fileUrl = file.fileUrl;
                    const fileName = file.fileName;
                    const fileElement = document.createElement("div");

                    // 이미지 파일이면 미리보기 표시
                    if (/\.(jpg|jpeg|png|gif)$/i.test(fileName)) {
                        fileElement.innerHTML = `<img src="${fileUrl}" alt="첨부 이미지">`;
                    } else {
                        fileElement.innerHTML = `<a href="${fileUrl}" download>${fileName}</a>`;
                    }

                    fileList.appendChild(fileElement);
                });

                // 로그인한 유저 정보 가져오기
                fetchLoggedInUser(data.userId);

            })
            .catch(error => {
                console.error("❌ 게시글 불러오기 실패:", error);
                alert("게시글을 불러오는 중 오류가 발생했습니다.");
            });
    }

    document.getElementById("back-button").addEventListener("click", function () {
        const boardListPage = "/board/list"; // 게시판 리스트 페이지 경로

        if (document.referrer.includes(boardListPage)) {
            window.history.back(); // 이전 페이지가 게시판 리스트면 뒤로가기
        } else {
            window.location.href = boardListPage; // 그렇지 않으면 리스트 페이지로 이동
        }
    });

    function fetchLikeStatus() {
        api.get(`/api/board/${tugoId}/like-status`, { withCredentials: true })
            .then(response => {
                liked = response.likedByUser; // 🔹 서버에서 가져온 값 적용
                updateLikeButton();
            })
            .catch(error => {
                console.error("❌ 좋아요 상태 가져오기 실패:", error);
            });
    }

    function updateLikeButton() {
        const likeButton = document.getElementById('like-button');
    }

    document.getElementById('like-button').addEventListener('click', function () {
        if (!liked) {
            api.post(`/api/board/${tugoId}/like`, {}, { withCredentials: true })
                .then(() => {
                    liked = true;
                    updateLikeButton();
                    fetchPost();
                })
                .catch(() => {
                    alert("로그인이 필요합니다.");
                    const currentUrl = window.location.href;
                    sessionStorage.setItem("redirectAfterLogin", currentUrl); // 세션에 저장
                    window.location.href = "/login";
                });
        } else {
            api.delete(`/api/board/${tugoId}/like`, { withCredentials: true })
                .then(() => {
                    liked = false;
                    updateLikeButton();
                    fetchPost();
                })
                .catch(() => {
                    alert("로그인이 필요합니다.");
                    const currentUrl = window.location.href;
                    sessionStorage.setItem("redirectAfterLogin", currentUrl); // 세션에 저장
                    window.location.href = "/login";
                });
        }
    });

    function fetchLoggedInUser(postAuthor) {
        api.get('/api/user/me')
            .then(response => {
                console.log(response);
                loggedInUser = response;  // 로그인한 유저의 아이디
                if (loggedInUser === postAuthor) {
                    document.getElementById("edit-delete-buttons").style.display = "flex";
                }
            })
            .catch(error => {
                console.error("❌ 사용자 정보 불러오기 실패:", error);
            });
    }

    document.getElementById('edit-button').addEventListener('click', function () {
        window.location.href = `/board/edit/${tugoId}`;
    });

    document.getElementById('delete-button').addEventListener('click', function () {
        if (confirm("정말 삭제하시겠습니까?")) {
            api.delete(`/api/board/${tugoId}`)
                .then(() => {
                    alert("게시글이 삭제되었습니다.");
                    window.location.href = "/board/list";
                })
                .catch(error => {
                    console.error("❌ 게시글 삭제 실패:", error);
                    alert("게시글 삭제에 실패했습니다.");
                });
        }
    });

    fetchPost();
});
