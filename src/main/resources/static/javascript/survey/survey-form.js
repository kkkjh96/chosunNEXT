document.addEventListener('DOMContentLoaded', function () {
    const surveyForm = document.getElementById('surveyForm');
    const pageTitle = document.querySelector('.page-title');
    const userId = document.querySelector('.userId').value;
    const surveyQuestions = document.getElementById('surveyQuestions');
    const path = window.location.pathname;
    const titleId = path.split('/').pop();

    if (!pageTitle || !surveyQuestions) {
        console.error('mainContent 또는 surveyQuestions 요소를 찾을 수 없습니다.');
        return;
    }

    // 설문 데이터 가져오기
    api.get(`/api/survey/${titleId}`)
        .then(response => {
            renderSurvey(response);
        })
        .catch(error => {
            console.error('설문 데이터를 가져오는데 실패했습니다.', error);
            surveyQuestions.innerHTML = '<p>데이터를 불러오는 중 오류가 발생했습니다.</p>';
        });

    // 설문 데이터 렌더링 함수
    function renderSurvey(survey) {
        // 설문 제목과 설명 추가
        const headerContainer = document.createElement('div');
        headerContainer.classList.add('survey-header');

        const surveyTitle = document.createElement('h2');
        surveyTitle.classList.add('survey-title');
        surveyTitle.textContent = survey.title;

        const descriptionElement = document.createElement('p');
        descriptionElement.classList.add('survey-description');
        descriptionElement.textContent = survey.content;

        headerContainer.appendChild(surveyTitle);
        headerContainer.appendChild(descriptionElement);

        // 제목과 설명을 surveyQuestions 위에 추가
        pageTitle.appendChild(headerContainer);

        // 질문 추가
        survey.questions.forEach((question, index) => {
            const questionContainer = document.createElement('div');
            questionContainer.classList.add('question-container');

            questionContainer.innerHTML = `
                <p class="question-title">질문 ${index + 1}: ${question.content}</p>
                <input type="hidden" class="surveyId" name="surveyId" value="${question.surveyId}" data-index="${index}">
                `;

            // 질문 유형에 따라 옵션 렌더링
            if (question.type === 'SINGLE_CHOICE') {
                question.options.forEach(option => {
                    const optionElement = document.createElement('div');
                    optionElement.classList.add('option');
                    optionElement.innerHTML = `
                        <input type="radio" name="questions[${index}]" value="${option.optionId}">
                        <label>${option.content}</label>
                    `;
                    questionContainer.appendChild(optionElement);
                });
            } else if (question.type === 'MULTIPLE_CHOICE') {
                question.options.forEach(option => {
                    const optionElement = document.createElement('div');
                    optionElement.classList.add('option');
                    optionElement.innerHTML = `
                        <input type="checkbox" name="questions[${index}]" value="${option.optionId}">
                        <label>${option.content}</label>
                    `;
                    questionContainer.appendChild(optionElement);
                });
            } else if (question.type === 'TEXT') {
                const optionElement = document.createElement('textarea');
                optionElement.name = `questions[${index}]`;
                optionElement.classList.add('input-field');
                questionContainer.appendChild(optionElement);
            }

            surveyQuestions.appendChild(questionContainer);
        });
    }

    // 설문 제출 버튼 이벤트
    document.getElementById('submitSurvey').addEventListener('click', function (e) {
        // e.preventDefault(); // �� 기본 동작 방지
        const formData = new FormData(surveyForm);
        const surveyResponses = {
            userId : userId,
            titleId : titleId,
            submitOptionsList : []
        };
        let idx = 0;
        const x = document.querySelector(`.surveyId[data-index="${idx}"]`).value;
        console.log("aaaa" + x);
        formData.forEach((value, key) => {
            const inputElement = document.querySelector(`[name="${key}"]`);

            if (inputElement) {
                if (inputElement.type === 'checkbox' && inputElement.checked) {
                    // 체크박스는 선택된 항목만 추가
                    surveyResponses.submitOptionsList.push({
                        surveyId: document.querySelector(`.surveyId[data-index="${idx}"]`).value,
                        optionValue: [value]
                    });
                    idx++;
                } else if (inputElement.type === 'radio' && inputElement.checked) {
                    // 라디오 버튼 처리
                    surveyResponses.submitOptionsList.push({
                        surveyId: document.querySelector(`.surveyId[data-index="${idx}"]`).value,
                        optionValue: [value]
                    });
                    idx++;
                } else if (inputElement.type === 'text') {
                    // 텍스트 응답 처리
                    surveyResponses.submitOptionsList.push({
                        surveyId: document.querySelector(`.surveyId[data-index="${idx}"]`).value,
                        optionValue: [value]
                    });

                }
            }
        });

        console.log(surveyResponses);

        api.post(`/api/survey/${titleId}/submit`, surveyResponses)
            .then(() => {
                console.log(surveyResponses);
                alert('설문조사가 성공적으로 제출되었습니다.');
                window.location.href = '/survey/list';
            })
            .catch(error => {
                console.error('설문조사 제출에 실패했습니다.', error);
                alert('설문조사 제출에 실패했습니다.');
                console.log(surveyResponses);
            });
    });
});
