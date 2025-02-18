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

    let uploadedImages = [];

    /**
     * ✅ 업로드할 이미지 파일을 저장하는 함수
     * @param {Blob} blob - 업로드할 이미지 Blob 데이터
     */
    function addImageToTrack(blob) {
        console.log("📌 업로드된 이미지 추가됨:", blob);
        uploadedImages.push(blob);
    }

    /**
     * ✅ HTML에서 이미지 태그의 src 속성만 추출하는 함수
     * @returns {Array} 이미지 src URL 목록
     */
    function extractImageSrc() {
        const editorContent = editor.getHTML();
        const imgTagRegex = /<img[^>]+src=["']([^"']+)["']/g;
        let matches, imageSrcList = [];

        while ((matches = imgTagRegex.exec(editorContent)) !== null) {
            imageSrcList.push(matches[1]);
        }

        console.log("📌 추출된 이미지 src 목록:", imageSrcList);
        return imageSrcList;
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

    /**
     * ✅ 대분류 선택 시 소분류 로드 함수 (복구된 기능)
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
     * ✅ 기사 제출 함수 (Axios + FormData + JSON + 이미지 포함)
     */
    function submitNews(event) {
        event.preventDefault();

        const title = document.getElementById('title').value.trim() || "기사 제목 없음";
        const subTitle = document.getElementById('sub_title').value.trim() || "기사 제목 없음";
        const editorContent = editor.getHTML();
        const imageSrcList = extractImageSrc();

        if (!title || !editorContent) {
            alert('제목과 내용을 입력하세요.');
            return;
        }

        const userId = document.getElementById('user_id') ? document.getElementById('user_id').value : "";
        const newsTypeElement = document.querySelector('input[name="newsType"]:checked');
        const newsType = newsTypeElement ? newsTypeElement.value : null;
        const writer = document.getElementById('reporter')?.value;

        const mainCategory = document.getElementById("main_category");
        const subCategory = document.getElementById("sub_category");

        if (!mainCategory.value) {
            alert("대분류를 선택하세요.");
            return;
        }
        if (!writer) {
            alert("기자를 선택하세요.");
            return;
        }

        // ✅ 서버로 보낼 JSON 데이터
        const newsData = {
            userId: userId,
            title: title,
            subTitle: subTitle,
            content: editorContent,
            reservationTime: document.getElementById('reservation_time').value,
            newsType: newsType,
            mainCategoryCd: mainCategory.value,
            subCategoryCd: subCategory.value,
            writer: writer,
            images: imageSrcList // 🟢 추출된 이미지 URL 추가
        };

        console.log("📌 서버로 전송할 JSON 데이터:", newsData);

        // ✅ FormData 생성
        const formData = new FormData();

        // 🔹 JSON 데이터를 Blob으로 변환해서 추가
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

        // ✅ Axios를 이용한 데이터 전송 (이미지 + JSON 포함)
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

    // ✅ 제출 버튼 이벤트 리스너 추가
    document.getElementById('submitBtn').addEventListener('click', submitNews);
});
