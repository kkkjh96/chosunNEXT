document.addEventListener('DOMContentLoaded', function() {
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
                const formData = new FormData();
                formData.append("uploadFile", blob);

                api.post('/api/file/upload', formData, {
                    headers: { 'Content-Type': 'multipart/form-data' }
                })
                    .then(response => {
                        callback(response.fileUrl, '이미지 설명');

                        // 삽입된 이미지에 draggable 속성 적용
                        document.querySelectorAll('#editor img').forEach(img => {
                            img.setAttribute('draggable', 'false');
                        });
                    })
                    .catch(error => {
                        console.error("이미지 업로드 실패:", error);
                        alert("이미지 업로드에 실패했습니다.");
                    });
            }
        }
    });

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

    // 게시글 등록 버튼 이벤트
    document.getElementById('submitPost').addEventListener('click', function () {
        const title = document.getElementById('title').value;
        const content = editor.getHTML();

        if (!title || !content) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        // 게시글 데이터 전송
        api.post('/api/board', {
            title: title,
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
