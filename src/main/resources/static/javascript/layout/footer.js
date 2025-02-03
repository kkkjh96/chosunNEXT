api.get("/api/footer/categories")
    .then(response => {
        console.log("하단 카테고리:", response);
        // response.data: { categories: [ { id: 1, name: "카테고리1" },... ] }
        // TODO: 하단 카테고리를 HTML template로 랜더링
        response.forEach(category => {
            if(category.parentCd === 'MAIN_CATEGORY_CD') {
                console.log(category);
            }

        })
    })