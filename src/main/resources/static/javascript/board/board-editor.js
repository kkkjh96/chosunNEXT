document.addEventListener('DOMContentLoaded', function() {
    const instId = document.getElementById('instId').value;
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
                console.log("📌 업로드할 blob 데이터:", blob);
                const formData = new FormData();
                formData.append("file", blob);
                formData.append("instId", instId);

                // FormData 내용 확인
                for (let [key, value] of formData.entries()) {
                    console.log(`🔍 FormData 내용 확인: ${key} =`, value);
                }

                axios.post('/api/file/upload', formData, {
                    headers: { "Content-Type": "multipart/form-data" }
                })
                    .then(response => {
                        console.log(response);
                        const imageUrl = "http://localhost:7070" + response.data;
                        addImageToTrack(imageUrl);

                        // 이미지 로딩을 20초 대기 후 또는 재시도 방식으로 설정
                        setTimeout(() => {
                            callback(imageUrl, '이미지 설명');
                            attachImageRetryHandler();  // 이미지 태그에 재시도 기능 적용
                        }, 20000);
                    })
                    .catch(error => {
                        console.error("이미지 업로드 실패:", error);
                        alert("이미지 업로드에 실패했습니다.");
                    });
            }
        }
    });

    function attachImageRetryHandler() {
        document.querySelectorAll('#editor img').forEach(img => {
            img.setAttribute('draggable', 'false');
            img.addEventListener('error', function() {
                retryLoadImage(img, 5, 4000);
            });
        });
    }

    function retryLoadImage(imgElement, maxRetries, retryDelay) {
        let retries = 0;

        function tryLoad() {
            const imgClone = new Image();
            imgClone.src = imgElement.src;

            imgClone.onload = () => {
                imgElement.src = imgClone.src;
                console.log('이미지 로딩 성공:', imgClone.src);
            };

            imgClone.onerror = () => {
                retries++;
                if (retries < maxRetries) {
                    console.log('이미지 로딩 재시도 중...:', imgClone.src);
                    setTimeout(tryLoad, retryDelay);
                } else {
                    console.error('이미지 로딩 실패:', imgClone.src);
                }
            };
        }

        tryLoad();
    }

    // 복사 및 드래그 방지 이벤트 추가
    document.querySelector('#editor').addEventListener('copy', function(e) {
        if (e.target.tagName === 'IMG') {
            e.preventDefault();
            alert('이미지를 복사할 수 없습니다.');
        }
    });

    document.querySelector('#editor').addEventListener('contextmenu', function(e) {
        if (e.target.tagName === 'IMG') {
            e.preventDefault();
            alert('이미지에 대한 마우스 오른쪽 버튼 사용이 금지되어 있습니다.');
        }
    });

    // 이미지 경로 관리 배열
    let uploadedImages = [];

    function addImageToTrack(imageUrl) {
        uploadedImages.push(imageUrl);
    }

    function removeImageFromTrack(imageUrl) {
        uploadedImages = uploadedImages.filter(url => url !== imageUrl);
    }

    // 에디터 변경 이벤트 감지 (이미지 삭제 확인)
    editor.on('change', function() {
        const editorContent = editor.getHTML();

        // 삭제된 이미지 추적
        uploadedImages.forEach(imageUrl => {
            if (!editorContent.includes(imageUrl)) {
                // 이미지가 에디터에서 삭제된 경우 서버에 삭제 요청
                api.delete('/api/file/delete', {
                    data: { fileUrl: imageUrl }
                })
                    .then(() => {
                        console.log('이미지 삭제 성공:', imageUrl);
                        removeImageFromTrack(imageUrl);
                    })
                    .catch(error => {
                        console.error('이미지 삭제 실패:', error);
                    });
            }
        });
    });

    // 게시글 등록 버튼 이벤트
    document.getElementById('submitPost').addEventListener('click', function() {
        const title = document.getElementById('title').value;
        const content = editor.getHTML();

        if (!title || !content) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        // 게시글 데이터 전송
        api.post('/api/board', {
            title: title,
            userId: instId,
            content: content
        })
            .then(() => {
                alert('게시글이 등록되었습니다.');
                window.location.href = '/board/list';
            })
            .catch(error => {
                console.error('게시글 등록 실패:', error);
                alert('게시글 등록에 실패했습니다.');
            });
    });
});
