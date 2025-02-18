document.addEventListener('DOMContentLoaded', function () {
    console.log("🚀 페이지 로드 완료");

    // ✅ Toast UI Editor 초기화
    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        height: '800px',
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        hooks: {
            addImageBlobHook: (blob, callback) => {
                console.log("📌 이미지 업로드 요청됨:", blob);

                // 🔹 Blob URL 생성하여 미리보기 적용
                const previewUrl = URL.createObjectURL(blob);
                callback(previewUrl, '미리보기 이미지');

                // 🔹 업로드 이미지를 배열에 추가
                addImageToTrack(blob);
            }
        }
    });

    // ✅ 불필요한 HTML 태그 제거 후 순수 텍스트 추출
    function cleanEditorContent() {
        let fullContent = editor.getHTML();

        console.log("📌 원본 HTML 내용:", fullContent);

        // 🔹 블롭 미리보기 이미지 제거
        fullContent = fullContent.replace(/<img[^>]*src=["']blob:[^"']*["'][^>]*>/g, '');

        // 🔹 HTML 태그 제거 후 순수 텍스트만 추출
        let cleanedContent = fullContent.replace(/<\/?[^>]+(>|$)/g, "").trim();

        console.log("📌 정제된 내용 (HTML 제거됨):", cleanedContent);

        return cleanedContent;
    }

    // ✅ 업로드할 이미지 파일 리스트
    let uploadedImages = [];

    /**
     * ✅ 업로드할 이미지 파일을 저장하는 함수
     * @param {Blob} blob - 업로드할 이미지 Blob 데이터
     */
    function addImageToTrack(blob) {
        console.log("📌 업로드된 이미지 추가됨:", blob);
        uploadedImages.push(blob);
    }

    // ✅ 카테고리 데이터 파싱
    const categoryDataElement = document.getElementById("categoryData");
    let categories = [];

    try {
        categories = JSON.parse(categoryDataElement.textContent.trim());
        console.log("✅ 카테고리 데이터:", categories);
    } catch (error) {
        console.error("❌ JSON 파싱 오류:", error);
        return;
    }

    // ✅ 카테고리 선택 이벤트 추가
    initCategorySelection(categories);

    // ✅ 제출 버튼 이벤트 리스너 추가
    document.getElementById('submitBtn').addEventListener('click', submitNews);

    /**
     * ✅ 대분류 선택 시 소분류 로드 함수
     */
    function initCategorySelection(categories) {
        const mainCategory = document.getElementById("main_category");
        const subCategory = document.getElementById("sub_category");

        mainCategory.addEventListener("change", function () {
            const selectedMainCategory = mainCategory.value;
            subCategory.innerHTML = '<option value="">소분류를 선택하세요</option>'; // 초기화

            // ✅ 선택한 대분류에 해당하는 소분류 필터링
            const filteredSubCategories = categories.filter(cat => cat.parentCd === selectedMainCategory);

            filteredSubCategories.forEach(cat => {
                const option = document.createElement("option");
                option.value = cat.cd;
                option.textContent = cat.cdKor;
                subCategory.appendChild(option);
            });
        });
    }

    /**
     * ✅ 기사 제출 함수 (Axios + FormData + 이미지 포함)
     */
    function submitNews(event) {
        event.preventDefault(); // 기본 폼 제출 막기

        const title = document.getElementById('title').value;
        const cleanedContent = cleanEditorContent();

        if (!title || !cleanedContent) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        document.getElementById('editor-content').value = cleanedContent;

        const userId = document.getElementById('user_id') ? document.getElementById('user_id').value : "";
        const newsTypeElement = document.querySelector('input[name="newsType"]:checked');
        const newsType = newsTypeElement ? newsTypeElement.value : null;
        const writerElement = document.getElementById('reporter');
        const writer = writerElement ? writerElement.value : null;

        const mainCategory = document.getElementById("main_category");
        const subCategory = document.getElementById("sub_category");

        // ✅ 필수 값 검증
        if (!mainCategory.value) {
            alert("대분류를 선택하세요.");
            return;
        }
        if (!writer) {
            alert("기자를 선택하세요.");
            return;
        }

        // ✅ 전송할 데이터 객체 생성
        const newsData = {
            userId: userId,
            title: title,
            subTitle: document.getElementById('sub_title').value,
            content: cleanedContent,
            reservationTime: document.getElementById('reservation_time').value,
            newsType: newsType,
            mainCategoryCd: mainCategory.value,
            subCategoryCd: subCategory.value,
            writer: writer
        };

        console.log("📌 서버로 전송할 데이터:", newsData);

        // ✅ FormData 생성
        const formData = new FormData();
        formData.append('data', new Blob([JSON.stringify(newsData)], { type: 'application/json' }));

        // 🔹 업로드된 이미지 추가
        if (uploadedImages.length > 0) {
            uploadedImages.forEach((blob, index) => {
                formData.append('files', blob, `image${index}.jpg`);
                console.log(`📌 이미지 추가됨: image${index}.jpg`);
            });
        } else {
            console.log("❌ 업로드된 이미지가 없음!");
        }

        // ✅ FormData 내부 값 출력
        console.log("📌 최종 FormData 확인:");
        for (let pair of formData.entries()) {
            console.log(pair[0] + ": ", pair[1]);
        }

        // ✅ Axios를 이용한 데이터 전송 (이미지 포함)
        axios.post('/api/cms/news', formData, {
            headers: { "Content-Type": "multipart/form-data" }
        })
            .then(response => {
                console.log("✅ 기사 저장 성공", response.data);
                alert("저장 성공!");
                window.location.href = "/cms/";
            })
            .catch(error => {
                console.error("❌ 기사 저장 실패", error);
                alert("저장 실패!");
            });
    }
});
