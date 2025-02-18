document.addEventListener('DOMContentLoaded', function () {
    const postId = window.location.pathname.split('/').pop();
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
                addImageToTrack(blob);
            }
        }
    });

    let uploadedImages = [];
    let deletedImages = []; // 삭제할 이미지 목록

    function addImageToTrack(blob) {
        uploadedImages.push(blob);
    }

    // ✅ 기존 게시글 데이터 불러오기
    api.get(`/api/board/${postId}`)
        .then(response => {
            const post = response;
            document.getElementById('title').value = post.title;

            let content = post.contents; // 기존 텍스트 내용

            // ✅ 기존 이미지 HTML 생성 (삭제 버튼 포함)
            post.files.forEach(file => {
                const imageUrl = file.fileUrl;
                const imageName = file.fileName;

                const imageHTML = `
                    <span class="editor-image-container" contenteditable="false">
                        <img src="${imageUrl}" alt="${imageName}" class="editor-image" data-id="${file.id}" />
                        <button type="button" class="delete-image-btn" data-id="${file.id}">X</button>
                    </span>`;

                // ✅ 기존 내용 + 이미지 함께 삽입
                content += imageHTML;
            });

            editor.setHTML(content);
        })
        .catch(error => {
            console.error("게시글 불러오기 실패", error);
            alert("게시글을 불러오는 데 실패했습니다.");
        });

    // ✅ 이미지 삭제 기능 추가
    document.addEventListener('click', function (event) {
        if (event.target.classList.contains('delete-image-btn')) {
            const imageId = event.target.dataset.id;
            event.target.closest('.editor-image-container').remove(); // 화면에서 삭제

            // 🔹 삭제할 이미지 목록에 추가
            if (!deletedImages.includes(imageId)) {
                deletedImages.push(imageId);
            }
        }
    });

    // ✅ 게시글 수정 요청
    document.getElementById('submitPost').addEventListener('click', function () {
        const title = document.getElementById('title').value;
        let fullContent = editor.getHTML();

        // 🔹 블롭(미리보기 이미지) 삭제
        fullContent = fullContent.replace(/<img[^>]*src=["']blob:[^"']*["'][^>]*>/g, '');

        if (!title || !fullContent) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        const postData = {
            tugoId: postId,
            title: title,
            userId: instId,
            content: fullContent,  // ✅ HTML 그대로 저장
            deletedImages: deletedImages // ✅ 삭제할 이미지 목록 서버로 전달
        };

        const formData = new FormData();
        formData.append('data', new Blob([JSON.stringify(postData)], { type: 'application/json' }));

        // ✅ 업로드된 이미지 추가
        if (uploadedImages.length > 0) {
            uploadedImages.forEach((blob, index) => {
                formData.append('files', blob, `image${index}.jpg`);
            });
        }

        console.log("📌 FormData 확인:", formData);

        // ✅ 서버로 전송
        axios.put(`/api/board/${postId}`, formData, {
            headers: { "Content-Type": "multipart/form-data" },
            withCredentials: true
        })
            .then(() => {
                alert('게시글이 수정되었습니다.');
                window.location.href = '/board/list';
            })
            .catch(error => {
                console.error('게시글 수정 실패', error);
                alert('게시글 수정에 실패했습니다.');
            });
    });

});
