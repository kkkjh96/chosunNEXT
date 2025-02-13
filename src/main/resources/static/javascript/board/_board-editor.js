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
            // 이미지 업로드 후 반영하는 Hook
            addImageBlobHook: (blob, callback) => {
                console.log("📌 업로드할 blob 데이터:", blob);

                // FormData 생성 (파일과 사용자 ID 포함)
                const formData = new FormData();
                formData.append("file", blob);
                formData.append("instId", instId);

                axios.post('/api/file/upload', formData, {
                    headers: { "Content-Type": "multipart/form-data" }
                })
                    .then(response => {
                        console.log("✅ 이미지 업로드 성공:", response);

                        // 서버에서 반환된 파일명으로 이미지 URL 생성
                        const fileName = response.data;
                        const timestamp = new Date().getTime();  // 캐시 무효화용 타임스탬프 추가
                        const imageUrl = `${fileName}?t=${timestamp}`;

                        addImageToTrack(imageUrl); // 업로드된 이미지 URL 저장
                        callback(imageUrl, '이미지 로딩 중 입니다.'); // 에디터에 이미지 삽입
                        attachImageRetryHandler(); // 이미지 로딩 실패 시 재시도 기능 적용
                    })
                    .catch(error => {
                        console.error("❌ 이미지 업로드 실패:", error);
                        alert("이미지 업로드에 실패했습니다.");
                    });
            }
        }
    });

    // 이미지가 로딩되지 않으면 재시도하는 함수
    function attachImageRetryHandler() {
        document.querySelectorAll('#editor img').forEach(img => {
            img.setAttribute('draggable', 'false'); // 이미지 드래그 방지
            img.addEventListener('error', function() {
                retryLoadImage(img, 10, 10000); // 최대 10번, 10초 간격으로 재시도
            });
        });
    }

    /**
     * 이미지 로딩 실패 시 재시도하는 함수
     * @param {HTMLElement} imgElement - 로딩할 이미지 요소
     * @param {number} maxRetries - 최대 재시도 횟수
     * @param {number} retryDelay - 재시도 간격 (ms)
     */
    function retryLoadImage(imgElement, maxRetries, retryDelay) {
        let retries = 0;

        function tryLoad() {
            const imgClone = new Image();
            imgClone.src = imgElement.src;

            imgClone.onload = () => {
                imgElement.src = imgClone.src; // 로딩 성공 시 이미지 업데이트
                console.log('✅ 이미지 로딩 성공:', imgClone.src);
            };

            imgClone.onerror = () => {
                retries++;
                if (retries < maxRetries) {
                    console.log(`🔄 이미지 로딩 재시도 중 (${retries}/${maxRetries}): ${imgClone.src}`);
                    setTimeout(tryLoad, retryDelay);
                } else {
                    console.error('❌ 이미지 로딩 실패:', imgClone.src);
                }
            };
        }

        tryLoad();
    }

    // 업로드된 이미지 경로를 저장하는 배열
    let uploadedImages = [];

    /**
     * 업로드된 이미지 경로 추가
     * @param {string} imageUrl - 업로드된 이미지 URL
     */
    function addImageToTrack(imageUrl) {
        uploadedImages.push(imageUrl);
    }

    /**
     * 삭제된 이미지 경로 제거
     * @param {string} imageUrl - 삭제된 이미지 URL
     */
    function removeImageFromTrack(imageUrl) {
        uploadedImages = uploadedImages.filter(url => url !== imageUrl);
    }

    // 에디터 변경 감지하여 삭제된 이미지 확인
    editor.on('change', function() {
        const editorContent = editor.getHTML();

        uploadedImages.forEach(imageUrl => {
            if (!editorContent.includes(imageUrl)) {
                // 에디터에서 삭제된 이미지는 서버에서도 삭제 요청
                api.delete('/api/file/delete', {
                    data: { fileUrl: imageUrl }
                })
                    .then(() => {
                        console.log('🗑️ 이미지 삭제 성공:', imageUrl);
                        removeImageFromTrack(imageUrl);
                    })
                    .catch(error => {
                        console.error('❌ 이미지 삭제 실패:', error);
                    });
            }
        });
    });

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
        data.append('fileUrl', uploadedImages.join(","));  // 이미지 URL 배열을 문자열로 변환하여 전송
        data.append('content', content);  // HTML 태그 제거한 텍스트만 전송

        if (!title || !content) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        // 게시글 데이터 전송
        api.post('/api/board', data)
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
