document.addEventListener('DOMContentLoaded', function() {
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
            // 이미지 업로드 후 미리보기 적용
            addImageBlobHook: (blob, callback) => {
                console.log("📌 미리보기용 이미지 데이터:", blob);

                // 🔹 Blob URL 생성하여 즉시 미리보기 적용
                const previewUrl = URL.createObjectURL(blob);
                callback(previewUrl, '미리보기 이미지');

                // 🔹 이후 업로드를 위해 이미지 데이터 저장
                addImageToTrack(blob);
            }
        }
    });

    // 저장할 이미지 파일 리스트
    let uploadedImages = [];

    /**
     * 업로드할 이미지 파일을 저장하는 함수
     * @param {Blob} blob - 업로드할 이미지 Blob 데이터
     */
    function addImageToTrack(blob) {
        uploadedImages.push(blob);
    }

    document.getElementById('submitPost').addEventListener('click', function() {
        const title = document.getElementById('title').value;
        const fullContent = editor.getMarkdown().replace(/[#>*_`~\[\](){}<>-]/g, '').trim();
        const content = fullContent.replace(/<img[^>]*>/g, '').trim();

        if (!title || !content) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        // DTO 데이터 (JSON 형식)
        const postData = {
            title: title,
            userId: instId,
            content: content
        };

        // FormData 생성
        const formData = new FormData();

        // 🔹 JSON 데이터를 Blob으로 변환하여 FormData에 추가
        formData.append('data', new Blob([JSON.stringify(postData)], { type: 'application/json' }));

        // 🔹 업로드된 이미지 파일 추가
        uploadedImages.forEach((blob, index) => {
            formData.append('files', blob, `image${index}.jpg`);  // 파일명 지정
        });

        console.log("📌 FormData 확인:", formData); // 디버깅 로그 추가

        // 서버로 전송
        axios.post('/api/board', formData, {
            headers: { "Content-Type": "multipart/form-data" },
            withCredentials: true // 세션 유지
        })
            .then(() => {
                alert('게시글이 등록되었습니다.');
                window.location.href = '/board/list';
            })
            .catch(error => {
                console.error('❌ 게시글 등록 실패:', error);
                alert('게시글 등록에 실패했습니다.');
            });
    });

});
