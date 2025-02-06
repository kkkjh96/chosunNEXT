// api.get("/api/category")
//     .then(response => {
//         console.log("하단 카테고리:", response);
//         // response.data: { categories: [ { id: 1, name: "카테고리1" },... ] }
//         // TODO: 하단 카테고리를 HTML template로 랜더링
//         response.forEach(category => {
//             if(category.parentCd === 'MAIN_CATEGORY_CD') {
//                 console.log(category);
//             }
//
//         })
//     })
document.addEventListener('DOMContentLoaded', function () {
    const mainCategoryList = document.getElementById('main-category-list');
    const subCategoryWrapper = document.getElementById('sub-category-wrapper');
    const expandedMenu = document.getElementById('main-menu');
    const menuIcon = document.querySelector('.menu-icon-container');

    // API 요청으로 카테고리 데이터 가져오기
    api.get("/api/category")
        .then(response => {
            const categories = response;

            // 대분류 필터링
            const mainCategories = categories.filter(cat => cat.parentCd === 'MAIN_CATEGORY_CD');

            // 대분류 항목 추가
            mainCategories.forEach(mainCategory => {
                const mainItem = document.createElement('li');
                mainItem.className = 'main-category-item';
                mainItem.textContent = mainCategory.cdKor;
                mainCategoryList.appendChild(mainItem);
            });

            // 메뉴 아이콘에 마우스를 올리면 소분류 표시
            menuIcon.addEventListener('mouseenter', () => {
                expandedMenu.style.display = 'block';
                subCategoryWrapper.innerHTML = '';

                // 각 대분류별 소분류 항목 그룹화
                mainCategories.forEach(mainCategory => {
                    const subCategoryColumn = document.createElement('div');
                    subCategoryColumn.className = 'sub-category-column';

                    // 대분류 제목 추가
                    const subCategoryTitle = document.createElement('div');
                    subCategoryTitle.className = 'sub-category-title';
                    subCategoryTitle.textContent = mainCategory.cdKor;
                    subCategoryColumn.appendChild(subCategoryTitle);

                    // 소분류 항목 추가
                    const subCategories = categories.filter(sub => sub.parentCd === mainCategory.cd);
                    subCategories.forEach(subCategory => {
                        const subItem = document.createElement('div');
                        subItem.textContent = subCategory.cdKor;
                        subItem.className = 'sub-category-item';
                        subCategoryColumn.appendChild(subItem);
                    });

                    subCategoryWrapper.appendChild(subCategoryColumn);
                });
            });

            // 메뉴에서 마우스가 벗어나면 숨기기
            expandedMenu.addEventListener('mouseleave', () => {
                expandedMenu.style.display = 'none';
            });
        })
        .catch(error => console.error('카테고리 데이터를 불러오는 중 오류 발생:', error));
});
