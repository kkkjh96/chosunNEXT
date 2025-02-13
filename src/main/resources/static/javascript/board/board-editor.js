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

        // HTML 태그를 포함한 전체 내용
        const fullContent = editor.getHTML();

        // HTML 태그 제거 후 순수 텍스트만 추출
        const content = editor.getMarkdown().replace(/[#>*_`~\[\](){}<>-]/g, '').trim();

        // FormData를 활용하여 데이터 전송
        const data = new FormData();
        data.append('title', title);
        data.append('userId', instId);
        data.append('content', content); // 텍스트 내용

        // 🔹 최종 등록 시 이미지 파일을 실제 업로드
        uploadedImages.forEach((blob, index) => {
            data.append(`file${index}`, blob);
        });

        if (!title || !content) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        // 게시글 데이터 전송
        axios.post('/api/board', data, {
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
