document.addEventListener('DOMContentLoaded', function () {
    const path = window.location.pathname; // "/survey/view/1"
    const tugoId = path.split('/').pop();

    function fetchPost() {
        api.get(`/api/board/${tugoId}`)
            .then(response => {
                console.log(response);
                const data = response;
                // const data = response.data;
                console.log(data);
                document.getElementById('title').textContent = data.title;
                document.getElementById('nickname').textContent = data.userId;
                document.getElementById('instDt').textContent = data.createDt;
                document.getElementById('contents').innerHTML = data.contents;
                document.getElementById('like-count').textContent = data.like;
                // document.getElementById('dislike-count').textContent = data.dislikes;

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
            })
            .catch(error => {
                console.error("❌ 게시글 불러오기 실패:", error);
                alert("게시글을 불러오는 중 오류가 발생했습니다.");
            });
    }

    document.getElementById('like-button').addEventListener('click', function () {
        api.post(`/api/board/${tugoId}/like`, {}, { withCredentials: true })
            .then(() => fetchPost())
            .catch(() => alert("로그인이 필요합니다."));
    });

    // document.getElementById('dislike-button').addEventListener('click', function () {
    //     api.post(`/api/board/${tugoId}/dislike`, {}, { withCredentials: true })
    //         .then(() => fetchPost())
    //         .catch(() => alert("로그인이 필요합니다."));
    // });

    fetchPost();
});
