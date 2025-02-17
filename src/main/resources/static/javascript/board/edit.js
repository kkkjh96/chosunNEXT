document.addEventListener('DOMContentLoaded', function () {
    const postId = window.location.pathname.split('/').pop(); // URL에서 게시글 ID 추출
    const instId = document.getElementById('instId').value;

    // Toast UI Editor 초기화
    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        height: '500px',
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        toolbarItems: [
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task'],
            ['table', 'link'],
            ['image', 'code', 'codeblock']
        ],
        hooks: {
            addImageBlobHook: (blob, callback) => {
                const previewUrl = URL.createObjectURL(blob);
                callback(previewUrl, '미리보기 이미지');
                addImageToTrack(blob); // 이미지 추가
            }
        }
    });

    let uploadedImages = [];
    let existingImages = [];  // 기존 이미지 목록

    function addImageToTrack(blob) {
        uploadedImages.push(blob);
    }

    // 기존 게시글 데이터 불러오기
    api.get(`/api/board/${postId}`)
        .then(response => {
            const post = response;
            document.getElementById('title').value = post.title;
            editor.setHTML(post.contents);

            // 기존 이미지 처리 (미리보기 및 삭제 기능)
            post.files.forEach(file => {
                existingImages.push(file); // 기존 이미지 목록에 추가

                // 이미지 미리보기 처리
                const imagePreview = document.createElement('img');
                imagePreview.src = file.fileUrl; // 파일 URL 설정
                imagePreview.alt = file.fileName;
                imagePreview.classList.add('existing-image');
                imagePreview.setAttribute('data-file-id', file.fileId); // 파일 ID를 data 속성에 저장

                // 이미지 삭제 버튼 추가
                const deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                deleteButton.classList.add('delete-image');
                deleteButton.addEventListener('click', function () {
                    deleteImage(file.fileId); // 이미지 삭제 처리
                });

                // 에디터에 기존 이미지 및 삭제 버튼 삽입
                const imageWrapper = document.createElement('div');
                imageWrapper.appendChild(imagePreview);
                imageWrapper.appendChild(deleteButton);
                document.querySelector('#fileList').appendChild(imageWrapper); // #imagePreviewArea에 삽입
            });
        })
        .catch(error => {
            console.error("게시글 불러오기 실패", error);
            alert("게시글을 불러오는 데 실패했습니다.");
        });

    // 게시글 수정 버튼 클릭 시
    document.getElementById('submitPost').addEventListener('click', function () {
        const title = document.getElementById('title').value;
        let fullContent = editor.getHTML();

        // 🔹 blob 미리보기 이미지 제거
        fullContent = fullContent.replace(/<img[^>]*src=["']blob:[^"']*["'][^>]*>/g, '');

        // 🔹 HTML 태그 제거 후 순수 텍스트만 추출
        let content = fullContent.replace(/<\/?[^>]+(>|$)/g, "").trim();

        if (!title || !content) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        const postData = {
            title: title,
            userId: instId,
            content: content,
            postId: postId,
            deletedImages: existingImages.filter(image => image.deleted).map(image => image.fileId)  // 삭제된 이미지 리스트
        };

        // FormData 생성
        const formData = new FormData();
        formData.append('data', new Blob([JSON.stringify(postData)], { type: 'application/json' }));

        // 🔹 업로드된 이미지가 있을 경우만 추가
        if (uploadedImages.length > 0) {
            uploadedImages.forEach((blob, index) => {
                formData.append('files', blob, `image${index}.jpg`);  // 파일명 지정
            });
        }

        console.log("📌 FormData 확인:", formData); // 디버깅 로그 추가

        // 서버로 전송
        axios.put(`/api/board/${postId}`, formData, {
            headers: { "Content-Type": "multipart/form-data" },
            withCredentials: true
        })
            .then(() => {
                alert('게시글이 수정되었습니다.');
                window.location.href = '/board/list'; // 게시판 리스트로 이동
            })
            .catch(error => {
                console.error('게시글 수정 실패', error);
                alert('게시글 수정에 실패했습니다.');
            });
    });

    // 이미지 삭제 처리
    function deleteImage(fileId) {
        // 서버에서 해당 이미지 삭제
        api.delete(`/api/board/image/${fileId}`)
            .then(() => {
                alert('이미지가 삭제되었습니다.');
                // UI에서 해당 이미지 삭제
                const imageElement = document.querySelector(`[data-file-id="${fileId}"]`);
                imageElement.parentElement.remove(); // 삭제된 이미지 제거

                // 기존 이미지 목록에서 해당 이미지 삭제
                const imageIndex = existingImages.findIndex(image => image.fileId === fileId);
                if (imageIndex !== -1) {
                    existingImages[imageIndex].deleted = true;  // 삭제된 이미지로 표시
                }
            })
            .catch(error => {
                console.error('이미지 삭제 실패', error);
                alert('이미지 삭제에 실패했습니다.');
            });
    }
});
